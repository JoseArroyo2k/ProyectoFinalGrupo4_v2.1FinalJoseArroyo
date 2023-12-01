package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val btnReg: Button = findViewById(R.id.btnRegistro)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtCorreo: EditText = findViewById(R.id.txtUsuario)
        val txtClave: EditText = findViewById(R.id.txtClave)

        val btnRegProveedor: Button = findViewById(R.id.btnRegistroProveedor)

        btnReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = txtCorreo.text.toString()
            val password = txtClave.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser

                        if (currentUser != null) {
                            val uid = currentUser.uid

                            db.collection("Usuarios").document(uid)
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document != null && document.exists()) {
                                        val tipo = document.getString("tipo")
                                        if (tipo != null && tipo == "proveedor") {
                                            startActivity(Intent(this, NavigationProveedoresActivity::class.java))
                                            finish()
                                        } else {
                                            startActivity(Intent(this, NavigationActivity::class.java))
                                            finish()
                                        }
                                    } else {
                                        db.collection("Supermercados").document(uid)
                                            .get()
                                            .addOnSuccessListener { providerDoc ->
                                                if (providerDoc != null && providerDoc.exists()) {
                                                    startActivity(Intent(this, NavigationProveedoresActivity::class.java))
                                                    finish()
                                                } else {
                                                    Snackbar.make(
                                                        findViewById(android.R.id.content),
                                                        "Error al obtener información del usuario",
                                                        Snackbar.LENGTH_LONG
                                                    ).show()
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                Snackbar.make(
                                                    findViewById(android.R.id.content),
                                                    "Error al obtener información del usuario: ${exception.message}",
                                                    Snackbar.LENGTH_LONG
                                                ).show()
                                            }
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Error al obtener información del usuario: ${exception.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                        }
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Credenciales inválidas",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }

        btnRegProveedor.setOnClickListener {
            startActivity(Intent(this, RegisterProveedorActivity::class.java))
        }
    }
}
