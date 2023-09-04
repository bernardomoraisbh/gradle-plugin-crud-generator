package com.crud.generator

import com.crud.generator.utils.LocalizationUtils
import com.crud.generator.utils.Utils

class EntityGenerator extends BaseGenerator {

    EntityGenerator(String groupName, String entityName, LocalizationUtils languageDict, def fieldsInput, String tableName, String tableSchema, String jdkVersion, String completePackagePath) {
        super(groupName, entityName, languageDict, fieldsInput, tableName, tableSchema, jdkVersion, completePackagePath)
    }

    String temporalAnnotation(String attribute_name) {
        if (attribute_name =~ /Date|Data|date|data/) {
            return '@Temporal(TemporalType.TIMESTAMP)'
        }
        return ""
    }

    void parseJoin(Map fieldDict, List fieldCodeLines, String attributeName) {
        String joinDetails = fieldDict.join_details
        String joinAnnotation = ""

        if (joinDetails) {
            if (!joinDetails.contains(',')) {
                println "Warning: Invalid join_details format for ${fieldDict.name}. Expected 'type,column', got '$joinDetails'"
                return
            }
            def (joinTypeObj, joinColumnNameObj) = joinDetails.split(',')
            String joinType = joinTypeObj
            String joinColumnName = joinColumnNameObj

            switch (joinType) {
                case '1-1':
                    joinAnnotation = """@OneToOne(fetch = FetchType.LAZY)\n@JoinColumn(name = "${joinColumnName}")"""
                    break
                case '1-n':
                    joinAnnotation = """@OneToMany(mappedBy = "$attributeName", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH], orphanRemoval = true)"""
                    break
                case 'n-1':
                    joinAnnotation = """@ManyToOne(fetch = FetchType.LAZY)\n@JoinColumn(name = "${joinColumnName}")"""
                    break
                case 'n-n':
                    joinAnnotation = """@ManyToMany(fetch = FetchType.LAZY)\n@JoinColumn(name = "${joinColumnName}")"""
                    break
            }

            if (joinAnnotation) {
                fieldCodeLines << (fieldCodeLines ? '\t\t' : '') + joinAnnotation
            }
        }
    }

    String generateFieldCode() {
        List fieldCodeLines = []
        fieldsInput.each { fieldDict ->
            String fieldType = fieldDict.type
            String attributeName = fieldDict.name
            def columnName = fieldDict.column_name ?: Utils.camelToSnake(attributeName)

            def temporalAnnotation = temporalAnnotation(attributeName)

            parseJoin(fieldDict, fieldCodeLines, attributeName)
            fieldCodeLines << "@Column(name = \"$columnName\")"

            if (temporalAnnotation) {
                fieldCodeLines << temporalAnnotation
            }

            fieldCodeLines << "private $fieldType $attributeName;"
        }

        fieldCodeLines.join('\n').replaceAll(";\n", ";\n\t\t").replaceAll("\\)\n", ")\n\t\t")
    }

    void generate() {
        def annotationsPackage = jdkVersion == '11' ? 'javax.persistence' : 'jakarta.persistence'
        def entityNamePascal = Utils.camelToPascal(entityName)
        def versionField = languageDict.getText("version")

        def entityCode = """
            package ${groupName}.entity;

            import lombok.Data;
            import $annotationsPackage.*;
            import java.time.LocalDateTime;
            import javax.persistence.Temporal;
            import javax.persistence.TemporalType;
            import javax.persistence.CascadeType;
            import javax.persistence.FetchType;

            @Data
            @Table(name = "$tableName", schema = "$tableSchema")
            @SequenceGenerator(name = "seq_id_$tableName", sequenceName = "seq_id_$tableName", allocationSize = 1)
            public class $entityNamePascal {

                @Id
                @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_id_$tableName")
                @Column(name = "seq_$tableName")
                private Long id;

                ${generateFieldCode()}

                @Version
                private Integer $versionField;
            }
        """

        writeToJavaFile("$completePackagePath/entity", entityNamePascal, entityCode)
    }
}