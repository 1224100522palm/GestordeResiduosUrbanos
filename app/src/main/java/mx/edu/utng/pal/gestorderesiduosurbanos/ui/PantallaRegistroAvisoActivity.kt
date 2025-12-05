package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Aviso
import java.text.SimpleDateFormat
import java.util.*

class PantallaRegistroAvisoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val avisoId = intent?.getStringExtra("avisoId") ?: ""

        setContent { PantallaRegistroAvisoUI(avisoId) }
    }
}

/**  🔥 FUNCIÓN PARA OBTENER SIGUIENTE ID CONSECUTIVO **/
suspend fun obtenerSiguienteIdAviso(db: FirebaseFirestore): Int {
    val docs = db.collection("avisos").get().await()
    val ids = docs.documents.mapNotNull { it.getLong("idInt")?.toInt() }
    return if (ids.isEmpty()) 1 else (ids.max() + 1)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroAvisoUI(avisoId: String) {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("sesion", Activity.MODE_PRIVATE)

    val usuarioActual = prefs.getString("usuario_correo", "") ?: ""
    val esAdmin = prefs.getString("rol", "usuario") == "admin"

    val scope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    var idDocumento by remember { mutableStateOf(avisoId) }   // ID Firestore
    var idInt by remember { mutableStateOf(0) }                // ID consecutivo

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var creadoPor by remember { mutableStateOf("") }

    var listaColonias by remember { mutableStateOf(listOf<String>()) }
    val tipos = listOf("Información", "Alerta", "Recordatorio")

    /** Cargar colonias y aviso si se edita */
    LaunchedEffect(Unit) {

        val hResult = db.collection("horarios").get().await()
        listaColonias = hResult.documents.mapNotNull { it.getString("colonia") }.distinct()

        if (idDocumento.isNotEmpty()) {
            val doc = db.collection("avisos").document(idDocumento).get().await()
            if (doc.exists()) {
                idInt = (doc.getLong("idInt") ?: 0).toInt()
                titulo = doc.getString("titulo") ?: ""
                descripcion = doc.getString("descripcion") ?: ""
                fecha = doc.getString("fecha") ?: ""
                tipo = doc.getString("tipo") ?: ""
                colonia = doc.getString("colonia") ?: ""
                creadoPor = doc.getString("creadoPor") ?: ""
            }
        } else {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            fecha = sdf.format(Date())
        }
    }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (idDocumento.isEmpty()) "Crear Aviso" else "Editar Aviso", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { (context as? Activity)?.finish() }) {
                        Text("⬅", fontSize = 28.sp, color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF446247))
            )
        }
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
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    OutlinedTextField(
                        value = if (idInt == 0) "Auto" else idInt.toString(),
                        onValueChange = {},
                        label = { Text("ID consecutivo") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título del aviso") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    Text("Tipo", color = Color(0xFF446247), fontWeight = FontWeight.Bold)
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        tipos.forEach { t ->
                            val selected = tipo == t
                            Button(
                                onClick = { tipo = t },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected) Color(0xFF4CAF50) else Color.LightGray
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(t, color = Color.White)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    /** COLONIA Dropdown */
                    var expanded by remember { mutableStateOf(false) }

                    Text("Colonia", color = Color(0xFF446247), fontWeight = FontWeight.Bold)

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = colonia,
                            onValueChange = { colonia = it },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listaColonias.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        colonia = opcion
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = fecha,
                        onValueChange = { fecha = it },
                        label = { Text("Fecha (dd/MM/yyyy)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        maxLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    /** BOTONES */
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // GUARDAR
                        Button(
                            onClick = {

                                if (titulo.isBlank() || descripcion.isBlank() ||
                                    tipo.isBlank() || colonia.isBlank()) {
                                    Toast.makeText(context, "Faltan campos", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                scope.launch {

                                    /** 🔥 Generar ID consecutivo si es nuevo */
                                    if (idDocumento.isEmpty()) {
                                        idInt = obtenerSiguienteIdAviso(db)
                                        idDocumento = db.collection("avisos").document().id
                                    }

                                    val aviso = Aviso(
                                        id = idDocumento,
                                        titulo = titulo,
                                        descripcion = descripcion,
                                        fecha = fecha,
                                        tipo = tipo,
                                        colonia = colonia,
                                        creadoPor = if (esAdmin) "admin" else usuarioActual
                                    )

                                    db.collection("avisos")
                                        .document(idDocumento)
                                        .set(aviso)
                                        .await()

                                    // Guardamos también el ID consecutivo
                                    db.collection("avisos")
                                        .document(idDocumento)
                                        .update("idInt", idInt)

                                    Toast.makeText(context, "Guardado correctamente", Toast.LENGTH_SHORT).show()

                                    (context as Activity).finish()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247))
                        ) {
                            Text(if (idDocumento.isEmpty()) "Registrar" else "Actualizar", color = Color.White)
                        }

                        Button(
                            onClick = { (context as Activity).finish() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                        ) {
                            Text("Cancelar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}



