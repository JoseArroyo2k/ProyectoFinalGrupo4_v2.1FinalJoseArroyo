package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DetalleProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        val txtProductName = findViewById<TextView>(R.id.txtProductName)
        val txtProductDescription = findViewById<TextView>(R.id.txtProductDescription)
        val txtProductPrice = findViewById<TextView>(R.id.txtProductPrice)
        val btnBuy = findViewById<Button>(R.id.btnBuy)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Obtener los valores pasados desde ProductosActivity
        val productName = intent.getStringExtra("productName")
        val productDescription = intent.getStringExtra("productDescription")
        val productPrice = intent.getStringExtra("productPrice")

        // Establecer los valores en los TextViews correspondientes
        txtProductName.text = productName
        txtProductDescription.text = productDescription
        txtProductPrice.text = "Precio: $productPrice" // Agregar el prefijo "Precio: " al valor del precio

        // Configurar el botón de regreso
        btnBack.setOnClickListener {
            onBackPressed()
        }

        // Configurar el botón de compra
        btnBuy.setOnClickListener {
            // Crear un Intent para ir a PagosActivity y pasar los datos adicionales como extras
            val pagosIntent = Intent(this, PagosActivity::class.java)

            // Obtener la hora actual y formatearla
            val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val currentTime = sdf.format(Date())

            // Pasar datos a PagosActivity como extras
            pagosIntent.putExtra("montoFinal", productPrice)
            pagosIntent.putExtra("nombreProducto", productName)
            pagosIntent.putExtra("horaActual", currentTime)
            pagosIntent.putExtra("estado", "Revision")

            startActivity(pagosIntent)
        }
    }
}
