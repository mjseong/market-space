package com.juneox.marketspace.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {
    public static List<List<String>> readCsv(String filePath){

        File file = new File(filePath);
        if(file.exists() && !getFileExtension(file).equals("csv")){
            throw new RuntimeException("not csv file");
        }

        List<List<String>> records = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while((line = reader.readLine()) != null){
                List<String> values = new ArrayList<>();
                String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for(String token:tokens){
                    values.add(token.replaceAll("^\"|\"$", ""));
                }
                records.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return records;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        else return "";
    }
}
