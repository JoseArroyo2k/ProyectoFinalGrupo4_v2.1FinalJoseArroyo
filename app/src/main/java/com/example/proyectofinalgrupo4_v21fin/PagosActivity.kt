package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalgrupo4_v21fin.model.PedidosModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class PagosActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagos)

        db = FirebaseFirestore.getInstance()

        val btnPay = findViewById<Button>(R.id.btnPay)

        btnPay.setOnClickListener {
            val estado = "Revision" // Estado fijo para esta transacción
            val producto = intent.getStringExtra("nombreProducto")
            val monto = intent.getStringExtra("montoFinal")

            // Obtener la hora actual y formatearla
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val hora = sdf.format(Date())

            // Crear el objeto PedidosModel con los datos obtenidos
            val pedido = PedidosModel(estado, producto.orEmpty(), monto.orEmpty(), hora)

            // Insertar el pedido en Firestore
            db.collection("Pedidos")
                .add(pedido)
                .addOnSuccessListener { documentReference ->
                    // Mostrar mensaje simulado
                    Toast.makeText(this, "CONFIRMANDO PAGO...", Toast.LENGTH_SHORT).show()

                    // Simular un retraso de 3 segundos antes de ir a la próxima actividad
                    Handler().postDelayed({
                        Toast.makeText(this, "Pago Confirmado", Toast.LENGTH_SHORT).show()

                        // Éxito al agregar el pedido
                        val intent = Intent(this@PagosActivity, NavigationActivity::class.java)
                        startActivity(intent)
                        finish() // Opcional, dependiendo de la lógica de tu app
                    }, 3000)
                }
                .addOnFailureListener { e ->
                    // Error al agregar el pedido
                    // Manejar el fallo de inserción si es necesario
                }
        }
    }
}
