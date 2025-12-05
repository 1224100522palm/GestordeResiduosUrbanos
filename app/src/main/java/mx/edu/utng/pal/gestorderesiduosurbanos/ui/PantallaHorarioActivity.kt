package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Horarios

class PantallaHorarioActivity : ComponentActivity() {

    companion object {
        var recargar = mutableStateOf(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaHorario() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaHorario() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    var lista by remember { mutableStateOf(listOf<Horarios>()) }
    var listaFiltrada by remember { mutableStateOf(listOf<Horarios>()) }
    var busqueda by remember { mutableStateOf("") }

    val trigger = PantallaHorarioActivity.recargar.value

    /** Cargar horarios desde Firestore */
    LaunchedEffect(trigger) {
        scope.launch {
            val result = db.collection("horarios").get().await()
            lista = result.toObjects(Horarios::class.java)   // ❗ YA NO SE COPIA it.id
            listaFiltrada = lista
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Horarios de Recolección", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF446247))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(
                        Intent(context, PantallaRegistroHorarioActivity::class.java)
                    )
                },
                containerColor = Color(0xFF446247)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        },
        bottomBar = { BottomNavigationBarHorarios() },
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
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { texto ->
                            busqueda = texto
                            listaFiltrada = lista.filter {
                                it.colonia.contains(texto, ignoreCase = true)
                            }
                        },
                        label = { Text("Buscar colonia") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (listaFiltrada.isEmpty()) {

                        Text(
                            "No hay colonias registradas",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                    } else {

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {

                            items(listaFiltrada) { h ->

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                                ) {

                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {

                                        Text(
                                            "Colonia: ${h.colonia}",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF446247)
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text("Días: ${h.dias}", fontSize = 16.sp)
                                        Text("Hora: ${h.hora}", fontSize = 16.sp)
                                        Text("Tipo: ${h.tipoResiduo}", fontSize = 16.sp)
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
fun BottomNavigationBarHorarios() {

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
                selected = title == "Horarios",
                onClick = {
                    if (title != "Horarios") {
                        context.startActivity(Intent(context, screen))
                    }
                },
                icon = { Icon(icon, contentDescription = null, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40)) }
            )
        }
    }
}
