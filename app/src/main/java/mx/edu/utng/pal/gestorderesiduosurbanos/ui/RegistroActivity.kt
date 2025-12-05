package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaRegistro() }
    }
}

@Composable
fun PantallaRegistro() {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var telefono by remember { mutableStateOf("") }
    var nombreCompleto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFF5A7F5B), Color(0xFF446247))
    )

    Scaffold { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
        ) {

            /** Botón regresar */
            IconButton(
                onClick = { (context as? Activity)?.finish() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
            }

            /** CARD central */
            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "Crear Cuenta",
                        fontSize = 26.sp,
                        color = Color(0xFF446247),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    /** TELEFONO */
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Número de teléfono") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    /** NOMBRE */
                    OutlinedTextField(
                        value = nombreCompleto,
                        onValueChange = { nombreCompleto = it },
                        label = { Text("Nombre completo") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    /** CORREO */
                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico (solo @gmail.com)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    /** CONTRASEÑA */
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña (mínimo 6 caracteres)") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    /** CONFIRMAR */
                    OutlinedTextField(
                        value = confirmarContrasena,
                        onValueChange = { confirmarContrasena = it },
                        label = { Text("Confirmar contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(28.dp))

                    /** BOTÓN REGISTRAR */
                    Button(
                        onClick = {

                            if (telefono.isBlank() || nombreCompleto.isBlank() ||
                                correo.isBlank() || contrasena.isBlank() ||
                                confirmarContrasena.isBlank()
                            ) {
                                Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (!correo.endsWith("@gmail.com")) {
                                Toast.makeText(context, "El correo debe ser @gmail.com", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (contrasena != confirmarContrasena) {
                                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (contrasena.length < 6) {
                                Toast.makeText(context, "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            val email = correo.trim()

                            auth.createUserWithEmailAndPassword(email, contrasena)
                                .addOnSuccessListener { result ->

                                    val uid = result.user?.uid ?: return@addOnSuccessListener

                                    val rol = if (email == "admin@gmail.com") "admin" else "usuario"

                                    val datos = mapOf(
                                        "id" to uid,
                                        "nombreCompleto" to nombreCompleto.trim(),
                                        "nombreUsuario" to email,
                                        "numeroTelefono" to telefono.trim(),
                                        "rol" to rol
                                    )

                                    db.collection("usuarios")
                                        .document(uid)
                                        .set(datos)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,
                                                "Registro completado",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            (context as? Activity)?.finish()
                                        }

                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                                }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247))
                    ) {
                        Text("Registrar", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

