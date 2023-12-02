package com.example.proyectofinalgrupo4_v21fin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalgrupo4_v21fin.model.PedidosModel
import com.google.firebase.firestore.FirebaseFirestore

class GestionPedidosActivity : AppCompatActivity() {

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_pedidos)

        obtenerPedidosDesdeFirebase()
    }

    private fun obtenerPedidosDesdeFirebase() {
        val listaPedidos = mutableListOf<PedidosModel>()

        // Referencia a la colección "pedidos" en Firebase Firestore
        firestore.collection("pedidos")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Obtener cada documento y convertirlo a un objeto PedidosModel
                    val pedido = document.toObject(PedidosModel::class.java)
                    listaPedidos.add(pedido)
                }

                mostrarPedidosEnListView(listaPedidos)
            }
            .addOnFailureListener { exception ->
                // Manejar errores aquí
            }
    }

    private fun mostrarPedidosEnListView(listaPedidos: List<PedidosModel>) {
        val listaInformacionPedidos = mutableListOf<String>()
        for (pedido in listaPedidos) {
            val infoPedido = "${pedido.estado} - ${pedido.producto} - ${pedido.monto} - ${pedido.hora}"
            listaInformacionPedidos.add(infoPedido)
        }

        val listviewPedidos = findViewById<ListView>(R.id.listviewPedidos)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacionPedidos)
        listviewPedidos.adapter = adapter


        // Configurar el clic del botón de retroceso
        val btnBackPedidos = findViewById<Button>(R.id.btnBackPedidos)
        btnBackPedidos.setOnClickListener {
            onBackPressed()
        }
    }
}
