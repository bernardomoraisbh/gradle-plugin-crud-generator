package com.crud.generator
import com.crud.generator.utils.LocalizationUtils

class BaseGenerator {
    String groupName
    String entityName
    LocalizationUtils languageDict // This could be a Groovy map or another object
    def fieldsInput  // This could be a Groovy list of maps
    String tableName
    String tableSchema
    String jdkVersion
    String completePackagePath

    BaseGenerator(String groupName, String entityName, LocalizationUtils languageDict, def fieldsInput, String tableName, String tableSchema, String jdkVersion, String completePackagePath) {
        this.groupName = groupName
        this.entityName = entityName
        this.languageDict = languageDict
        this.fieldsInput = fieldsInput
        this.tableName = tableName
        this.tableSchema = tableSchema
        this.jdkVersion = jdkVersion
        this.completePackagePath = completePackagePath
    }

    void writeToJavaFile(String basePath, String fileName, String content) {
        File directory = new File(basePath)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        File file = new File("$basePath/$fileName.java")
        file.text = content
    }
}
