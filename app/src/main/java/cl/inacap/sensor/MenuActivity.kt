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
    private lateinit var btnSensor: Button
    private lateinit var btnDistancia: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        txtBienvenida = findViewById(R.id.txtBienvenida)
        btnSensor = findViewById(R.id.btnSensor)
        btnDistancia = findViewById(R.id.btnDistancia)

        val nombre = intent.getStringExtra("usuario")
        txtBienvenida.text = "Bienvenido, $nombre"

        val sensorRef = FirebaseDatabase.getInstance().reference
            .child("ESP32")
            .child("sensorActivo")

        btnSensor.setOnClickListener {

            sensorRef.get().addOnSuccessListener { snapshot ->
                val estadoActual = snapshot.getValue(Boolean::class.java) ?: false
                val nuevoEstado = !estadoActual  // Invierte el estado

                sensorRef.setValue(nuevoEstado)

                if (nuevoEstado) {
                    Toast.makeText(this, "Sensor ACTIVADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Sensor DESACTIVADO", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnDistancia.setOnClickListener {
            val intent = Intent(this, DistanciaActivity::class.java)
            startActivity(intent)
        }
    }
}
