package com.sen.framework.array;

import java.util.Arrays;

public class SetArrayTest {

    public static void main(String[] args) {
        int w=0,r=0;
        String[] strArr1 = {"1","2","3"};
        String[] strArr2 = {"4","5","6"};
        strArr1[w++] = strArr2[r];
        System.out.println(Arrays.toString(strArr1));
        System.out.println("w:"+w);
    }
}
