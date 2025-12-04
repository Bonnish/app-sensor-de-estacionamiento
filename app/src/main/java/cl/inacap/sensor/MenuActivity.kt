package cl.inacap.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import android.content.Intent
class MenuActivity : AppCompatActivity() {

    private lateinit var txtBienvenida: TextView
    private lateinit var btnDistancia: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        txtBienvenida = findViewById(R.id.txtBienvenida)
        btnDistancia = findViewById(R.id.btnDistancia)

        val nombre = intent.getStringExtra("usuario")
        txtBienvenida.text = "Bienvenido, $nombre"

        val sensorRef = FirebaseDatabase.getInstance().reference
            .child("ESP32")
            .child("sensorActivo")


        btnDistancia.setOnClickListener {
            val intent = Intent(this, DistanciaActivity::class.java)
            startActivity(intent)

        }
    }
}

