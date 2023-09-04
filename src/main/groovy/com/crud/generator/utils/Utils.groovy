package com.crud.generator.utils

class Utils {
    // Convert camelCase to kebab-case
    static String camelToKebab(String camel) {
        StringBuilder kebab = new StringBuilder()
        for(char c : camel.toCharArray()) {
            if(Character.isUpperCase(c)) {
                kebab.append('-')
                kebab.append(Character.toLowerCase(c))
            } else {
                kebab.append(c)
            }
        }
        return kebab.toString()
    }

    // Convert camelCase to snake_case
    static String camelToSnake(String camel) {
        StringBuilder snake = new StringBuilder()
        for(char c : camel.toCharArray()) {
            if(Character.isUpperCase(c)) {
                snake.append('_')
                snake.append(Character.toLowerCase(c))
            } else {
                snake.append(c)
            }
        }
        return snake.toString()
    }

    // Convert camelCase to PascalCase
    static String camelToPascal(String camel) {
        return camel[0].toUpperCase() + camel.substring(1)
    }

    // Convert PascalCase to camelCase
    static String pascalToCamel(String pascal) {
        return pascal[0].toLowerCase() + pascal.substring(1)
    }
}
