// Archivo completo: LoginActivity.kt

package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.MainActivity
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Usuario

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaLogin() }
    }
}

@Composable
fun PantallaLogin() {
    val context = LocalContext.current
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF5A7F5B), Color(0xFF446247))
    )

    Box(
        modifier = Modifier.fillMaxSize().background(gradient)
    ) {

        IconButton(
            onClick = {
                val activity = context as Activity
                activity.startActivity(Intent(activity, MainActivity::class.java))
                activity.finish()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = Color.White)
        }

        Card(
            modifier = Modifier.padding(24.dp).fillMaxWidth().align(Alignment.Center),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(28.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Inicio de Sesión", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF446247))
                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = usuario,
                    onValueChange = { usuario = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(26.dp))

                Button(
                    onClick = {
                        scope.launch {
                            try {
                                auth.signInWithEmailAndPassword(usuario.trim(), contrasena.trim()).await()
                                val uid = auth.currentUser!!.uid

                                val userDoc = db.collection("usuarios").document(uid).get().await()
                                val user = userDoc.toObject(Usuario::class.java)

                                if (user != null) {

                                    // ASIGNAR AUTOMÁTICAMENTE ADMIN
                                    if (usuario == "admin@gmail.com") {
                                        user.rol = "admin"
                                    }

                                    val prefs = context.getSharedPreferences("sesion", Activity.MODE_PRIVATE)
                                    prefs.edit()
                                        .putString("usuario_id", user.id)
                                        .putString("rol", user.rol)
                                        .apply()

                                    val act = context as Activity
                                    act.startActivity(Intent(act, PantallaMapaActivity::class.java))
                                    act.finish()
                                }

                            } catch (e: Exception) {
                                Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247), contentColor = Color.White)
                ) {
                    Text("Ingresar", fontSize = 18.sp)
                }

                Spacer(Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        (context as Activity).startActivity(Intent(context, RegistroActivity::class.java))
                    }
                ) {
                    Text("Crear nuevo usuario", color = Color(0xFF446247), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
