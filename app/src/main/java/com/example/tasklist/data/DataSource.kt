package com.example.tasklist.data

import com.example.tasklist.models.Tarefa

fun insertList(): List<Tarefa> {

    val tarefa = listOf(
        Triple("Fazer compras", "Ir ao supermercado e comprar mantimentos.", true),
        Triple("Fazer exercícios", "Praticar exercícios físicos por pelo menos 30 minutos.", false),
        Triple("Ler livro", "Dedicar tempo para ler um capítulo do livro atual.", false)
    )

    val tarefaList = tarefa.mapIndexed { index, (titulo, descricao, concluido) ->
        Tarefa(titulo = titulo, descricao = descricao, concluido = concluido)
    }

    return tarefaList
}