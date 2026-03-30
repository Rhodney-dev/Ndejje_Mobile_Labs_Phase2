package com.ndejje.momocalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ndejje.momocalc.ui.theme.MoMoCalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoMoCalculatorAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        InternalStateInput()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun BrokenInput() {
    // This is intentionally broken as it doesn't use remember/mutableStateOf
    var amount by remember { mutableStateOf("0") }
    
    TextField(
        value = amount,
        onValueChange = { amount = it },
        label = {
            Text(stringResource(id = R.string.enter_amount))
        }
    )
}

@Composable
fun InternalStateInput(){
    var amount by remember { mutableStateOf("") }
    TextField(
        value = amount,
        onValueChange = { amount = it },
        label = {
            Text(stringResource(id = R.string.enter_amount))
        }
    )
}

@Composable
fun HoistedAmountInput(
    amount: String,
    onAmountChange: (String) -> Unit,
    isError: Boolean = false
){
    Column {
        TextField(
            value = amount,
            onValueChange = onAmountChange,
            isError = isError,
            supportingText = {
                if (isError) {
                    Text(
                        text = stringResource(id = R.string.error_numbers_only),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            label = {
                Text(stringResource(id = R.string.enter_amount))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyInput() {
    MoMoCalculatorAppTheme {
        HoistedAmountInput(amount = "", onAmountChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewfilledInput() {
    MoMoCalculatorAppTheme {
        HoistedAmountInput(amount = "50000", onAmountChange = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorInput() {
    MoMoCalculatorAppTheme {
        HoistedAmountInput(amount = "Rhodney", onAmountChange = {}, isError = true)
    }
}
