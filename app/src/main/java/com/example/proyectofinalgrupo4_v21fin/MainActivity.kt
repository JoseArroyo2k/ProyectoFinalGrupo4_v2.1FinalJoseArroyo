package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()

        val btnReg: Button = findViewById(R.id.btnRegistro)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val txtCorreo: EditText = findViewById(R.id.txtUsuario)
        val txtClave: EditText = findViewById(R.id.txtClave)

        btnReg.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

        }

    }
}