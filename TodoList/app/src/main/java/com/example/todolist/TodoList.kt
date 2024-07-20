package com.example.todolist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class TodoItem(
    val id: Int,
    var name: String
)

@Composable
fun TodoList() {
    var todoItems by remember { mutableStateOf(listOf<TodoItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var todoName by remember { mutableStateOf("") }
   
    Column {
        Button(onClick = {showDialog = true}) {
            Text(text = "Add Todo")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            items(todoItems){
                item -> TodoListItem(item = item, onEditClick = {}, onDeleteClick = {})
            }
        }
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                            Row {
                                Button(onClick = {
                                    val todoItem = TodoItem(
                                        id = todoItems.size,
                                        name = todoName)
                                    todoItems = todoItems + todoItem
                                    todoName = ""
                                    showDialog = false
                                }) {
                                    Text(text = "Add Todo")
                                }
                                Button(onClick = { showDialog = false }) {
                                    Text(text = "Cancel")
                                }
                            }
            },
            title = { Text(text = "Add Todo Item")},
            text = {
                Column {
                    OutlinedTextField(value = todoName, onValueChange = {todoName = it})
                }
            })
    }//end if showDialog
}

@Composable
fun TodoListItem(
    item: TodoItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Row {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Row {
            IconButton(onClick = {onEditClick}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Todo Item")
            }
            IconButton(onClick = {onDeleteClick}) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Todo Item")
            }
        }
    }
}




