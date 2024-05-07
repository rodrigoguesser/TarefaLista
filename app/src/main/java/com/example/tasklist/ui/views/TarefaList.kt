import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.models.Tarefa
import com.example.tasklist.ui.tema.TaskListTheme
import com.example.tasklist.viewmodels.TarefaListaView

@Composable
fun TarefaList(
    viewModel: TarefaListaView,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.tarefaListUiState.collectAsState()

    LazyColumn {
        items(uiState.tarefaList) { tarefa ->
            TarefaCard(
                tarefa = tarefa,
                onDelete = viewModel::deletarTarefa,
                onEditTask = {
                    viewModel.onEditTarefa(
                        tarefa = tarefa,
                        navController = navController,
                    )
                },
                onUpdateStatus = {
                    viewModel.onUpdateStatus(
                        tarefa = tarefa,
                        navController = navController,
                    )
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun TarefaCard(
    tarefa: Tarefa,
    onDelete: (Tarefa) -> Unit,
    onUpdateStatus: (Tarefa) -> Unit,
    onEditTask: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEditTask() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = tarefa.titulo,
                    fontWeight = if (tarefa.concluido) FontWeight.Light else FontWeight.Bold,
                    fontStyle = if (tarefa.concluido) FontStyle.Italic else FontStyle.Normal,
                    fontSize = 22.sp,
                    color = if (tarefa.concluido) Color.Gray else Color.Black, // Alterando a cor do texto
                    textAlign = TextAlign.Start // Alinhamento do texto
                )
                Text(
                    text = tarefa.descricao,
                    fontStyle = FontStyle.Italic,
                    fontWeight = if (tarefa.concluido) FontWeight.ExtraLight else FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 2,
                    modifier = Modifier.width(260.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(if (tarefa.concluido) R.drawable.baseline_backspace_24 else R.drawable.baseline_assignment_turned_in_24),
                contentDescription = if (tarefa.concluido) "delete" else "check",
                modifier = Modifier
                    .clickable { if (tarefa.concluido) onDelete(tarefa) else onUpdateStatus(tarefa) }
                    .padding(end = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    TaskListTheme {
        TarefaCard(
            tarefa = Tarefa(
                titulo = "Teste",
                descricao = "Atividade a fazer de teste",
                concluido = true
            ),
            onDelete = {},
            onUpdateStatus = {},
            onEditTask = {}
        )
    }
}
