package com.upn.movilapp3431

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme
import androidx.core.content.edit
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.upn.movilapp3431.entities.Contact
import com.upn.movilapp3431.entities.User

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferences = getSharedPreferences("com.upn.movilapp3431", MODE_PRIVATE)
        val estaLogueado = preferences.getBoolean("ESTA_LOGUEADO", false)

//        // como accedo a los valores del preferences
//        val estaLogueado = preferences.getBoolean("ESTA_LOGUEADO", false)
//        val username = preferences.getString("USERNAME",  null)

        setContent {
            MovilApp3431Theme {

                val context = LocalContext.current

                if (estaLogueado) {
                    val intent = Intent(context, FirebaseRealtimeDatabaseActivity::class.java)
                    context.startActivity(intent)
                    finish()
                }


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

                                val database = Firebase.database
                                val usersRef = database.getReference("users")

                                usersRef.addValueEventListener(object : ValueEventListener {
                                    var loggedUser: User? = null
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        for (item in dataSnapshot.children) {
                                            val user = item.getValue(User::class.java)!!
                                             if (user.password == password && user.username == username) {
                                                 loggedUser = user
                                             }
                                        }

                                        if (loggedUser == null) {
                                            Toast.makeText(context, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Usuario correcto", Toast.LENGTH_SHORT).show()
                                        }

                                    }

                                    override fun onCancelled(error: DatabaseError) {

                                    }
                                })



//                                val userRef = usersRef.child(username)
//                                userRef.get().addOnSuccessListener { dataSnapshot ->
//                                    if (dataSnapshot.exists()) {
//
//                                        Log.i("MAIN_APP", dataSnapshot.value.toString())
//
//                                        val user = dataSnapshot.getValue(User::class.java)!!
//
//                                        if (user.password == password) {
//                                            preferences.edit {
//                                                putBoolean("ESTA_LOGUEADO", true)
//                                                putString("USERNAME", username)
//                                                putString("ROLE", user.role)
//                                            }
//
//                                            val intent = Intent(
//                                                context,
//                                                FirebaseRealtimeDatabaseActivity::class.java
//                                            )
//                                            context.startActivity(intent)
//                                            finish()
//                                        } else {
//                                            Toast.makeText(context, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show()
//                                        }
//
//                                    } else {
//                                        Log.i("MAIN_APP", "No existe $username")
//                                        Toast.makeText(context, "Usuario y contraseña incorrectos", Toast.LENGTH_SHORT).show()
//                                    }
//                                }


                                // validar contra firebase


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
