package main.util;

public class ValidationUtil {

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean isPositiveNumber(int number) {
        return number > 0;
    }
}