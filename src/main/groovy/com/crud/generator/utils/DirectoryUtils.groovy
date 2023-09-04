package com.crud.generator.utils

class DirectoryUtils {
    static final String SRC_MAIN_JAVA = "src/main/java"
    static final String SRC_TEST_JAVA = "src/test/java"

    static String createDirectories(String parentFolderName, String groupName) {
        String fullPath = "$parentFolderName/$SRC_MAIN_JAVA/${groupName.replace('.', '/')}"
        File directory = new File(fullPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return fullPath
    }

    static String createTestDirectories(String parentFolderName, String groupName) {
        String fullPath = "$parentFolderName/$SRC_TEST_JAVA/${groupName.replace('.', '/')}"
        File directory = new File(fullPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return fullPath
    }
}
