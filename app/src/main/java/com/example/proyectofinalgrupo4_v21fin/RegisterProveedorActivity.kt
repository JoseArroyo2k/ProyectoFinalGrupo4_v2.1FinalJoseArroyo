package com.example.proyectofinalgrupo4_v21fin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalgrupo4_v21fin.model.SupermercadosModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterProveedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_proveedor)

        val txtNombreProveedor: EditText = findViewById(R.id.txtNombreProveedor)
        val txtRUC: EditText = findViewById(R.id.txtRUC)
        val txtDireccion: EditText = findViewById(R.id.txtDireccion)
        val txtUrlImagen: EditText = findViewById(R.id.txtUrlImagen)
        val txtClaveProveedor: EditText = findViewById(R.id.txtClaveProveedor)
        val txtCorreoProveedor: EditText = findViewById(R.id.txtCorreoProveedor)
        val btnRegistroProveedor2: Button = findViewById(R.id.btnRegistroProveedor2)
        val radioSupermercado: RadioButton = findViewById(R.id.radioSupermercado)
        val radioRestaurante: RadioButton = findViewById(R.id.radioRestaurante)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        val collectionRef = db.collection("Supermercados") // Asegúrate de cambiar "Supermercados" al nombre correcto de tu colección

        btnRegistroProveedor2.setOnClickListener {
            val nombreProveedor = txtNombreProveedor.text.toString()
            val ruc = txtRUC.text.toString()
            val direccion = txtDireccion.text.toString()
            val urlImagen = txtUrlImagen.text.toString()
            val claveProveedor = txtClaveProveedor.text.toString()
            val correoProveedor = txtCorreoProveedor.text.toString()
            val tipoTienda = if (radioSupermercado.isChecked) "Supermercado" else "Restaurante"

            auth.createUserWithEmailAndPassword(correoProveedor, claveProveedor)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid

                        val proveedorModel = SupermercadosModel(
                            nombre = nombreProveedor,
                            imagen = urlImagen,
                            ruc = ruc,
                            direccion = direccion,
                            clave = claveProveedor,
                            tipo = "proveedor",
                            tipotienda = tipoTienda,
                            correo = correoProveedor
                        )

                        collectionRef.document(uid ?: "").set(proveedorModel)
                            .addOnCompleteListener {
                                Snackbar
                                    .make(
                                        findViewById(android.R.id.content),
                                        "Registro exitoso del proveedor",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                            }.addOnFailureListener { error ->
                                Snackbar
                                    .make(
                                        findViewById(android.R.id.content),
                                        "Error al registrar en Firestore: ${error.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                            }
                    } else {
                        Snackbar
                            .make(
                                findViewById(android.R.id.content),
                                "Error al registrar en Firebase Auth: ${task.exception?.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                    }
                }
        }
    }
}
