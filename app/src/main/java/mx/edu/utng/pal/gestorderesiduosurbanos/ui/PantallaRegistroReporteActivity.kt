package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Reporte

class PantallaRegistroReporteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaRegistroReporteUI() }
    }
}

fun obtenerFechaActual(): String {
    val sdf = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
    return sdf.format(java.util.Date())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroReporteUI() {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
    val userName = prefs.getString("usuario_nombre", "Desconocido") ?: "Desconocido"

    var listaBotes by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var listaColonias by remember { mutableStateOf(listOf<String>()) }

    var boteSeleccionado by remember { mutableStateOf<Map<String, Any>?>(null) }
    var coloniaSeleccionada by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var expBotes by remember { mutableStateOf(false) }
    var expColonias by remember { mutableStateOf(false) }

    // 🔥 Cargar datos desde Firestore
    LaunchedEffect(Unit) {

        // Botes
        db.collection("botes")
            .get()
            .addOnSuccessListener { docs ->
                listaBotes = docs.map { it.data }
            }

        // Colonias
        db.collection("horarios")
            .get()
            .addOnSuccessListener { docs ->
                listaColonias = docs.map { it.getString("colonia") ?: "" }
                    .filter { it.isNotBlank() }
                    .distinct()
            }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Reporte", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { (context as? Activity)?.finish() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF446247))
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
        ) {

            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {

                    Text(
                        "Usuario: $userName",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF446247)
                    )

                    Spacer(Modifier.height(20.dp))

                    // 🔥 BOTES (Firebase)
                    Text("Bote", fontWeight = FontWeight.SemiBold, color = Color(0xFF446247))
                    ExposedDropdownMenuBox(
                        expanded = expBotes,
                        onExpandedChange = { expBotes = !expBotes }
                    ) {
                        OutlinedTextField(
                            value = boteSeleccionado?.let {
                                "${it["colonia"]} - ${it["tipoResiduo"]}"
                            } ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecciona un bote") },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expBotes,
                            onDismissRequest = { expBotes = false }
                        ) {
                            listaBotes.forEach { bote ->
                                DropdownMenuItem(
                                    text = {
                                        Text("${bote["colonia"]} - ${bote["tipoResiduo"]}")
                                    },
                                    onClick = {
                                        boteSeleccionado = bote
                                        expBotes = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // 🔥 COLONIAS (Firebase)
                    Text("Colonia", fontWeight = FontWeight.SemiBold, color = Color(0xFF446247))
                    ExposedDropdownMenuBox(
                        expanded = expColonias,
                        onExpandedChange = { expColonias = !expColonias }
                    ) {
                        OutlinedTextField(
                            value = coloniaSeleccionada,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecciona una colonia") },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = expColonias,
                            onDismissRequest = { expColonias = false }
                        ) {
                            listaColonias.forEach { col ->
                                DropdownMenuItem(
                                    text = { Text(col) },
                                    onClick = {
                                        coloniaSeleccionada = col
                                        expColonias = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Text("Descripción", fontWeight = FontWeight.SemiBold, color = Color(0xFF446247))
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción del problema") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5
                    )

                    Spacer(Modifier.height(30.dp))

                    // 🔥 GUARDAR REPORTE EN FIREBASE
                    Button(
                        onClick = {
                            if (boteSeleccionado != null && coloniaSeleccionada.isNotEmpty()) {

                                val datos = mapOf(
                                    "usuario" to userName,
                                    "bote" to (boteSeleccionado!!["colonia"] ?: ""),
                                    "colonia" to coloniaSeleccionada,
                                    "tipo" to (boteSeleccionado!!["tipoResiduo"] ?: ""),
                                    "descripcion" to descripcion,
                                    "fecha" to obtenerFechaActual(),
                                    "estado" to "Activo"
                                )

                                db.collection("reportes")
                                    .add(datos)
                                    .addOnSuccessListener {
                                        (context as? Activity)?.finish()
                                    }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Enviar Reporte", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
