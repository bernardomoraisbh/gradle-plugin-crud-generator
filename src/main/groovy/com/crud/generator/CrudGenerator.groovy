package com.crud.generator

import com.crud.generator.utils.LocalizationUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

class CrudGenerator implements Plugin<Project> {
    void apply(Project project) {
        project.task('crudGenerator') {
            doLast {
                String type = project.hasProperty('type') ? project.property('type') : 'Controller'
                String language = project.hasProperty('language') ? project.property('language') : 'US'
                String entityName = project.hasProperty('entityName') ? project.property('entityName') : 'ERROR'
                String tableName = project.hasProperty('tableName') ? project.property('tableName') : 'ERROR'
                String tableSchema = project.hasProperty('tableSchema') ? project.property('tableSchema') : 'ERROR'
                String jdkVersion = project.hasProperty('jdk') ? project.property('jdk') : '11'
                
                // Group name will be the same as the project name
                def groupName = project.name

                LocalizationUtils localizationDict = new LocalizationUtils(language)
                
                // You might want to call various helper methods depending on 'type'
                if (type == 'Controller') {
                    generateController()
                } else if (type == 'Entity') {
                    new EntityGenerator(groupName, entityName, localizationDict, null, tableName, tableSchema, jdkVersion, "").generate()
                } // and so on...
            }
        }
    }

    void generateController() {
        // Controller generation logic here
    }

    void generateEntity() {
        // Entity generation logic here
    }
    
    // Add more methods for VO, DTO, Request, Service, TestController, Repository
}