package com.benjamin.parsy.runnetic.user.infrastructure.utils;

public class ClassNameUtils {

    /**
     * Retrieves the class name with the first letter in lower case.
     *
     * @param clazz The class name to be retrieved.
     * @return The class name with the first letter in lowercase.
     */
    public static String getClassNameWithFirstLetterLowerCase(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        if (simpleName.length() > 1) {
            return Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
        } else {
            return simpleName.toLowerCase();
        }
    }

}
