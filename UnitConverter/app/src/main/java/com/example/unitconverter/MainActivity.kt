package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter(){
    var inputValue by remember{mutableStateOf("")}
    var outputValue by remember{mutableStateOf("")}
    var inputUnit by remember{mutableStateOf("Select")}
    var outputUnit by remember{ mutableStateOf("Select") }
    var iExpanded by remember{ mutableStateOf(false) }
    var oExpanded by remember{ mutableStateOf(false) }
    var iRate by remember{ mutableDoubleStateOf(0.00) }
    var oRate by remember{ mutableDoubleStateOf(0.00) }

    fun convertUnits(){
        var inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        var rate = 0.00
        if(oRate != 0.00){
            rate = iRate / oRate
        }
        var result = inputValueDouble * rate
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //đặt item ở đây sẽ xuống dòng
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge.copy(color = Color.Red))
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnits()
            },
            label = {Text("Enter Value")}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            //đặt items ở đây sẽ xếp cạnh nhau
           Box {
               Button(onClick = {iExpanded = true}) {
                   Text(text = inputUnit)
                   Icon(Icons.Default.ArrowDropDown, contentDescription = null)
               }
               DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                   DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                       inputUnit = "Centimeters"
                       iExpanded = false
                       iRate = 0.01
                       convertUnits()
                   })
                   DropdownMenuItem(text = { Text(text = "Meters") }, onClick = {
                       inputUnit = "Meters"
                       iExpanded = false
                       iRate = 1.00
                       convertUnits()
                   })
                   DropdownMenuItem(text = { Text(text = "Millimeters") }, onClick = {
                       inputUnit = "Millimeters"
                       iExpanded = false
                       iRate = 0.001
                       convertUnits()
                   })
               }
           }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = {oExpanded = true}) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(text = { Text(text = "Centimeters") }, onClick = {
                        outputUnit = "Centimeters"
                        oExpanded = false
                        oRate = 0.01
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text(text = "Meters") }, onClick = {
                        outputUnit = "Meters"
                        oExpanded = false
                        oRate = 1.00
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text(text = "Millimeters") }, onClick = {
                        outputUnit = "Millimeters"
                        oExpanded = false
                        oRate = 0.001
                        convertUnits()
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if(outputUnit  == "Select"){
            Text("Result is: $outputValue", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Magenta))
        }else{
            Text("Result is: $outputValue $outputUnit", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Magenta))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}