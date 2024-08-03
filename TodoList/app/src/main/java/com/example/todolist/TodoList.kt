package com.example.todolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class TodoItem(
    val id: Int,
    var name: String,
    val isEditing:Boolean = false
)

@Composable
fun TodoList() {
    var todoItems by remember { mutableStateOf(listOf<TodoItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var todoName by remember { mutableStateOf("") }
   
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {showDialog = true}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Add Todo")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            items(todoItems){
                item ->
                if(item.isEditing){
                    TodoListItemEditor(item = item, onEditComplete = {
                        editedTodo ->
                        //chuyển tất cả danh sách thành trạng thái ko sửa
                        todoItems = todoItems.map { it.copy(isEditing = false) }

                        //tìm đúng item cần sửa
                        val editedItem = todoItems.find { it.id == item.id }

                        //sửa nó
                        editedItem?.let { it.name = editedTodo }
                    })
                }else{
                    TodoListItem(
                        item = item,
                        onEditClick = {
                            todoItems = todoItems.map { it.copy(isEditing = it.id == item.id) }
                        },
                        onDeleteClick = {
                            todoItems = todoItems - item
                        }
                    )
                }
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
fun TodoListItemEditor(
    item: TodoItem,
    onEditComplete: (String) -> Unit
){
    var editedTodo by remember{ mutableStateOf(item.name) }
    var isEditing by remember { mutableStateOf(item.isEditing) }
    Row {
        Column {
            OutlinedTextField(value = editedTodo, onValueChange = {editedTodo = it})
            Button(onClick = {
                isEditing = false
                onEditComplete(editedTodo)
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun TodoListItem(
    item: TodoItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(BorderStroke(2.dp, Color.Cyan), shape = RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Row {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Todo Item")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Todo Item")
            }
        }
    }
}




