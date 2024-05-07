package com.example.tasklist.models

data class Tarefa(
    val titulo: String = "",
    val descricao: String = "",
    val concluido: Boolean = false,
)
