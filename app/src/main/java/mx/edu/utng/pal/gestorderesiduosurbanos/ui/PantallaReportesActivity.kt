package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
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

class PantallaReportesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaReportes() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaReportes() {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("sesion", android.app.Activity.MODE_PRIVATE)
    val rol = prefs.getString("rol", "usuario") ?: "usuario"

    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    var lista by remember { mutableStateOf(listOf<Reporte>()) }
    var listaFiltrada by remember { mutableStateOf(listOf<Reporte>()) }
    var busqueda by remember { mutableStateOf("") }
    var tabSeleccionado by remember { mutableStateOf("Abiertos") }

    // 🔥 Cargar reportes desde Firebase
    fun cargarReportes() {
        db.collection("reportes")
            .get()
            .addOnSuccessListener { docs ->
                lista = docs.map { doc ->
                    Reporte(
                        id = (doc.getLong("id")?.toInt() ?: 0).toString(),
                        usuario = doc.getString("usuario") ?: "",
                        bote = doc.getString("bote") ?: "",
                        colonia = doc.getString("colonia") ?: "",
                        tipo = doc.getString("tipo") ?: "",
                        descripcion = doc.getString("descripcion") ?: "",
                        fecha = doc.getString("fecha") ?: "",
                        estado = doc.getString("estado") ?: "Activo",
                        firebaseId = doc.id
                    )


                }
                listaFiltrada = lista.filter {
                    (tabSeleccionado == "Abiertos" && it.estado == "Activo") ||
                            (tabSeleccionado == "Resueltos" && it.estado == "Resuelto")
                }
            }
    }

    LaunchedEffect(Unit) {
        cargarReportes()
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reportes", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF446247))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { context.startActivity(Intent(context, PantallaRegistroReporteActivity::class.java)) },
                containerColor = Color(0xFF446247)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar reporte", tint = Color.White)
            }
        },
        bottomBar = { BottomNavigationBarReportes() },
        containerColor = Color.Transparent
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
                        .padding(16.dp)
                ) {

                    // 🔥 Tabs
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        listOf("Abiertos", "Resueltos").forEach { tab ->
                            TextButton(onClick = {
                                tabSeleccionado = tab
                                listaFiltrada = lista.filter {
                                    (tab == "Abiertos" && it.estado == "Activo") ||
                                            (tab == "Resueltos" && it.estado == "Resuelto")
                                }
                            }) {
                                Text(
                                    tab,
                                    color = if (tabSeleccionado == tab) Color(0xFF446247) else Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // 🔥 Buscador
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { texto ->
                            busqueda = texto
                            listaFiltrada = lista.filter { r ->
                                (tabSeleccionado == "Abiertos" && r.estado == "Activo" ||
                                        tabSeleccionado == "Resueltos" && r.estado == "Resuelto") &&
                                        (
                                                r.usuario.contains(texto, true) ||
                                                        r.bote.contains(texto, true) ||
                                                        r.colonia.contains(texto, true) ||
                                                        r.tipo.contains(texto, true) ||
                                                        r.descripcion.contains(texto, true) ||
                                                        r.fecha.contains(texto, true) ||
                                                        r.id.toString().contains(texto)
                                                )
                            }
                        },
                        label = { Text("Buscar reporte…") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (listaFiltrada.isEmpty()) {
                        Text(
                            "No hay reportes disponibles",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(listaFiltrada) { rep ->

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .clickable {},
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            "Reporte #${rep.id}",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF446247)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text("Usuario: ${rep.usuario}", color = Color.DarkGray)
                                        Text("Bote: ${rep.bote}", color = Color.DarkGray)
                                        Text("Colonia: ${rep.colonia}", color = Color.DarkGray)
                                        Text("Tipo: ${rep.tipo}", color = Color.DarkGray)
                                        Text("Descripción: ${rep.descripcion}", color = Color.DarkGray)
                                        Text("Fecha: ${rep.fecha}", color = Color.DarkGray)
                                        Text("Estado: ${rep.estado}", color = Color.DarkGray)

                                        // 🔥 ADMIN CAMBIA ESTADO
                                        if (rol == "admin") {
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Button(
                                                onClick = {
                                                    val nuevo = if (rep.estado == "Activo") "Resuelto" else "Activo"

                                                    db.collection("reportes")
                                                        .document(rep.firebaseId)
                                                        .update("estado", nuevo)
                                                        .addOnSuccessListener {
                                                            cargarReportes()
                                                        }
                                                },
                                                colors = ButtonDefaults.buttonColors(
                                                    if (rep.estado == "Activo") Color(0xFF446247) else Color.Gray
                                                )
                                            ) {
                                                Text(
                                                    if (rep.estado == "Activo") "Marcar Resuelto" else "Reabrir"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBarReportes() {
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
                selected = title == "Reportes",
                onClick = {
                    if (title != "Reportes") context.startActivity(Intent(context, screen))
                },
                icon = { Icon(icon, contentDescription = title, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40), fontSize = MaterialTheme.typography.labelSmall.fontSize) }
            )
        }
    }
}
