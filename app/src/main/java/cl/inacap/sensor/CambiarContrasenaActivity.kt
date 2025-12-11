package cl.inacap.sensor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CambiarContrasenaActivity : AppCompatActivity() {

    private lateinit var txtPassActual: EditText
    private lateinit var txtPassNueva: EditText
    private lateinit var txtPassRepetir: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cambiar_contrasena)

        txtPassActual = findViewById(R.id.txtPassActual)
        txtPassNueva = findViewById(R.id.txtPassNueva)
        txtPassRepetir = findViewById(R.id.txtPassRepetir)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            actualizarContrasena()
        }
    }

    private fun actualizarContrasena() {
        val actual = txtPassActual.text.toString()
        val nueva = txtPassNueva.text.toString()
        val repetir = txtPassRepetir.text.toString()

        if (actual.isEmpty() || nueva.isEmpty() || repetir.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        if (nueva != repetir) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        val userRef = FirebaseDatabase.getInstance()
            .getReference("ESP32")
            .child("Usuarios")
            .child("1")

        userRef.get().addOnSuccessListener { snapshot ->
            val passActualFirebase = snapshot.child("password").value?.toString()

            if (passActualFirebase == null) {
                Toast.makeText(this, "No se encontró la contraseña del usuario", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            if (passActualFirebase != actual) {
                Toast.makeText(this, "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            userRef.child("password").setValue(nueva)
                .addOnSuccessListener {
                    Toast.makeText(this, "Contraseña actualizada correctamente", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
