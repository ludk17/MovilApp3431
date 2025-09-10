package com.upn.movilapp3431

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.upn.movilapp3431.entities.Contact
import com.upn.movilapp3431.ui.theme.MovilApp3431Theme

class FirebaseRealtimeDatabaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovilApp3431Theme {

                val contacts = remember { mutableStateListOf<Contact>() }
//                val contacts = mutableListOf<Contact>()

                val database = Firebase.database
                val myRef = database.getReference("contacts")

//                val contact = Contact("2", "Juana Perez", "123456789", "2024-06-10")
//                val record = myRef.child("contacts").push()
//                contact.id = record.key.toString()
//                record.setValue(contact)


                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val tempContacts = mutableListOf<Contact>()
                        for (item in dataSnapshot.children) {
                            Log.d("MAIN_APP", "Value is: $item")
                            val contact = item.getValue(Contact::class.java)
                            tempContacts.add(contact!!)
                        }

                        contacts.addAll(tempContacts);

                        Log.d("MAIN_APP", "Value is: ${contacts.size}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("MAIN_APP", "Failed to read value.", error.toException())
                    }
                })


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        LazyColumn {
                            items(contacts) { contact ->
                                Text(text = "${contact.name} - ${contact.phone}")
                            }
                        }
                    }
                }
            }
        }
    }
}
