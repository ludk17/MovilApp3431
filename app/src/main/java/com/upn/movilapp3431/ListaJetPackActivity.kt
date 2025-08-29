package com.upn.movilapp3431

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upn.movilapp3431.entities.Contact
import com.upn.movilapp3431.entities.Transaction
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme

class ListaJetPackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val transactions = listOf(
            Transaction(title="Compra en Supermercado", amount = 150.75, date="28/08/25 07:30", type="gasto"),
            Transaction(title="Pago de Servicios", amount = 80.50, date="27/08/25 10:00", type="gasto"),
            Transaction(title="Venta de Artículo", amount = 200.00, date="26/08/25 15:45", type="ingreso"),
        )

        setContent {
            MovilApp3431Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(8.dp)
                    ) {
                        LazyColumn {
                        items(transactions.size) { index ->
                            val transaction = transactions[index]
                            val context = LocalContext.current
                            Card(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(vertical = 10.dp, horizontal = 4.dp)
                                    .clickable{
                                        Toast
                                            .makeText(context, "Seleccionó: ${transaction.title}", Toast.LENGTH_SHORT)
                                            .show()
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column {
                                    Text(
                                        text = transaction.title,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                    Row {
                                        Text(
                                            text = transaction.date,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "S/${transaction.amount}",
                                            color = if (transaction.type == "gasto") Color.Red else Color.Blue,
                                            modifier = Modifier.padding(4.dp)
                                        )
                                    }


                                }
                            }
                        }
                    }
                    }
                }
            }
        }
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovilApp3431Theme {
        Greeting("Android")
    }
}
 */