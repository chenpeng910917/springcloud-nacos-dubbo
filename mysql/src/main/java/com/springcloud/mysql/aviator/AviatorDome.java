package com.springcloud.mysql.aviator;

import com.google.common.collect.Lists;
import com.springcloud.mysql.aviator.entity.RuleConfigDetailDO;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenpeng
 */
public class AviatorDome {

    /**
     * aviator生成器
     */
    @Test
    public void test() {

        StringBuilder sb = new StringBuilder();
        sb.append("let result = false;\n");
        // 获取数据
        List<RuleConfigDetailDO> data = getData();
        // 按字段类型分组
        Map<Integer, List<RuleConfigDetailDO>> fieldTypeMap = data.stream().collect(Collectors.groupingBy(RuleConfigDetailDO::getFieldType));

        for (Map.Entry<Integer, List<RuleConfigDetailDO>> entry : fieldTypeMap.entrySet()) {

            List<RuleConfigDetailDO> fieldTypeList = fieldTypeMap.get(entry.getKey());

            // 判断字符串类型处理
            if (entry.getKey().equals(1)) {
                // 按条件key分组
                Map<String, List<RuleConfigDetailDO>> conditionKeyMap = fieldTypeList.stream().collect(Collectors.groupingBy(RuleConfigDetailDO::getConditionKey));
                for (Map.Entry<String, List<RuleConfigDetailDO>> conditionKeyEntry : conditionKeyMap.entrySet()) {
                    List<RuleConfigDetailDO> conditionKeyList = conditionKeyMap.get(conditionKeyEntry.getKey());
                    // 排序
                    conditionKeyList.sort(Comparator.comparing(RuleConfigDetailDO::getSort));

                    // 拼接规则
                    String scriptList = "let %ss = seq.list(\"%s\");\n";

                    // 转list
                    List<String> conditionValueList = conditionKeyList.stream().map(RuleConfigDetailDO::getConditionValue).collect(Collectors.toList());
                    // list转字符串逗号分隔
                    String conditionValueString = String.join("\",\"", conditionValueList);
                    // 替换占位符
                    String scriptListFormat = String.format(scriptList, conditionKeyEntry.getKey(), conditionValueString);
                    // 拼接list
                    sb.append(scriptListFormat);

                    // 拼接循环
                    String scriptLoop = "for i in %s {\n" +
                            "  ## %s\n" +
                            "  println(i);\n" +
                            "  if i!=\"ALL\" {\n" +
                            "      result = i %s %s;\n" +
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
                            "  println(\"%S提前返回不匹配\");\n" +
                            "  return false;\n" +
                            "}\n";
                    String conditionKeyListScript = conditionKeyEntry.getKey() + "s";
                    String scriptLoopFormat = String.format(scriptLoop, conditionKeyListScript, conditionKeyListScript, conditionKeyList.get(0).getOperator(), conditionKeyEntry.getKey(), conditionKeyListScript);
                    sb.append(scriptLoopFormat);
                }
            }
            // 判断日期类型处理
            if (entry.getKey().equals(2)) {

                List<String> list = Lists.newArrayList();
                // 拼接日期规则
                String scriptDate = "result = %s;\n";
                String script = "%s %s \"%s\"";
                for (RuleConfigDetailDO ruleConfigDetailDO : fieldTypeList) {
                    list.add(String.format(script, ruleConfigDetailDO.getConditionKey(), ruleConfigDetailDO.getOperator(), ruleConfigDetailDO.getConditionValue()));
                }
                String scriptDateJoin = String.join(" && ", list);
                sb.append(String.format(scriptDate, scriptDateJoin));
            }

            // 判断浮动金额
            if (entry.getKey().equals(3)) {
                // 排序
                fieldTypeList.sort(Comparator.comparing(RuleConfigDetailDO::getSort));

                String floatMoneyUp = "let %ss = seq.list(%s);\n";
                String floatMoneyDown = "let floatMoneyDown = seq.list(%s);\n";
                String floatMoneys = "let floatMoneys = seq.list(%s);\n";

                // 按运算符分组
                Map<String, List<RuleConfigDetailDO>> operatorMap = fieldTypeList.stream().collect(Collectors.groupingBy(RuleConfigDetailDO::getOperator));
                for (Map.Entry<String, List<RuleConfigDetailDO>> conditionKeyEntry : operatorMap.entrySet()) {
                    List<RuleConfigDetailDO> operatorList = operatorMap.get(conditionKeyEntry.getKey());
                    // 转list
                    List<String> conditionValueList = operatorList.stream().map(RuleConfigDetailDO::getConditionValue).collect(Collectors.toList());
                    // list转字符串逗号分隔
                    String conditionValueString = String.join(",", conditionValueList);

                    if ("up".equals(conditionKeyEntry.getKey())) {
                        sb.append(String.format(floatMoneyUp, conditionKeyEntry.getKey(), conditionValueString));
                    }
                    if ("down".equals(conditionKeyEntry.getKey())) {
                        sb.append(String.format(floatMoneyDown, conditionValueString));
                    }
                    if ("value".equals(conditionKeyEntry.getKey())) {
                        sb.append(String.format(floatMoneys, conditionValueString));
                    }
                }

                String scriptFloatMoney = "let sum = 0;\n" +
                        "for i in floatMoneyUp {\n" +
                        "    result = %s > i && %s < floatMoneyDown[sum] && floatMoneys[sum] <= floatMoney;\n" +
                        "    sum = sum + 1;\n" +
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
                        "}\n";
                String scriptFloatMoneyFormat = String.format(scriptFloatMoney, fieldTypeList.get(0).getConditionKey(), fieldTypeList.get(0).getConditionKey());
                sb.append(scriptFloatMoneyFormat);
            }

        }

        sb.append("return result;");
        System.out.println(sb);

    }

    @Test
    public void test1() {
        String script = "for i in %s {\n" +
                "  ## %s\n" +
                "  println(i);\n" +
                "  if i!=\"ALL\" {\n" +
                "      result = i==%s;\n" +
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
                "  println(\"%S提前返回不匹配\");\n" +
                "  return false;\n" +
                "}";
        String format = String.format(script, "channels", "渠道", "channel", "渠道");
        System.out.println(format);
    }

    /**
     * 获取数据模拟数据库
     *
     * @return 数据
     */
    public List<RuleConfigDetailDO> getData() {
        List<RuleConfigDetailDO> list = Lists.newArrayList();

        // 渠道
        RuleConfigDetailDO ruleConfigDetailDO = RuleConfigDetailDO.builder()
                .fieldType(1)
                .conditionKey("channel")
                .conditionValue("gaode")
                .operator("==")
                .sort(1)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO1 = RuleConfigDetailDO.builder()
                .fieldType(1)
                .conditionKey("channel")
                .conditionValue("ziyou")
                .operator("==")
                .sort(2)
                .build();

        // 城市
        RuleConfigDetailDO ruleConfigDetailDO3 = RuleConfigDetailDO.builder()
                .fieldType(1)
                .conditionKey("cityCode")
                .conditionValue("100011")
                .operator("==")
                .sort(1)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO4 = RuleConfigDetailDO.builder()
                .fieldType(1)
                .conditionKey("cityCode")
                .conditionValue("200011")
                .operator("==")
                .sort(2)
                .build();

        // 日期
        RuleConfigDetailDO ruleConfigDetailDO5 = RuleConfigDetailDO.builder()
                .fieldType(2)
                .conditionKey("time")
                .conditionValue("2022-03-01 22:33:23")
                .operator(">")
                .sort(1)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO6 = RuleConfigDetailDO.builder()
                .fieldType(2)
                .conditionKey("time")
                .conditionValue("2022-05-20 22:33:23")
                .operator("<")
                .sort(2)
                .build();

        // 浮动金额
        RuleConfigDetailDO ruleConfigDetailDO7 = RuleConfigDetailDO.builder()
                .fieldType(3)
                .conditionKey("money")
                .conditionValue("5")
                .operator("value")
                .sort(1)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO8 = RuleConfigDetailDO.builder()
                .fieldType(3)
                .conditionKey("money")
                .conditionValue("0")
                .operator("up")
                .sort(2)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO9 = RuleConfigDetailDO.builder()
                .fieldType(3)
                .conditionKey("money")
                .conditionValue("10")
                .operator("down")
                .sort(3)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO10 = RuleConfigDetailDO.builder()
                .fieldType(3)
                .conditionKey("money")
                .conditionValue("8")
                .operator("value")
                .sort(4)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO12 = RuleConfigDetailDO.builder()
                .fieldType(3)
                .conditionKey("money")
                .conditionValue("20")
                .operator("down")
                .sort(6)
                .build();

        RuleConfigDetailDO ruleConfigDetailDO11 = RuleConfigDetailDO.builder()
                .fieldType(3)
                .conditionKey("money")
                .conditionValue("11")
                .operator("up")
                .sort(5)
                .build();



        list.add(ruleConfigDetailDO);
        list.add(ruleConfigDetailDO1);
        list.add(ruleConfigDetailDO3);
        list.add(ruleConfigDetailDO4);
        list.add(ruleConfigDetailDO5);
        list.add(ruleConfigDetailDO6);
        list.add(ruleConfigDetailDO7);
        list.add(ruleConfigDetailDO8);
        list.add(ruleConfigDetailDO9);
        list.add(ruleConfigDetailDO10);
        list.add(ruleConfigDetailDO11);
        list.add(ruleConfigDetailDO12);

        return list;
    }
}
