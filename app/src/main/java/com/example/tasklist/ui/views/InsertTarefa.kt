package com.example.tasklist.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasklist.viewmodels.TarefaListaView

@Composable
fun InsertTarefa(
    viewModel: TarefaListaView,
    navController: NavController,
) {
    BackHandler {
        viewModel.navigateBack(navController)
    }
    val uiState by viewModel.insertFormUiState.collectAsState()
    InsertForm(
        titulo = uiState.titulo,
        descricao = uiState.descricao,
        onUpdateName = viewModel::onNameChage,
        onUpdateDescricao = viewModel::onUpdateDescricao,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun InsertForm(
    titulo: String,
    descricao: String,
    onUpdateName: (String) -> Unit,
    onUpdateDescricao: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TextField(
            value = titulo,
            onValueChange = onUpdateName,
            label = { Text(text = "Título") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
        TextField(
            value = descricao,
            onValueChange = onUpdateDescricao,
            label = { Text(text = "Descrição") },
            singleLine = false,
            minLines = 1,
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InsertFormPreview() {
    InsertForm( "", "", {}, {})
}
