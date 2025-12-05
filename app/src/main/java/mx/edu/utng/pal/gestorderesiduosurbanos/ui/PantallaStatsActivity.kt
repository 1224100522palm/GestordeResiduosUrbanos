package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Usuario

class PantallaStatsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaStats() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaStats() {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val scope = rememberCoroutineScope()

    // Sesión Firebase
    val firebaseUser = auth.currentUser
    val userId = firebaseUser?.uid ?: ""

    var usuario by remember { mutableStateOf<Usuario?>(null) }
    var editando by remember { mutableStateOf(false) }

    var telefono by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var usuarioNombre by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    var mostrarDialogo by remember { mutableStateOf(false) }

    // 🔥 Cargar usuario desde Firebase
    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            db.collection("usuarios")
                .document(userId)
                .get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        usuario = Usuario(
                            id = doc.id,
                            nombreCompleto = doc.getString("nombreCompleto") ?: "",
                            nombreUsuario = doc.getString("nombreUsuario") ?: "",
                            numeroTelefono = doc.getString("numeroTelefono") ?: "",
                            contrasena = doc.getString("contrasena") ?: "",
                            rol = doc.getString("rol") ?: "usuario"
                        )

                        usuario?.let {
                            telefono = it.numeroTelefono
                            nombre = it.nombreCompleto
                            usuarioNombre = it.nombreUsuario
                            contrasena = it.contrasena
                        }
                    }

                }
        }
    }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    // 🔥 DIALOGO DE ELIMINAR
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Confirmar eliminación", fontWeight = FontWeight.Bold) },
            text = { Text("¿Seguro que quieres eliminar tu cuenta? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogo = false
                        scope.launch {
                            db.collection("usuarios").document(userId).delete()
                            auth.currentUser?.delete()

                            context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
                                .edit()
                                .clear()
                                .apply()

                            val act = context as Activity
                            act.startActivity(Intent(act, LoginActivity::class.java))
                            act.finish()
                        }
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFD32F2F))
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del Usuario", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF446247)
                )
            )
        },
        bottomBar = { BottomNavigationBarPerfil() },
        containerColor = Color.Transparent
    ) { padding ->

        Box(
            Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
                .padding(12.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    usuario?.let { u ->

                        if (!editando) {

                            Text(
                                "Información del Usuario",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF446247)
                            )

                            Spacer(Modifier.height(16.dp))

                            Text("Nombre: ${u.nombreCompleto}", fontSize = 18.sp)
                            Text("Correo Electronico: ${u.nombreUsuario}", fontSize = 18.sp)
                            Text("Teléfono: ${u.numeroTelefono}", fontSize = 18.sp)
                            Text("Contraseña: ******", fontSize = 18.sp, color = Color.Gray)

                            Spacer(Modifier.height(30.dp))

                            Button(
                                onClick = { editando = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247))
                            ) {
                                Text("Editar Información", color = Color.White)
                            }

                            Spacer(Modifier.height(10.dp))

                            Button(
                                onClick = { mostrarDialogo = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                            ) {
                                Text("Eliminar Cuenta", color = Color.White)
                            }

                            Spacer(Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    auth.signOut()
                                    context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
                                        .edit()
                                        .clear()
                                        .apply()

                                    val act = context as Activity
                                    act.startActivity(Intent(act, LoginActivity::class.java))
                                    act.finish()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF616161))
                            ) {
                                Text("Cerrar Sesión", color = Color.White)
                            }

                        } else {

                            Text(
                                "Editar Usuario",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF446247)
                            )

                            Spacer(Modifier.height(16.dp))

                            OutlinedTextField(
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = { Text("Nombre Completo") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = usuarioNombre,
                                onValueChange = { usuarioNombre = it },
                                label = { Text("Nombre de Usuario") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = telefono,
                                onValueChange = { telefono = it },
                                label = { Text("Número de Teléfono") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = contrasena,
                                onValueChange = { contrasena = it },
                                label = { Text("Contraseña") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    val datos = mapOf(
                                        "nombreCompleto" to nombre,
                                        "nombreUsuario" to usuarioNombre,
                                        "numeroTelefono" to telefono,
                                        "contrasena" to contrasena,
                                        "rol" to u.rol
                                    )

                                    db.collection("usuarios")
                                        .document(userId)
                                        .set(datos)
                                        .addOnSuccessListener {
                                            editando = false
                                        }

                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247))
                            ) {
                                Text("Guardar Cambios", color = Color.White)
                            }

                            Spacer(Modifier.height(10.dp))

                            Button(
                                onClick = { editando = false },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                            ) {
                                Text("Cancelar")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBarPerfil() {
    val context = LocalContext.current

    NavigationBar(
        containerColor = Color(0xFFF8F8F8),
        tonalElevation = 8.dp
    ) {

        val items = listOf(
            Triple("Mapa", Icons.Default.LocationOn, PantallaMapaActivity::class.java),
            Triple("Horarios", Icons.Default.Schedule, PantallaHorarioActivity::class.java),
            Triple("Botes", Icons.Default.Delete, PantallaBotesActivity::class.java),
            Triple("Reportes", Icons.Default.Report, PantallaReportesActivity::class.java),
            Triple("Avisos", Icons.Default.Notifications, PantallaAvisoActivity::class.java),
            Triple("Perfil", Icons.Default.Person, PantallaStatsActivity::class.java)
        )

        items.forEach { (title, icon, screen) ->
            NavigationBarItem(
                selected = title == "Perfil",
                onClick = {
                    if (title != "Perfil") context.startActivity(Intent(context, screen))
                },
                icon = { Icon(icon, contentDescription = title, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40)) }
            )
        }
    }
}
