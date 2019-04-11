package com.tfm.framework.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionConst {
    public ExceptionConst() {
    }

    public static String printStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String result = e.getMessage();

        return result + "\n" + sw.toString();
    }
}