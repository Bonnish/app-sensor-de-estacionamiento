package cl.inacap.sensor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase

class Principal : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        btnLogin = findViewById(R.id.btnLogin)

        val database = FirebaseDatabase.getInstance().reference

        btnLogin.setOnClickListener {
            val emailInput = txtEmail.text.toString().trim()
            val passInput = txtPassword.text.toString().trim()

            if (emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuariosRef = database.child("ESP32").child("Usuarios")

            usuariosRef.get().addOnSuccessListener { snapshot ->
                var encontrado = false

                for (userSnap in snapshot.children) {
                    val email = userSnap.child("email").value.toString().trim()
                    val password = userSnap.child("password").value.toString().trim()

                    if (email == emailInput && password == passInput) {
                        encontrado = true
                        val nombreUsuario = userSnap.child("usuario").value.toString()
                        val userId = userSnap.key.toString()
                        val intent = Intent(this, MenuActivity::class.java)
                        intent.putExtra("usuario", nombreUsuario)
                        intent.putExtra("userId", userId)
                        startActivity(intent)
                        finish()
                    }


                }

                if (!encontrado) {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }

        }
    }
}
