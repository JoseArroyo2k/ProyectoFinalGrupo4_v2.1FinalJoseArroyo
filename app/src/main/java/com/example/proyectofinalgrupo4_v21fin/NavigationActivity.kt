package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.proyectofinalgrupo4_v21fin.RestauranteActivity
import com.example.proyectofinalgrupo4_v21fin.SupermercadoActivity

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val ivRestaurantes: ImageView = findViewById(R.id.ivRestaurantes)
        val ivSupermercados: ImageView = findViewById(R.id.ivSupermercados)

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
