package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.example.proyectofinalgrupo4_v21fin.model.UsuarioModel
import android.widget.TextView

class NavigationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        auth = FirebaseAuth.getInstance()

        val ivRestaurantes: ImageView = findViewById(R.id.ivRestaurantes)
        val ivSupermercados: ImageView = findViewById(R.id.ivSupermercados)
        val tvUserName: TextView = findViewById(R.id.tvUserName)

        val currentUser: FirebaseUser? = auth.currentUser

        currentUser?.let { firebaseUser ->
            val uid = firebaseUser.uid
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("Usuarios").document(uid)

            userRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val usuario = documentSnapshot.toObject(UsuarioModel::class.java)
                        val nombreUsuario = usuario?.nombre ?: "Usuario"
                        tvUserName.text = "Bienvenido, $nombreUsuario"
                    } else {
                        tvUserName.text = "Bienvenido, Usuario"
                    }
                }
                .addOnFailureListener { exception ->
                    tvUserName.text = "Error: ${exception.message}"
                }
        }

        ivRestaurantes.setOnClickListener {
            val intent = Intent(this, RestauranteActivity::class.java)
            startActivity(intent)
        }

        ivSupermercados.setOnClickListener {
            val intent = Intent(this, SupermercadoActivity::class.java)
            startActivity(intent)
        }
    }
}
