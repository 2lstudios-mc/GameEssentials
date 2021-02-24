package dev._2lstudios.gameessentials.utils;

import net.md_5.bungee.api.ChatColor;

public class SideboardExpressions {

    // Expressions
    public static boolean endExpression(String line) {
        final String value = line.split("_")[1];
        return !ChatColor.stripColor(line).endsWith(value);
    }

    public static boolean equalsExpression(String line) {
        final String first = line.split("==")[0];
        final String second = line.split("==")[1];

        return first.equalsIgnoreCase(second);
    }

    public static boolean exactExpression(String line) {
        final String first = line.split("===")[0];
        final String second = line.split("===")[1];

        return first.equals(second);
    }

    public static boolean notEqualsExpression(String line) {
        final String first = line.split("!=")[0];
        final String second = line.split("!=")[1];

        return !first.equalsIgnoreCase(second);
    }

    public static boolean notExactExpression(String line) {
        final String first = line.split("!==")[0];
        final String second = line.split("!==")[1];

        return !first.equals(second);
    }

    public static boolean notExpression(String line) {
        final String value = line.split("_")[1];
        return ChatColor.stripColor(line).endsWith(value);
    }

    // Utils
    public static String extractExpression(String line) {
        if (line.startsWith("[") && line.contains("]")) {
            return line.split("\\[")[1].split("\\]")[0];
        }
        return null;
    }

    public static boolean evaluateExpression(String line) {
        final String expression = SideboardExpressions.extractExpression(line);

        if (expression != null) {
            if (expression.startsWith("end_")) {
                return SideboardExpressions.endExpression(expression);
            }

            else if (expression.startsWith("not_")) {
                return SideboardExpressions.notExpression(expression);
            }

            else if (expression.contains("!==")) {
                return SideboardExpressions.notExactExpression(expression);
            }

            else if (expression.contains("!=")) {
                return SideboardExpressions.notEqualsExpression(expression);
            }

            else if (expression.contains("===")) {
                return SideboardExpressions.exactExpression(expression);
            }

            else if (expression.contains("==")) {
                return SideboardExpressions.equalsExpression(expression);
            }
        }

        return true;
    }

}
