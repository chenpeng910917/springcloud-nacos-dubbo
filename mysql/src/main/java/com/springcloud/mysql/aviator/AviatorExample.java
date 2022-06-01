package com.springcloud.mysql.aviator;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aviator规则引擎
 *
 * @author chenpeng
 */
public class AviatorExample {
    /**
     * 规则可以保存在数据库中，mysql或者redis等等
     */
    Map<Integer, String> ruleMap = new HashMap<>();

    public AviatorExample() {
        //秒数计算公式
        ruleMap.put(1, "hour * 3600 + minute * 60 + second");
        //正方体体积计算公式
        ruleMap.put(2, "height * width * length");
        //判断一个人是不是资深顾客
        ruleMap.put(3, "age >= 18 && sumConsume > 2000 && vip");
        //资深顾客要求修改
        ruleMap.put(4, "age > 10 && sumConsume >= 8000 && vip && avgYearConsume >= 1000");
        //判断一个人的年龄是不是大于等于18岁
        ruleMap.put(5, "age  >= 18 ? 'yes' : 'no'");
        // 判断包含
        ruleMap.put(6, "string.contains(s1,s2)");
        // 自定义函数 乘法
        ruleMap.put(7, "multiply(a,b)");
        // 自定义时间函数
        ruleMap.put(8, "time(a,b,c)");
        // 判断日期
        ruleMap.put(9, "time > startTime && time < endTime");
    }

    public static void main(String[] args) {
        // 注册自定义函数
        AviatorEvaluator.addFunction(new MultiplyFunction());
        AviatorEvaluator.addFunction(new TimeFunction());
        AviatorExample aviatorExample = new AviatorExample();
        //选择规则，传入规则所需要的参数
        System.out.println("公式1：" + aviatorExample.getResult(1, 1, 1, 1));
        System.out.println("公式2：" + aviatorExample.getResult(2, 3, 3, 3));
        System.out.println("公式3：" + aviatorExample.getResult(3, 20, 3000, false));
        System.out.println("公式4：" + aviatorExample.getResult(4, 23, 8000, true, 2000));
        System.out.println("公式5：" + aviatorExample.getResult(5, 12));
        System.out.println("公式6：" + aviatorExample.getResult(6, "100024,100000", "100024"));
        System.out.println("公式7：" + aviatorExample.getResult(7, 2, 2));
        System.out.println("公式8：" + aviatorExample.getResult(8, "2022-03-01 22:33:23", "2022-05-20 22:33:23", "2022-05-17 22:33:23"));
        System.out.println("公式9：" + aviatorExample.getResult(9, "2022-05-17 22:33:23", "2022-03-01 22:33:23", "2022-05-20 22:33:23"));

        // 多层集合过滤
        Map<String, Object> env = Maps.newHashMap();
        env.put("time", "2022-05-17 22:33:23");
        env.put("startTime", "2022-03-01 22:33:23");
        env.put("endTime", "2022-05-20 22:33:23");
        env.put("s1", "100024");
        env.put("s2", "100024");
        System.out.println("公式6map：" + aviatorExample.getResultMap(6, env));
        System.out.println("公式9map：" + aviatorExample.getResultMap(9, env));

    }

    public Object getResult(int ruleId, Object... args) {
        String rule = ruleMap.get(ruleId);
        return AviatorEvaluator.exec(rule, args);
    }

    public Object getResultMap(int ruleId, Map<String, Object> env) {
        String rule = ruleMap.get(ruleId);
        return AviatorEvaluator.execute(rule, env);
    }

    @Test
    public void script2() throws IOException {
        //编译脚本
        //路径是文件系统的绝对路径或相对路径，
        //相对路径的时候，必须项目的根目录开始的相对路径
        //classpath下的绝对或相对路径
        Expression compiledExp = AviatorEvaluator.getInstance().compileScript("aviator/script.av");
        //执行脚本，参数可以map，也可以通过newEnv kv对的方式塞入，最终还是map
        final Object o = compiledExp.execute(compiledExp.newEnv("age", 12, "name", "yxk"));
        System.out.println(o);
    }

    /**
     * 脚本存储字符串执行
     */
    @Test
    public void test3() {
        String script = "str = \"\";\n" +
                "if (age <=1 ){\n" +
                "  str = \"婴儿\";\n" +
                "} elsif (age>1 && age<=6) {\n" +
                "   str = \"儿童\";\n" +
                "} elsif (age>6 && age<=17) {\n" +
                "  str = \"青少年\";\n" +
                "} elsif (age>18 && age<=40){\n" +
                "  str = \"青年\";\n" +
                "} elsif (age>40 && age<=48){\n" +
                "  str = \"壮年\";\n" +
                "} elsif (age>48 && age<=65){\n" +
                "  str = \"中年\";\n" +
                "} elsif (age>65){\n" +
                "  str = \"老年\";\n" +
                "}\n" +
                "return str=\"#{name}处在#{str}期\";";
        Expression compile = AviatorEvaluator.getInstance().compile(script, true);
        final Object o = compile.execute(compile.newEnv("age", 12, "name", "yxk"));
        System.out.println(o);
    }

    @Test
    public void test4() {
        String channel = "if channels!=\"ALL\" {\n" +
                "    return string.contains(channels,channel);\n" +
                "} else {\n" +
                "    return true;\n" +
                "}";
        String cityCode = "if cityCodes!=\"ALL\" {\n" +
                "    return string.contains(cityCodes,cityCode);\n" +
                "} else {\n" +
                "    return true;\n" +
                "}";
        String time = "if startTime!= nil && endTime!=nil {\n" +
                "    return time > startTime && time < endTime;\n" +
                "} else {\n" +
                "    return true;\n" +
                "}";
        // 规则
        List<String> list = Lists.newArrayList();
        list.add(channel);
        list.add(cityCode);
        list.add(time);
        // 条件
        Map<String, Object> map = Maps.newHashMap();
        map.put("channels", "gaode,ziyou");
//        map.put("channels", "ALL");
        map.put("channel", "gaode");

        map.put("cityCodes", "100011,200011");
        map.put("cityCode", "100011");

        map.put("time", "2022-05-17 22:33:23");
        map.put("startTime", "2022-03-01 22:33:23");
        map.put("endTime", "2022-05-20 22:33:23");

        for (String script : list) {
            Expression compile = AviatorEvaluator.getInstance().compile(script, true);
            final Object o = compile.execute(map);
            System.out.println(o);
        }
    }

    @Test
    public void test5() {
        String script = "let result = false;\n" +
                "\n" +
                "if channels!=\"ALL\" {\n" +
                "    result = string.contains(channels,channel);\n" +
                "    if !result {\n" +
                "        return false;\n" +
                "    }\n" +
                "} else {\n" +
                "    result = true;\n" +
                "}\n" +
                "\n" +
                "if cityCodes!=\"ALL\" {\n" +
                "    result = string.contains(cityCodes,cityCode);\n" +
                "    if !result {\n" +
                "        return false;\n" +
                "    }\n" +
                "} else {\n" +
                "    result = true;\n" +
                "}\n" +
                "\n" +
                "if startTime!= nil && endTime!=nil {\n" +
                "    result = time > startTime && time < endTime;\n" +
                "    if !result {\n" +
                "        return false;\n" +
                "    }\n" +
                "} else {\n" +
                "    result = true;\n" +
                "}\n" +
                "return result;";
        // 条件
        Map<String, Object> map = Maps.newHashMap();
        map.put("channels", "gaode,ziyou");
//        map.put("channels", "ALL");
        map.put("channel", "gaode");

        map.put("cityCodes", "100011,200011");
        map.put("cityCode", "100011");

        map.put("time", "2022-05-17 22:33:23");
        map.put("startTime", "2022-03-01 22:33:23");
        map.put("endTime", "2022-05-20 22:33:23");

        Expression compile = AviatorEvaluator.getInstance().compile(script, true);
        final Object o = compile.execute(map);
        System.out.println(o);
    }


    /**
     * 循环
     */
    @Test
    public void test6() {
        String script = "for i in list {\n" +
                "  println(i);\n" +
                "}";
        // 条件
        Map<String, Object> map = Maps.newHashMap();
        List<String> list = Lists.newArrayList();
        list.add("aa");
        list.add("bb");
        map.put("list", list);

        Expression compile = AviatorEvaluator.getInstance().compile(script, true);
        final Object o = compile.execute(map);
        System.out.println(o);
    }

    /**
     * 循环传递实体
     */
    @Test
    public void test7() {
        String script = "for i in list {\n" +
                "  println(i.channels);\n" +
                "  println(i.cityCodes);\n" +
                "}";
        // 条件
        Map<String, Object> map = Maps.newHashMap();
        List<AviatorEntity> list = Lists.newArrayList();
        AviatorEntity aviatorEntity = new AviatorEntity();
        aviatorEntity.setChannels("gaode,ziyou");
        aviatorEntity.setCityCodes("100000");
        list.add(aviatorEntity);
        AviatorEntity aviatorEntity1 = new AviatorEntity();
        aviatorEntity1.setChannels("meituan,baidu");
        aviatorEntity1.setCityCodes("120000");
        list.add(aviatorEntity1);
        map.put("list", list);

        Expression compile = AviatorEvaluator.getInstance().compile(script, true);
        final Object o = compile.execute(map);
        System.out.println(o);
    }

    @Test
    public void test8() {
        String script = "let result = false;\n" +
                "for i in list {\n" +
                "  println(i);\n" +
                "  println(i.channels);\n" +
                "  ## 判断渠道\n" +
                "  if i.channels!=\"ALL\" {\n" +
                "      result = string.contains(i.channels,channel);\n" +
                "      if !result {\n" +
                "          println(i.channels+\"9行\"+channel);\n" +
                "          continue;\n" +
                "      }\n" +
                "  } else {\n" +
                "      result = true;\n" +
                "  }\n" +
                "\n" +
                " ## 判断城市\n" +
                "  if i.cityCodes!=\"ALL\" {\n" +
                "      result = string.contains(i.cityCodes,cityCode);\n" +
                "      if !result {\n" +
                "           println(i.cityCodes+\"20行\"+cityCode);\n" +
                "          continue;\n" +
                "      }\n" +
                "  } else {\n" +
                "      result = true;\n" +
                "  }\n" +
                "\n" +
                "  ## 判断时间\n" +
                "  if i.startTime!= nil && i.endTime!=nil {\n" +
                "      result = time > i.startTime && time < i.endTime;\n" +
                "      if !result {\n" +
                "           println(i.startTime+\"31行\"+time);\n" +
                "          continue;\n" +
                "      }\n" +
                "  } else {\n" +
                "      result = true;\n" +
                "  }\n" +
                "\n" +
                "  if result {\n" +
                "      println(\"提前返回结果\"+i);\n" +
                "      return result;\n" +
                "  }\n" +
                "}\n" +
                "return result;";
        Map<String, Object> map = Maps.newHashMap();
        List<AviatorEntity> list = Lists.newArrayList();
        AviatorEntity aviatorEntity = new AviatorEntity();
        aviatorEntity.setChannels("gaode,ziyou");
        aviatorEntity.setCityCodes("100011,200011");
        aviatorEntity.setStartTime("2022-03-01 22:33:23");
        aviatorEntity.setEndTime("2022-05-20 22:33:23");
        list.add(aviatorEntity);
        AviatorEntity aviatorEntity1 = new AviatorEntity();
        aviatorEntity1.setChannels("ALL");
        aviatorEntity1.setCityCodes("ALL");
        aviatorEntity1.setStartTime("2022-03-01 22:33:23");
        aviatorEntity1.setEndTime("2022-05-20 22:33:23");
        list.add(aviatorEntity1);
        map.put("list", list);

        map.put("channel", "gaode");
//        map.put("channel", "aa");

        map.put("cityCode", "100011");

        map.put("time", "2022-05-17 22:33:23");

        Expression compile = AviatorEvaluator.getInstance().compile(script, true);
        final Object o = compile.execute(map);
        System.out.println(o);

        for (AviatorEntity l : list) {
            System.out.println(l);

        }
    }

    /**
     * 生成规则
     */
    @Test
    public void test9() {
//        String script = "string.contains(\"gaode,ziyou\",channel) && string.contains(\"100011,200011\", cityCode) &&  time > \"2022-03-01 22:33:23\" && time < \"2022-05-20 22:33:23\"";
//        String script = "\"gaode\"==channel && string.contains(\"100011,200011\", cityCode) &&  time > \"2022-03-01 22:33:23\" && time < \"2022-05-20 22:33:23\"";

        String script = "let channels = seq.list(\"gaode\",\"ziyou\");\n" +
                "let cityCodes = seq.list(\"100011\",\"200011\");\n" +
                "let floatMoneyUp = seq.list(0,11);\n" +
                "let floatMoneyDown = seq.list(10,20);\n" +
                "let floatMoneys = seq.list(5,8);\n" +
                "\n" +
                "let result = false;\n" +
                "for i in channels {\n" +
                "  ## 判断渠道\n" +
                "  println(i);\n" +
                "  if i!=\"ALL\" {\n" +
                "      result = i==channel;\n" +
                "      if !result {\n" +
                "          continue;\n" +
                "      } else {\n" +
                "          result = true;\n" +
                "          break;\n" +
                "      }\n" +
                "  } else {\n" +
                "      result = true;\n" +
                "      break;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "if !result {\n" +
                "  println(\"渠道提前返回不匹配\");\n" +
                "  return false;\n" +
                "}\n" +
                "\n" +
                " ## 判断城市\n" +
                "for i in cityCodes {\n" +
                "  if i!=\"ALL\" {\n" +
                "      result = i==cityCode;\n" +
                "      if !result {\n" +
                "          continue;\n" +
                "      } else {\n" +
                "        result = true;\n" +
                "        break;\n" +
                "      }\n" +
                "  } else {\n" +
                "      result = true;\n" +
                "      break;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "if !result {\n" +
                "println(\"城市提前返回不匹配\");\n" +
                "  return false;\n" +
                "}\n" +
                "\n" +
                " ## 判断浮动金额 浮动范围区间并且小于浮动金额\n" +
                "let sum = 0;\n" +
                "for i in floatMoneyUp {\n" +
                "println(\"浮动\"+money+\" \"+i+\" \"+floatMoneyDown[sum]+\" \"+floatMoneys[sum]+\" \"+floatMoney);\n" +
                "    result = money > i && money < floatMoneyDown[sum] && floatMoneys[sum] >= floatMoney;\n" +
                "    sum = sum + 1;\n" +
                "println(result);\n" +
                "    if !result {\n" +
                "        continue;\n" +
                "    } else {\n" +
                "        result = true;\n" +
                "        break;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "if !result {\n" +
                "  println(\"浮动金额提前返回不匹配\");\n" +
                "  return false;\n" +
                "}\n" +
                "\n" +
                "  ## 判断时间\n" +
                "result = time > \"2022-03-01 22:33:23\" && time < \"2022-05-20 22:33:23\";\n" +
                "\n" +
                "return result;";

        String script1 = "return true;";
        ArrayList<String> list = Lists.newArrayList();
        list.add(script);
//        list.add(script1);

        Map<String, Object> map = Maps.newHashMap();
        map.put("channel", "gaode");
//        map.put("channel", "aa");
        map.put("cityCode", "100011");
        map.put("time", "2022-05-17 22:33:23");
        map.put("floatMoney", 3);
        map.put("money", 9);

        for (String l : list) {
            System.out.println(l);
            Expression compile = AviatorEvaluator.getInstance().compile(script, true);
            final Object o = compile.execute(map);
            System.out.println(o);
        }
    }
}

/**
 * 自定义函数
 */
class MultiplyFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
        double num1 = FunctionUtils.getNumberValue(arg1, env).doubleValue();
        double num2 = FunctionUtils.getNumberValue(arg2, env).doubleValue();
        return new AviatorDouble(num1 * num2);
    }

    @Override
    public String getName() {
        return "multiply";
    }
}

/**
 * 自定义时间范围函数
 */
class TimeFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2, AviatorObject arg3) {
        String stringValue1 = FunctionUtils.getStringValue(arg1, env);
        String stringValue2 = FunctionUtils.getStringValue(arg2, env);
        String stringValue3 = FunctionUtils.getStringValue(arg3, env);

        // 当前时间
//        Date now = DateUtil.date();

        Date start = DateUtil.parse(stringValue1);
        Date end = DateUtil.parse(stringValue2);
        Date now = DateUtil.parse(stringValue3);
        boolean in = DateUtil.isIn(now, start, end);
        return AviatorBoolean.valueOf(in);
    }

    @Override
    public String getName() {
        return "time";
    }
}

