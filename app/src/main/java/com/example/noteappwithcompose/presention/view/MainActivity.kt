package com.example.noteappwithcompose.presention.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.Navigator
import com.example.noteappwithcompose.presention.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //val viewModel = NoteViewModel(application)
        //val noteViewModel : NoteViewModel by viewModels()

        setContent {
            val noteViewModel : NoteViewModel = hiltViewModel()
            Navigator(screen = MainScreen(noteViewModel))
        }
    }
}


@Composable
fun AddNoteDialog(onSaveClick : (String,String)->Unit,onDismissRequest : ()->Unit){
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = { onDismissRequest() },
        /*
        icon = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { onDismissRequest() }) {
                Icon(
                    Icons.Default.Close, contentDescription =null , modifier = Modifier.align(
                        Alignment.CenterEnd))
            }
        },
         */


        confirmButton = {
            Button(onClick = { onSaveClick(title.value,description.value) }) {
                Text(text ="Save" )
            }
        },

        dismissButton = {
            Button( onClick = { onDismissRequest() }) {
                Text(text ="Cancel" )
            }
        },
        title = { Text(text = "Add New Note"
            ,modifier = Modifier.fillMaxWidth()) },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(value = title.value, onValueChange = {title.value=it},
                    label = { Text(text = "Add Title")})
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(modifier = Modifier.height(100.dp),value = description.value, onValueChange = {description.value=it},
                    label = { Text(text = "Add Description")})
            }
        }
    )
}
