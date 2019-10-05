package com.zhongshang.utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");

        List<String> b = new ArrayList<String>();
        b.add("1");
        b.add("3");

        a.removeAll(b);

        String res = a.toString();

        System.out.println(res);

    }
}
