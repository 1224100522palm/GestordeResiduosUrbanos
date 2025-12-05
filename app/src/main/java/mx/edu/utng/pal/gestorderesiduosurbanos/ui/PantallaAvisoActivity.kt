package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
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
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Aviso

class PantallaAvisoActivity : ComponentActivity() {

    companion object {
        val triggerRecargarAvisos = mutableStateOf(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaAvisosUI() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisosUI() {

    val context = LocalContext.current
    val prefs = context.getSharedPreferences("sesion", Activity.MODE_PRIVATE)

    // 🔥 Corrección importante
    val usuarioActual = prefs.getString("usuario_correo", "") ?: ""
    val esAdmin = prefs.getString("rol", "usuario") == "admin"

    val scope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    var lista by remember { mutableStateOf(listOf<Aviso>()) }
    var busqueda by remember { mutableStateOf("") }

    var avisosAdmin by remember { mutableStateOf(listOf<Aviso>()) }
    var avisosUsuarios by remember { mutableStateOf(listOf<Aviso>()) }

    LaunchedEffect(PantallaAvisoActivity.triggerRecargarAvisos.value) {
        scope.launch {
            val snapshot = db.collection("avisos").get().await()
            lista = snapshot.toObjects(Aviso::class.java)

            // 🔥 Clasificación correcta
            avisosAdmin = lista.filter {
                it.creadoPor == "admin" || it.creadoPor == "admin@gmail.com"
            }

            avisosUsuarios = lista.filter {
                it.creadoPor != "admin" && it.creadoPor != "admin@gmail.com"
            }
        }
    }

    val gradient = Brush.verticalGradient(
        listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Avisos Municipales", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF446247)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(
                        Intent(context, PantallaRegistroAvisoActivity::class.java)
                    )
                },
                containerColor = Color(0xFF446247)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar aviso", tint = Color.White)
            }
        },
        bottomBar = { BottomNavigationBarAvisos() },
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

                    // BUSCADOR
                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { texto ->
                            busqueda = texto

                            avisosAdmin = lista.filter {
                                (it.creadoPor == "admin" || it.creadoPor == "admin@gmail.com") &&
                                        (
                                                it.titulo.contains(texto, true) ||
                                                        it.id.contains(texto, true) ||
                                                        it.colonia.contains(texto, true) ||
                                                        it.tipo.contains(texto, true) ||
                                                        it.fecha.contains(texto, true) ||
                                                        it.descripcion.contains(texto, true)
                                                )
                            }

                            avisosUsuarios = lista.filter {
                                (it.creadoPor != "admin" && it.creadoPor != "admin@gmail.com") &&
                                        (
                                                it.titulo.contains(texto, true) ||
                                                        it.id.contains(texto, true) ||
                                                        it.colonia.contains(texto, true) ||
                                                        it.tipo.contains(texto, true) ||
                                                        it.fecha.contains(texto, true) ||
                                                        it.descripcion.contains(texto, true)
                                                )
                            }
                        },
                        label = { Text("Buscar aviso…") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(16.dp))

                    LazyColumn(Modifier.fillMaxSize()) {

                        // ADMIN
                        item {
                            Text(
                                "Avisos del Municipio",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF446247),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(avisosAdmin) { aviso ->
                            AvisoItem(aviso, esAdmin, context)
                        }

                        item {
                            Spacer(Modifier.height(20.dp))
                            Divider(thickness = 2.dp, color = Color(0xFF446247))
                            Spacer(Modifier.height(20.dp))
                        }

                        // USUARIOS
                        item {
                            Text(
                                "Avisos de la Comunidad",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF446247),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(avisosUsuarios) { aviso ->
                            AvisoItem(
                                aviso,
                                esAdmin || aviso.creadoPor == usuarioActual,
                                context
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AvisoItem(aviso: Aviso, puedeEditar: Boolean, context: android.content.Context) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                if (puedeEditar) {
                    val i = Intent(context, PantallaRegistroAvisoActivity::class.java)
                    i.putExtra("avisoId", aviso.id)
                    context.startActivity(i)
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                aviso.titulo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF446247)
            )
            Spacer(Modifier.height(4.dp))
            Text("Colonia: ${aviso.colonia}", color = Color.DarkGray)
            Text("Tipo: ${aviso.tipo}", color = Color.DarkGray)
            Text("Fecha: ${aviso.fecha}", color = Color.DarkGray)
            Text("Descripción: ${aviso.descripcion}", color = Color.DarkGray)
            Text("Creado por: ${aviso.creadoPor}", color = Color.DarkGray)
        }
    }
}

@Composable
fun BottomNavigationBarAvisos() {
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
                selected = title == "Avisos",
                onClick = {
                    if (title != "Avisos") context.startActivity(Intent(context, screen))
                },
                icon = { Icon(icon, contentDescription = title, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40)) }
            )
        }
    }
}
