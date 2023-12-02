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

class SupermercadoActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supermercado)

        listView = findViewById(R.id.listviewSupermercados)
        btnBack = findViewById(R.id.btnBack)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val supermercadosList = ArrayList<SupermercadosModel>()

        val supermercadosRef = db.collection("Supermercados")
            .whereEqualTo("tipotienda", "Supermercado")

        supermercadosRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val supermercado = document.toObject(SupermercadosModel::class.java)
                    supermercadosList.add(supermercado)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, supermercadosList.map { it.nombre })
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

        // Agregar escucha de clics a la lista
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedStore = supermercadosList[position] // Obtenemos la tienda seleccionada
            val intent = Intent(this, ProductosActivity::class.java).apply {
                putExtra("storeName", selectedStore.nombre) // Pasamos el nombre de la tienda a ProductosActivity
            }
            startActivity(intent) // Iniciamos ProductosActivity al hacer clic en un elemento
        }
    }
}
