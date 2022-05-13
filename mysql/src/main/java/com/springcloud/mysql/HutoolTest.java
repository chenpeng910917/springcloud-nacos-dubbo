package com.springcloud.mysql;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author chenpeng
 */
public class HutoolTest {
    public static void main(String[] args) {

        String dateStart = "2022-03-01 22:33:23";
        Date start = DateUtil.parse(dateStart);
        String dateEnd = "2022-05-20 22:33:23";
        Date end = DateUtil.parse(dateEnd);
        // 当前时间
        Date now = DateUtil.date();
        boolean in = DateUtil.isIn(now, start, end);
        System.out.println(now);
        System.out.println(start);
        System.out.println(end);
        System.out.println(in);
    }
}
