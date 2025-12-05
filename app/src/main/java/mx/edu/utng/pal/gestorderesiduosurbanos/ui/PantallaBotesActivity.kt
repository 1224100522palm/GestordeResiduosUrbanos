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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote

class PantallaBotesActivity : ComponentActivity() {

    companion object {
        var recargarTrigger = mutableStateOf(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaBotes() }
    }

    override fun onResume() {
        super.onResume()
        recargarTrigger.value++
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBotes() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    var lista by remember { mutableStateOf(listOf<Bote>()) }
    var listaFiltrada by remember { mutableStateOf(listOf<Bote>()) }
    var busqueda by remember { mutableStateOf("") }

    val trigger = PantallaBotesActivity.recargarTrigger.value

    // 🔥 Leer botes desde Firestore
    LaunchedEffect(trigger) {
        scope.launch {
            val result = db.collection("botes").get().await()
            lista = result.toObjects(Bote::class.java)
            listaFiltrada = lista
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Botes Urbanos", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF446247))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(Intent(context, PantallaRegistroBoteActivity::class.java))
                },
                containerColor = Color(0xFF446247)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        },
        bottomBar = { BottomNavigationBarBotes() }
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

                    // 🔍 BUSQUEDA
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { texto ->
                            busqueda = texto
                            listaFiltrada = lista.filter {
                                it.id.toString().contains(texto, ignoreCase = true) ||
                                        it.colonia.contains(texto, ignoreCase = true) ||
                                        it.tipoResiduo.contains(texto, ignoreCase = true) ||
                                        it.estado.contains(texto, ignoreCase = true)
                            }
                        },
                        label = { Text("Buscar bote…") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(16.dp))

                    if (listaFiltrada.isEmpty()) {
                        Text(
                            "No hay botes registrados",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(listaFiltrada) { bote ->

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                        .clickable {

                                            val intent = Intent(context, PantallaRegistroBoteActivity::class.java)

                                            // 🔥 SIEMPRE ENVIAR EL ID COMO STRING
                                            intent.putExtra("boteId", bote.id.toString())

                                            context.startActivity(intent)
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {

                                        Text(
                                            "ID: ${bote.id.toString()}",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF446247)
                                        )

                                        Spacer(Modifier.height(4.dp))

                                        Text(
                                            "Colonia: ${bote.colonia}",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF446247)
                                        )

                                        Spacer(Modifier.height(4.dp))

                                        Text("Residuo: ${bote.tipoResiduo}", fontSize = 16.sp)
                                        Text("Estado: ${bote.estado}", fontSize = 16.sp)
                                        Text("Lat: ${bote.lat}   Lng: ${bote.lng}", fontSize = 16.sp)
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
fun BottomNavigationBarBotes() {
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
                selected = title == "Botes",
                onClick = {
                    if (title != "Botes") context.startActivity(Intent(context, screen))
                },
                icon = { Icon(icon, contentDescription = null, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40)) }
            )
        }
    }
}
