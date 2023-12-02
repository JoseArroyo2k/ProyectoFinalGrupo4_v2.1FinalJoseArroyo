package com.example.proyectofinalgrupo4_v21fin

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalgrupo4_v21fin.model.ProductosModel
import com.google.firebase.firestore.FirebaseFirestore

class ProductosActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var txtStoreName: TextView
    private lateinit var btnBackToStores: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        listView = findViewById(R.id.listviewProductos)
        txtStoreName = findViewById(R.id.txtStoreName)
        btnBackToStores = findViewById(R.id.btnBackToStores)
        db = FirebaseFirestore.getInstance()

        val storeName = intent.getStringExtra("storeName")

        txtStoreName.text = storeName

        val productosList = ArrayList<ProductosModel>() // Lista para almacenar productos

        // Consulta a Firestore para obtener los productos de la tienda seleccionada
        db.collection("Productos")
            .whereEqualTo("tienda", storeName)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val producto = document.toObject(ProductosModel::class.java)
                    productosList.add(producto)
                }
                // Crear un ArrayAdapter personalizado para mostrar los nombres de los productos
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    productosList.map { it.nombre } // Obtener solo los nombres de los productos
                )
                listView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                // Manejo de errores al obtener los datos de Firestore
            }

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // Obtiene el producto seleccionado de la lista
            val selectedProduct = productosList[position]

            // Crea un Intent para iniciar DetalleProductoActivity
            val intent = Intent(this, DetalleProductoActivity::class.java)

            // Pasa los detalles del producto a través del Intent
            intent.putExtra("productName", selectedProduct.nombre)
            intent.putExtra("productDescription", selectedProduct.descripcion)
            intent.putExtra("productPrice", selectedProduct.precio)

            // Inicia la actividad DetalleProductoActivity
            startActivity(intent)
        }

        btnBackToStores.setOnClickListener {
            onBackPressed()
        }


    }
}
