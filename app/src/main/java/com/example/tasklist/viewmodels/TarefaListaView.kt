package com.example.tasklist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.data.insertList
import com.example.tasklist.models.Tarefa
import com.example.tasklist.ui.views.AppScreens
import com.example.tasklist.ui.views.AppUiState
import com.example.tasklist.ui.views.InsertFormUiState
import com.example.tasklist.ui.views.TarefaListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TarefaListaView : ViewModel() {

    private val _tarefaListUiState = MutableStateFlow(TarefaListUiState(insertList()))
    val tarefaListUiState: StateFlow<TarefaListUiState> = _tarefaListUiState.asStateFlow()

    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    private val _insertFormUiState = MutableStateFlow(InsertFormUiState())
    val insertFormUiState: StateFlow<InsertFormUiState> = _insertFormUiState.asStateFlow()

    private var tarefaToEdit: Tarefa = Tarefa()
    private var editTarefa: Boolean = false

    fun navegar(navController: NavController) {
        if (_appUiState.value.title == R.string.tarefa_list) {
            _appUiState.update { currentState ->
                currentState.copy(
                    title = R.string.insert_tarefa,
                    fabIcon = R.drawable.baseline_assignment_turned_in_24,
                    iconContentDescription = R.string.confirm,
                )
            }
            navController.navigate(AppScreens.InsertTarefa.name)
        } else {
            val tarefas: MutableList<Tarefa> = _tarefaListUiState.value.tarefaList.toMutableList()
            if (editTarefa) {
                val pos = tarefas.indexOf(tarefaToEdit)
                tarefas.removeAt(pos)
                tarefas.add(pos, Tarefa(
                    _insertFormUiState.value.titulo,
                    _insertFormUiState.value.descricao,
                    _insertFormUiState.value.concluido,
                ))
                tarefaToEdit = Tarefa()
                editTarefa = false
            } else {
                tarefas.add(Tarefa(
                    titulo = _insertFormUiState.value.titulo,
                    descricao = _insertFormUiState.value.descricao,
                    concluido = _insertFormUiState.value.concluido,
                ))
            }
            _tarefaListUiState.update { currentState ->
                currentState.copy(
                    tarefaList = tarefas.toList()
                )
            }
            _insertFormUiState.update {
                InsertFormUiState()
            }
            _appUiState.update {
                AppUiState()
            }
            navController.navigate(AppScreens.TarefaList.name) {
                popUpTo(AppScreens.TarefaList.name) {
                    inclusive = true
                }
            }
        }
    }

    fun navigateBack(navController: NavController) {
        _appUiState.update {
            AppUiState()
        }
        navController.popBackStack()
    }

    fun deletarTarefa(tarefa: Tarefa) {
        val tarefas: MutableList<Tarefa> = _tarefaListUiState.value.tarefaList.toMutableList()
        tarefas.remove(tarefa)
        _tarefaListUiState.value = _tarefaListUiState.value.copy(
            tarefaList = tarefas.toList()
        )
    }

    fun onEditTarefa(tarefa: Tarefa, navController: NavController) {
        editTarefa = true
        tarefaToEdit = tarefa
        _insertFormUiState.update { currentState ->
            currentState.copy(
                titulo = tarefaToEdit.titulo,
                descricao = tarefaToEdit.descricao,
                concluido = tarefaToEdit.concluido,
            )
        }
        _appUiState.update { currentState ->
            currentState.copy(
                title = R.string.edit_tarefa,
                fabIcon = R.drawable.baseline_assignment_turned_in_24,
                iconContentDescription = R.string.confirm,
            )
        }
        navController.navigate(route = AppScreens.InsertTarefa.name)
    }

    fun onNameChage(titulo: String) {
        _insertFormUiState.update { currentState ->
            currentState.copy(
                titulo = titulo
            )
        }
    }

    fun onUpdateDescricao(descricao: String) {
        _insertFormUiState.update { currentState ->
            currentState.copy(
                descricao = descricao
            )
        }
    }

    fun onUpdateStatus(tarefa: Tarefa, navController: NavController) {
        val tarefas: MutableList<Tarefa> = _tarefaListUiState.value.tarefaList.toMutableList()
        val pos = tarefas.indexOf(tarefa)
        if (pos != -1) {
            val updatedTarefa = tarefa.copy(concluido = !tarefa.concluido)
            tarefas[pos] = updatedTarefa
        }
        _tarefaListUiState.update { currentState ->
            currentState.copy(
                tarefaList = tarefas.toList()
            )
        }
    }
}
