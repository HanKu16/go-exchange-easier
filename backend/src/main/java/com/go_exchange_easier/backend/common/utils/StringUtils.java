package com.go_exchange_easier.backend.common.utils;

public class StringUtils {

    public static String pascalToScreamingSnakeCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();
    }

}