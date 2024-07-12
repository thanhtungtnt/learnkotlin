package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMICalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BMICalculator()
                }
            }
        }
    }
}

@Composable
fun BMICalculator(){
    var heightValue by remember{ mutableStateOf("") }
    var weightValue by remember{ mutableStateOf("") }
    var bmiValue by remember{ mutableStateOf("") }
    var bmiNote by remember{ mutableStateOf("") }

    fun calcBMI(){
        var h = heightValue.toDoubleOrNull() ?: 0.0
        var w = weightValue.toDoubleOrNull() ?: 0.0
        var bmi = 0.0
        if(h != 0.0){
            bmi = w / (h*h)
        }
        bmiValue = String.format("%.2f", bmi)

        bmiNote = when {
            bmi < 16 -> "Severe Thinness"
            bmi in 16.0..16.99 -> "Moderate Thinness"
            bmi in 17.0..18.49 -> "Mild Thinness"
            bmi in 18.5..24.99 -> "Normal"
            bmi in 25.0..29.99 -> "Overweight"
            bmi in 30.0..34.99 -> "Obese Class I"
            bmi in 35.0..39.99 -> "Obese Class II"
            bmi >= 40 -> "Obese Class III"
            else -> "Unknown"
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("BMI Calculator", style = MaterialTheme.typography.headlineMedium.copy(
            color = Color.Red, fontWeight = FontWeight.Bold
        ))
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Height (m)")
            Spacer(modifier = Modifier.width(10.dp))
            TextField(
                value = heightValue,
                onValueChange = {
                    heightValue = it
                    calcBMI()
                })
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Weight (kg)")
            Spacer(modifier = Modifier.width(10.dp))
            TextField(
                value = weightValue,
                onValueChange = {
                    weightValue = it
                    calcBMI()
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "BMI = $bmiValue - $bmiNote", style = MaterialTheme.typography.headlineSmall.copy(color = Color(0xFFFFA500)))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        BMICalculator()
    }
}