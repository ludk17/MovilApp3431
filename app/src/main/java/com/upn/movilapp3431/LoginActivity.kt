package com.upn.movilapp3431

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferences = getSharedPreferences("com.upn.movilapp3431", MODE_PRIVATE)

        // como se edita o agrega valores a un shared preferences
//        val editor = preferences.edit()
//        editor.putString("USERNAME", "johndoe")
//        editor.apply() // guardar los cambios


//        // como accedo a los valores del preferences
//        val estaLogueado = preferences.getBoolean("ESTA_LOGUEADO", false)
//        val username = preferences.getString("USERNAME",  null)

        setContent {
            MovilApp3431Theme {

                val context = LocalContext.current;

                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    LaunchedEffect(Unit) {
                        val usernamePref = preferences.getString("USERNAME", null)
                        if (usernamePref != null) {
                            username = usernamePref
                        }
                    }

                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        Text("Inicio de Sesión", style = MaterialTheme.typography.headlineMedium) // 14sp

                        OutlinedTextField(
                            value = username,
                            onValueChange = {it -> username = it},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = {o -> password = o},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            label = { Text("Contraseña") },
                        )
                        Button(
                            onClick = {

                                // validar contra firebase
                                // si credenciales son correctas
                                // guardar en shared preferences que ya inició sesión
                                val editor = preferences.edit()
                                editor.putBoolean("ESTA_LOGUEADO", true)
                                editor.putString("USERNAME", username)
                                editor.apply() // guardar los cambios


                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Ingresar")
                        }
                    }
                }
            }
        }
    }
}
