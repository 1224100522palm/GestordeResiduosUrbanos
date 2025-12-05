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
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote
import androidx.compose.foundation.layout.FlowRow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import androidx.compose.foundation.layout.ExperimentalLayoutApi


class PantallaRegistroBoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val rol = prefs.getString("rol", "usuario")

        if (rol != "admin") {
            Toast.makeText(this, "Acceso permitido solo a administradores", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val boteEditar = intent.getSerializableExtra("bote") as? Bote
        setContent { PantallaRegistroBoteUI(boteEditar) }
    }
}

suspend fun obtenerSiguienteIdBote(db: FirebaseFirestore): Int {
    val docs = db.collection("botes").get().await()
    val ids = docs.documents.mapNotNull { it.getLong("id")?.toInt() }
    return if (ids.isEmpty()) 1 else (ids.max() + 1)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PantallaRegistroBoteUI(boteEditar: Bote?) {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    var id by remember { mutableStateOf(boteEditar?.id ?: 0) }
    val boteId = (context as? Activity)?.intent?.getStringExtra("boteId") ?: ""
    var docId by remember { mutableStateOf(boteId) }

    var colonia by remember { mutableStateOf(boteEditar?.colonia ?: "") }
    var tipoResiduo by remember { mutableStateOf(boteEditar?.tipoResiduo ?: "") }
    var estado by remember { mutableStateOf(boteEditar?.estado ?: "") }
    var lat by remember { mutableStateOf(boteEditar?.lat ?: 0.0) }
    var lng by remember { mutableStateOf(boteEditar?.lng ?: 0.0) }

    var idBuscar by remember { mutableStateOf("") }
    var coloniasRegistradas by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        db.collection("horarios")
            .get()
            .addOnSuccessListener { documentos ->
                coloniasRegistradas = documentos.map { it.getString("colonia") ?: "" }.distinct()
            }
    }

    val tipos = listOf(
        "Orgánico", "Inorgánico", "General", "Vidrio",
        "Plastico y envases", "Papel"
    )

    val estados = listOf("Activo", "Desactivado")

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    val inicio = LatLng(if (lat != 0.0) lat else 21.1561, if (lng != 0.0) lng else -100.9311)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(inicio, 15f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Botes", color = Color.White) },
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
            modifier = Modifier
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
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    // 🔍 BUSCAR POR ID Firestore
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = idBuscar,
                            onValueChange = { idBuscar = it },
                            label = { Text("Buscar por ID Firestore") },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247)),
                            onClick = {
                                if (idBuscar.isNotBlank()) {
                                    db.collection("botes")
                                        .document(idBuscar)
                                        .get()
                                        .addOnSuccessListener { doc ->
                                            if (doc.exists()) {
                                                docId = doc.id
                                                id = (doc.getLong("id") ?: 0).toInt()
                                                colonia = doc.getString("colonia") ?: ""
                                                tipoResiduo = doc.getString("tipoResiduo") ?: ""
                                                estado = doc.getString("estado") ?: ""
                                                lat = doc.getDouble("lat") ?: 0.0
                                                lng = doc.getDouble("lng") ?: 0.0
                                            } else {
                                                Toast.makeText(context, "No encontrado", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                }
                            }
                        ) {
                            Text("Buscar", color = Color.White)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = id.toString(),
                        onValueChange = {},
                        label = { Text("ID Local") },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

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
                            coloniasRegistradas
                                .filter { it.contains(colonia, true) }
                                .forEach { opcion ->
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

                    Text("Tipo de residuo", color = Color(0xFF446247), fontWeight = FontWeight.Bold)

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        maxItemsInEachRow = 3,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tipos.forEach { opcion ->
                            Button(
                                onClick = { tipoResiduo = opcion },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (tipoResiduo == opcion)
                                        Color(0xFF4CAF50) else Color.LightGray
                                ),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text(opcion, color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Estado del bote", color = Color(0xFF446247), fontWeight = FontWeight.Bold)

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        estados.forEach { opcion ->
                            Button(
                                onClick = { estado = opcion },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (estado == opcion)
                                        Color(0xFF2196F3) else Color.LightGray
                                ),
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text(opcion, color = Color.White)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    Text("Selecciona ubicación en el mapa", fontSize = 14.sp, color = Color(0xFF446247))

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(270.dp),
                        cameraPositionState = cameraPositionState,
                        onMapClick = { pos ->
                            lat = pos.latitude
                            lng = pos.longitude
                        }
                    ) {
                        if (lat != 0.0 && lng != 0.0) {
                            Marker(
                                state = MarkerState(position = LatLng(lat, lng)),
                                title = "Ubicación del bote"
                            )
                        }
                    }

                    Text("Lat: $lat   Lng: $lng", fontSize = 12.sp)

                    Spacer(Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247)),
                            onClick = {
                                if (colonia.isBlank() || tipoResiduo.isBlank() || estado.isBlank() || lat == 0.0 || lng == 0.0) {
                                    Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                scope.launch {

                                    if (docId.isBlank()) {
                                        // 🔥 ID CONSECUTIVO
                                        id = obtenerSiguienteIdBote(db)

                                        val datosNuevo = mapOf(
                                            "id" to id,
                                            "colonia" to colonia,
                                            "tipoResiduo" to tipoResiduo,
                                            "estado" to estado,
                                            "lat" to lat,
                                            "lng" to lng
                                        )

                                        db.collection("botes")
                                            .add(datosNuevo)
                                            .addOnSuccessListener {
                                                docId = it.id
                                                Toast.makeText(context, "Registrado con ID $id", Toast.LENGTH_SHORT).show()
                                                (context as? Activity)?.finish()
                                            }

                                    } else {
                                        val datosActualizar = mapOf(
                                            "id" to id,
                                            "colonia" to colonia,
                                            "tipoResiduo" to tipoResiduo,
                                            "estado" to estado,
                                            "lat" to lat,
                                            "lng" to lng
                                        )

                                        db.collection("botes")
                                            .document(docId)
                                            .set(datosActualizar)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show()
                                                (context as? Activity)?.finish()
                                            }
                                    }
                                }
                            }
                        ) {
                            Text(if (docId.isEmpty()) "Registrar" else "Actualizar", color = Color.White)
                        }

                        Button(
                            enabled = docId.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            onClick = {
                                db.collection("botes")
                                    .document(docId)
                                    .delete()
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
                                        (context as? Activity)?.finish()
                                    }
                            }
                        ) {
                            Text("Eliminar", color = Color.White)
                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            onClick = { (context as? Activity)?.finish() }
                        ) {
                            Text("Cancelar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

