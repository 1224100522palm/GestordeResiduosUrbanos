package mx.edu.utng.pal.gestorderesiduosurbanos.ui

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
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote

class PantallaMapaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaMapa() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMapa() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = FirebaseFirestore.getInstance()

    var botes by remember { mutableStateOf(listOf<Bote>()) }

    val centro = LatLng(21.1561, -100.9311)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centro, 14f)
    }

    /** 🔥 Cargar datos desde Firestore */
    LaunchedEffect(Unit) {
        scope.launch {
            val result = db.collection("botes").get().await()
            botes = result.toObjects(Bote::class.java)
        }
    }

    /** Fondo */
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8F0E8),
            Color(0xFFDDE7DD)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ubicación de Botes", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF446247)
                )
            )
        },
        bottomBar = { BottomNavigationBarMapa() },
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
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties()   // ← Evita pantalla negra
                ) {
                    botes.forEach { bote ->
                        if (bote.lat != 0.0 && bote.lng != 0.0) {
                            Marker(
                                state = MarkerState(position = LatLng(bote.lat, bote.lng)),
                                title = "Residuo: ${bote.tipoResiduo}",
                                snippet = """
                                    Colonia: ${bote.colonia}
                                    Estado: ${bote.estado}
                                """.trimIndent()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBarMapa() {
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
                selected = title == "Mapa",
                onClick = {
                    if (title != "Mapa") {
                        context.startActivity(Intent(context, screen))
                    }
                },
                icon = {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = Color(0xFF3D5F40)
                    )
                },
                label = {
                    Text(
                        title,
                        color = Color(0xFF3D5F40),
                        fontSize = MaterialTheme.typography.labelSmall.fontSize
                    )
                }
            )
        }
    }
}


