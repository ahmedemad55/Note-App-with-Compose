package com.example.noteappwithcompose.presention.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteappwithcompose.data.db.Note
import com.example.noteappwithcompose.presention.viewModel.NoteViewModel
import kotlinx.coroutines.delay
import kotlin.reflect.KFunction1

@Composable
fun HomeScreenContent(noteViewModel: NoteViewModel) {

    val allNotes = noteViewModel.allNotes.collectAsState()
    val navigator = LocalNavigator.currentOrThrow


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(allNotes.value ?: emptyList()) { note ->
            SwipeToDeleteContainer(item = note, onDelete = noteViewModel::deleteNote) {

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFA5B3FF)) //A5B3FF   7D91FA
                    ,
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigator.push(NoteDetailsScreen(note, noteViewModel)) }
                        .padding(16.dp)) {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    noteViewModel.updateNote(note.copy(isFavorite = !note.isFavorite))
                                }
                                .align(Alignment.End).size(30.dp),
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = if (note.isFavorite) Color.Black else Color.White)

                        Text(
                            text = note.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = note.description,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                            //font color
                        )
                    }

                }

            }
        }


    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteContainer(
    item: Note,
    onDelete: KFunction1<Note, Unit>,
    animationDurationMillis: Int = 500,
    content: @Composable (RowScope.(Note) -> Unit)
) {
//    if (item is Note) {
        val context = LocalContext.current
        var isRemoved by remember { mutableStateOf(false) }
        val systemBackgroundColor = remember(context) {
            Color.Unspecified
        }
        val state = rememberSwipeToDismissBoxState(
            confirmValueChange = { value ->
                if (value == SwipeToDismissBoxValue.EndToStart) {
                    isRemoved = true
                    true
                } else {
                    false
                }
            }
        )
        LaunchedEffect(key1 = isRemoved) {
            if (isRemoved) {
                delay(animationDurationMillis.toLong())
                onDelete(item)
            }
        }

        AnimatedVisibility(
            visible = !isRemoved,
            exit = shrinkVertically(
                animationSpec = tween(durationMillis = animationDurationMillis),
                shrinkTowards = Alignment.Top
            ) + fadeOut(animationSpec = tween(durationMillis = animationDurationMillis))
        ) {
            SwipeToDismissBox(
                state = state,
                backgroundContent = { DeleteBackground(systemBackgroundColor) },
                content = { content(item) },
                enableDismissFromStartToEnd = false
            )
        }
    }
//}

@Composable
fun DeleteBackground(systemBackgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(systemBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color(0xFF000000)
            )
        }
    }
}



/*
Search Code

val notes = noteViewModel.allNotes.collectAsState(initial = emptyList()).value
val filteredNotes = if (searchQuery.isEmpty()) {
    notes
} else {
    notes?.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.description.contains(searchQuery, ignoreCase = true)
    }
}

 */


