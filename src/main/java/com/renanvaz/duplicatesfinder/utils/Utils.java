package com.renanvaz.duplicatesfinder.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {

    /*
      Gets the content of a file
     */
    public static List<String> getFileContent(String filePath) throws Exception {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}