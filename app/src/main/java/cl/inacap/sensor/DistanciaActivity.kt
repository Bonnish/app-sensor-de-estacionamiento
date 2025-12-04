package cl.inacap.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*

class DistanciaActivity : AppCompatActivity() {

    private lateinit var txtDistancia: TextView
    private lateinit var btnVolver: Button
    private lateinit var btnSensor: Button
    private lateinit var refDistancia: DatabaseReference
    private lateinit var refSensorActivo: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distancia)

        txtDistancia = findViewById(R.id.txtDistancia)
        btnVolver = findViewById(R.id.btnVolver)
        btnSensor = findViewById(R.id.btnSensor)

        btnVolver.setOnClickListener { finish() }

        refDistancia = FirebaseDatabase.getInstance().reference
            .child("ESP32")
            .child("distancia")

        refSensorActivo = FirebaseDatabase.getInstance().reference
            .child("ESP32")
            .child("sensorActivo")

        btnSensor.setOnClickListener {
            refSensorActivo.get().addOnSuccessListener { snap ->
                val actual = snap.getValue(Boolean::class.java) ?: false
                val nuevo = !actual

                refSensorActivo.setValue(nuevo)

                if (nuevo) {
                    Toast.makeText(this, "Sensor ACTIVADO", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Sensor DESACTIVADO", Toast.LENGTH_SHORT).show()
                    txtDistancia.text = "SENSOR APAGADO"
                }
            }
        }

        refSensorActivo.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val activo = snapshot.getValue(Boolean::class.java) ?: false
                if (!activo) {
                    txtDistancia.text = "SENSOR APAGADO"
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        refDistancia.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val d = snapshot.getValue(Double::class.java) ?: 0.0

                refSensorActivo.get().addOnSuccessListener { snap ->
                    val activo = snap.getValue(Boolean::class.java) ?: false

                    if (activo) {
                        txtDistancia.text = "Distancia: ${d} cm"
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
