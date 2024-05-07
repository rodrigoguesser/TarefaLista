package com.example.tasklist.ui.views

import com.example.tasklist.models.Tarefa

data class TarefaListUiState(
    val tarefaList: List<Tarefa> = listOf()
)
