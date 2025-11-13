package cl.inacap.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import com.google.firebase.database.*

class DistanciaActivity : AppCompatActivity() {

    private lateinit var txtDistancia: TextView
    private lateinit var btnVolver: Button
    private lateinit var refDistancia: DatabaseReference
    private lateinit var refSensorActivo: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distancia)

        txtDistancia = findViewById(R.id.txtDistancia)
        btnVolver = findViewById(R.id.btnVolver)

        btnVolver.setOnClickListener {
            finish()
        }

        refDistancia = FirebaseDatabase.getInstance().reference
            .child("ESP32")
            .child("distancia")

        refSensorActivo = FirebaseDatabase.getInstance().reference
            .child("ESP32")
            .child("sensorActivo")

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

                refSensorActivo.get().addOnSuccessListener { activoSnap ->
                    val activo = activoSnap.getValue(Boolean::class.java) ?: false

                    if (activo) {
                        txtDistancia.text = "Distancia: ${d} cm"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
