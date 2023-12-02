package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class NavigationProveedoresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_proveedores)

        // Obtiene las referencias a los ImageView
        val ivPedidosGenerados = findViewById<ImageView>(R.id.ivPedidosGenerados)
        val ivMisProductos = findViewById<ImageView>(R.id.ivMisProductos)

        // Establece los listeners de clic para los ImageView
        ivPedidosGenerados.setOnClickListener {
            // Crea un Intent para GestionPedidosActivity y lo inicia
            val intent = Intent(this, GestionPedidosActivity::class.java)
            startActivity(intent)
        }

        ivMisProductos.setOnClickListener {
            // Crea un Intent para GestionProductosActivity y lo inicia
            val intent = Intent(this, GestionProductosActivity::class.java)
            startActivity(intent)
        }
    }
}
