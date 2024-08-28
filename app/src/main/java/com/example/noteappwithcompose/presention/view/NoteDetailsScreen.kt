package com.example.noteappwithcompose.presention.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteappwithcompose.data.db.Note
import com.example.noteappwithcompose.presention.viewModel.NoteViewModel


class NoteDetailsScreen(val note: Note, val noteViewModel: NoteViewModel) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val title = remember { mutableStateOf(note.title) }
        val description = remember { mutableStateOf(note.description) }
        val navigator = LocalNavigator.currentOrThrow


        Column(
            modifier = Modifier
                .fillMaxWidth()
            ,horizontalAlignment = Alignment.CenterHorizontally) {

            TopAppBar(
                title = { Text(text = "All Notes") },
                navigationIcon = {
                    IconButton(onClick = {navigator.pop()}
                        , modifier = Modifier.border(2.dp, color = Color.White)) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(text = "Title", fontWeight = FontWeight.Bold,color = Color.Black)

                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(text = "Enter Title") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
//                        .background( Color(0xFF83B7E4))/////color background of text field
                    ,shape = RoundedCornerShape(8.dp)
//                    ,colors = CardDefaults.cardColors(containerColor = Color(0xFF83B7E4) )

                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Text(text = "Description",fontWeight = FontWeight.Bold,color = Color.Black)

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = { Text(text = "Enter Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 8.dp)

//                        .background( Color(0xFF83B7E4))/////color background of text field
                    ,shape = RoundedCornerShape(8.dp)
//                    ,colors = CardDefaults.cardColors(containerColor = Color(0xFF83B7E4) )

                )
            }
            Spacer(modifier = Modifier.height(10.dp))





            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    noteViewModel.updateNote(note.copy(title = title.value, description = description.value))
                    navigator.pop()
                }, shape = RoundedCornerShape(8.dp)) {
                    Text(text = "Update")
                }

                Spacer(modifier = Modifier.width(15.dp))

                Button(onClick = {
                    noteViewModel.deleteNote(note)
                    navigator.pop()
                },shape = RoundedCornerShape(8.dp)) {
                    Text(text = "Delete")
                }
            }

        }

    }



}