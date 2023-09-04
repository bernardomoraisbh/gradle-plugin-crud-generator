package com.crud.generator.utils

class LocalizationUtils {
    String language
    Map localizationUtils

    LocalizationUtils(String language) {
        this.language = language
        this.localizationUtils = getLocalizationUtils(language)
    }

    static final Map BR = [
            "Group Project Name:" : "Nome do Grupo de Projeto",
            "Entity Name:" : "Nome da Entidade",
            "Language:" : "Idioma",
            "Table Name:" : "Nome da Tabela",
            "Table Schema:" : "Esquema da Tabela",
            "JDK Version:" : "Versão do JDK",
            "Add Field" : "Adicionar Campo",
            "Generate Files" : "Gerar Arquivos",
            "Error" : "Erro",
            "version": "versao",
            "getDatabaseTime": "retornarDataBanco",
            "findByFilters": "listarComFiltros",
            "endDate": "dataFim",
            "logicalDelete": "excluirLogicamente",
            "findById": "buscarPorId",
            "findVoById": "buscarVoPorId",
            "listVoWithFilters": "listarVoFiltrado",
            "listWithFilters": "listarFiltrado",
            "saveEntity": "salvar",
            "updateEntity": "editar",
            "deleteEntity": "excluir",
            "not found": "não encontrado",
            "Application closed by the user.": "Aplicação encerrada pelo usuário.",
            "CRUD Input Fields": "Campos do CRUD",
            "Column Name: ": "Nome da Coluna: ",
            "Type: ": "Tipo: ",
            "Name: ": "Nome: ",
            "Join Cardinality: ": "Cardinalidade do Join: ",
            "Join Column Name: ": "Nome da coluna do Join: ",
            "Remove": "Remover",
            "Successfully generated the CRUD files." : "CRUD gerado com sucesso.",
            // ... Add more as needed
    ]

    Map getLocalizationUtils(String language) {
        if (language == 'BR') {
            return BR
        } else {
            return [:]  // For "US", no translation needed
        }
    }

    String getText(String key) {
        return localizationUtils.get(key, key)
    }

    boolean equals(Object other) {
        if (other instanceof LocalizationUtils) {
            return this.language == other.language && this.localizationUtils == other.localizationUtils
        }
        return false
    }
}
