package com.sen.framework.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Huang Sen
 * @date 2019/7/3
 * @description
 */
public class FileUtils {

    public static void write(String filepath, Object content) {
        try {
            File file = new File(filepath);
            FileWriter writer = new FileWriter(file);
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
