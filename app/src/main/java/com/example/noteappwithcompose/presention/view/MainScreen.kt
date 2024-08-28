package com.example.noteappwithcompose.presention.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteappwithcompose.data.db.Note
import com.example.noteappwithcompose.presention.viewModel.NoteViewModel


class MainScreen(private val noteViewModel: NoteViewModel) :Screen {
    @Composable
    override fun Content() {

        val selectedIndex = remember { mutableIntStateOf(0) }
        val showAddDialog = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val searchQuery = remember { mutableStateOf("") }

        // Observe filtered notes based on search query
        val filteredNotes = noteViewModel.searchNotes(searchQuery.value).collectAsState().value


        Scaffold(
            floatingActionButton = {
                IconButton(modifier = Modifier.size(80.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF7D91FA),
                        contentColor = Color.White
                    ),
                    onClick = {
                        showAddDialog.value = true
                    }) {
                    Icon(
                        modifier = Modifier.size(35.dp),
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                }
            },
            topBar = { TopBar() }, bottomBar = {
                BottomAppBar(/* containerColor = Color.Gray */) {
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = { selectedIndex.intValue = 0 }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            tint = if (selectedIndex.intValue == 0) Color(0xFF7D91FA) else Color.White
                        )
                    }
                    IconButton(
                        modifier = Modifier.weight(1f),
                        onClick = { selectedIndex.intValue = 1 }) {
                        Icon(
                            modifier = Modifier.size(50.dp),
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = if (selectedIndex.intValue == 1) Color(0xFF7D91FA) else Color.White
                        )
                    }
                }
            }, modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                SearchBar(searchQuery)
                // screen content
                if (showAddDialog.value) {
                    AddNoteDialog(onSaveClick = { x, y ->
                        noteViewModel.insertNote(
                            Note(
                                title = x,
                                description = y,
                                color = 0xff000000,
                                isFavorite = false
                            )
                        )
                        showAddDialog.value = false
                    },
                        onDismissRequest = {
                            showAddDialog.value = false
                        })
                }

                // Display filtered notes if search query is not empty, otherwise show the appropriate screen
                if (searchQuery.value.isNotEmpty()) {
                    if (filteredNotes != null) {
                        NoteList(notes = filteredNotes, noteViewModel = noteViewModel)
                    }
                } else {
                    if (selectedIndex.intValue == 0) {
                        HomeScreenContent(noteViewModel = noteViewModel)
                    } else {
                        FavoriteScreenContent(noteViewModel = noteViewModel)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopBar() {
        TopAppBar(
            title = {
                Text(
                    text = "All Notes",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(start = 10.dp)
                )
            },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        )
    }

    @Composable
    fun SearchBar(searchQuery: MutableState<String>) {
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            placeholder = { Text("Search notes") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            singleLine = true
        )
    }
}





//colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Gray, contentColor = Color.White)

/*
@Composable
override fun Content() {
    val selectedIndex = remember { mutableIntStateOf(0) }
    val showAddDialog = remember { mutableStateOf(false) }
    val searchQuery = remember { mutableStateOf("") }

    // Observe filtered notes based on search query
    val filteredNotes = noteViewModel.searchNotes(searchQuery.value).collectAsState().value

    Scaffold(
        floatingActionButton = {
            IconButton(modifier = Modifier.size(80.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF7D91FA), contentColor = Color.White),
                onClick = {
                    showAddDialog.value = true
                }) {
                Icon(modifier = Modifier.size(35.dp), imageVector = Icons.Default.Edit, contentDescription = null)
            }
        },
        topBar = { TopBar() },
        bottomBar = {
            BottomAppBar {
                IconButton(modifier = Modifier.weight(1f), onClick = { selectedIndex.intValue = 0 }) {
                    Icon(modifier = Modifier.size(50.dp), imageVector = Icons.Default.Home, contentDescription = null, tint = if (selectedIndex.intValue == 0) Color(0xFF7D91FA) else Color.White)
                }
                IconButton(modifier = Modifier.weight(1f), onClick = { selectedIndex.intValue = 1 }) {
                    Icon(modifier = Modifier.size(50.dp), imageVector = Icons.Default.Favorite, contentDescription = null, tint = if (selectedIndex.intValue == 1) Color(0xFF7D91FA) else Color.White)
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(searchQuery)
            if (showAddDialog.value) {
                AddNoteDialog(onSaveClick = { x, y ->
                    noteViewModel.insertNote(Note(title = x, description = y, color = 0xff000000, isFavorite = false))
                    showAddDialog.value = false
                }, onDismissRequest = {
                    showAddDialog.value = false
                })
            }
            // Display filtered notes if search query is not empty, otherwise show the appropriate screen
            if (searchQuery.value.isNotEmpty()) {
                NoteList(notes = filteredNotes, noteViewModel = noteViewModel)
            } else {
                if (selectedIndex.intValue == 0) {
                    HomeScreenContent(noteViewModel = noteViewModel)
                } else {
                    FavoriteScreenContent(noteViewModel = noteViewModel)
                }
            }
        }
    }
}

 */

