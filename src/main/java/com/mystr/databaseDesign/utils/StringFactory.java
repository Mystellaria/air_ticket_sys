package com.mystr.databaseDesign.utils;


public class StringFactory {
    public static int[] StringSplitter(String input) {
        String[] a = input.split(",");
        int[] result = new int[a.length];
        for(int i = 0; i<a.length ; i++){
            result[i] = Integer.parseInt(a[i]);
        }
        return result;
    }

    public static String[] StringSplitterSpace(String input){
        String[] a = input.split(" ");
        return  a;
    }
}
