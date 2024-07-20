package com.example.shoppinglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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

data class ShoppingItem(
    val id:Int,
    var name:String,
    var quantity:Int,
    val isEditing:Boolean = false
)

@Composable
fun ShoppingList() {
    var sItems by remember{ mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember{ mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Button(onClick = { showDialog = true}, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Add Item")
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            items(sItems){
                item ->
                if (item.isEditing){
                    ShoppingListItemEditor(item = item, onEditComplete = {
                        editedName, editedQuantity ->
                        //sửa trạng thái isEditing của tất cả các item trong danh sách thành false hết, để đảm bảo ko có item nào đang ở trạng thái "đang chỉnh sửa" nữa
                        sItems = sItems.map { it.copy(isEditing = false)}

                        //tìm kiếm đúng item cần sửa (item có isEditing = true), bằng hàm bên dưới. Code phía trên không ảnh hưởng đến isEditing của item này nên item này vẫn có isEditing = true
                        //sItems.find { it.id == item.id } nghĩa là duyệt tìm trong danh sách item nào có id bằng với id của item (item.id)
                        val editedItem = sItems.find { it.id == item.id }

                        //Sau khi tìm ra thì thay đổi nó chỉ bên trong phạm vi của editedItem
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuantity
                        }
                    })
                }//end if
                else{
                    ShoppingListItem(
                        item = item,
                        onEditClick = {
                            //tìm ra đúng item đã click và chuyển isEditing của nó thành true, nếu thành true thì sẽ chạy code dòng 59
                            sItems = sItems.map { it.copy(isEditing = it.id==item.id) }
                        },
                        onDeleteClick = {
                            sItems = sItems - item
                        }
                    );
                }//end else
            }
        }
    }

    if (showDialog){
        AlertDialog(onDismissRequest = {showDialog = false},
            confirmButton = {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Button(onClick = {
                                    if(itemName.isNotBlank()){
                                        val sItem = ShoppingItem(
                                            id = sItems.size + 1,
                                            name = itemName,
                                            quantity = itemQty.toIntOrNull() ?: 0)
                                        sItems = sItems + sItem
                                        showDialog = false
                                        itemName = ""
                                        itemQty = ""
                                    }
                                }) {
                                    Text(text = "Add Item")
                                }
                                Button(onClick = {
                                    showDialog = false
                                    itemName = ""
                                    itemQty = ""
                                }) {
                                    Text(text = "Cancel")
                                }
                            }
            },
            title = { Text(text = "Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(value = itemName ,
                        onValueChange = {itemName = it},
                        singleLine = true,
                        modifier = Modifier.padding(8.dp),
                        label = { Text(text = "Enter a name")}
                    )

                    OutlinedTextField(value = itemQty ,
                        onValueChange = {itemQty = it},
                        singleLine = true,
                        modifier = Modifier.padding(8.dp),
                        label = { Text(text = "Enter a quantity")}
                    )
                }
            }
        )

    }
}

@Composable
fun ShoppingListItemEditor(
    item: ShoppingItem,
    onEditComplete: (String, Int) -> Unit
){
    var editedName by remember{ mutableStateOf(item.name) }
    var editedQuantity by remember{ mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }
    Row (modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp)){
        Column {
            OutlinedTextField(
                value = editedName,
                onValueChange = {editedName = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = editedQuantity,
                onValueChange = {editedQuantity = it},
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            Button(onClick = {
                isEditing = false
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(BorderStroke(2.dp, Color.Cyan), shape = RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "SL: ${item.quantity}", modifier = Modifier.padding(8.dp))
        Row (modifier = Modifier.padding(8.dp)){
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, "Edit Shopping List Item")
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, "Delete Shopping List Item")
            }
        }
    }
}