package com.example.proyectofinalgrupo4_v21fin

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.proyectofinalgrupo4_v21fin.model.SupermercadosModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RestauranteActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurante)

        listView = findViewById(R.id.listviewRestaurantes)
        btnBack = findViewById(R.id.btnBack)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val restaurantesList = ArrayList<SupermercadosModel>() // Cambiamos el nombre para evitar duplicados

        val restaurantesRef = db.collection("Supermercados")
            .whereEqualTo("tipotienda", "Restaurante") // Cambiamos el valor a "Restaurante"

        restaurantesRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val restaurante = document.toObject(SupermercadosModel::class.java) // Mantenemos el modelo
                    restaurantesList.add(restaurante)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurantesList.map { it.nombre })
                listView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Manejo de errores al obtener los datos de Firestore
            }

        btnBack.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
