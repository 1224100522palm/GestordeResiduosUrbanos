package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Horarios

class PantallaRegistroHorarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("sesion", MODE_PRIVATE)
        val rol = prefs.getString("rol", "usuario")

        if (rol != "admin") {
            Toast.makeText(this, "Acceso solo para administradores", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val horarioEditar = intent.getSerializableExtra("horario") as? Horarios
        val docId = intent.getStringExtra("docId") ?: ""

        setContent {
            PantallaRegistroHorarioUI(
                horarioEditar = horarioEditar,
                docIdInicial = docId
            ) {
                PantallaHorarioActivity.recargar.value++
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroHorarioUI(
    horarioEditar: Horarios?,
    docIdInicial: String,
    onFinish: () -> Unit
) {

    val context = LocalContext.current
    val act = context as Activity
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

    var docId by remember { mutableStateOf(docIdInicial) }

    // DATOS DEL MODELO
    var id by remember { mutableStateOf(horarioEditar?.id ?: 0L) }
    var colonia by remember { mutableStateOf(horarioEditar?.colonia ?: "") }
    var diasSeleccionados by remember {
        mutableStateOf(horarioEditar?.dias?.split(",")?.toSet() ?: emptySet())
    }
    var hora by remember { mutableStateOf(horarioEditar?.hora ?: "08:00") }
    var tiposSeleccionados by remember {
        mutableStateOf(horarioEditar?.tipoResiduo?.split(",")?.toSet() ?: emptySet())
    }

    val dias = listOf("Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do")

    val tipos = listOf(
        "Orgánico",
        "Inorgánico",
        "General",
        "Vidrio",
        "Plástico",
        "Papel"
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Horarios", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { act.finish() }) {
                        Text("⬅", fontSize = 28.sp, color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF446247)
                )
            )
        },
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
                        .padding(24.dp)
                ) {

                    // Buscar colonia
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedTextField(
                            value = colonia,
                            onValueChange = { colonia = it },
                            label = { Text("Colonia") },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (colonia.isNotBlank()) {
                                    db.collection("horarios")
                                        .whereEqualTo("colonia", colonia)
                                        .get()
                                        .addOnSuccessListener { docs ->
                                            if (!docs.isEmpty) {
                                                val d = docs.first()
                                                docId = d.id
                                                val h = d.toObject(Horarios::class.java)

                                                id = h.id
                                                diasSeleccionados = h.dias.split(",").toSet()
                                                hora = h.hora
                                                tiposSeleccionados = h.tipoResiduo.split(",").toSet()
                                            }
                                        }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247))
                        ) {
                            Text("Buscar", color = Color.White)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Días
                    Text("Días de recolección", fontSize = 18.sp, color = Color(0xFF446247))
                    Spacer(Modifier.height(5.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        dias.forEach { dia ->
                            val selected = diasSeleccionados.contains(dia)

                            Button(
                                onClick = {
                                    diasSeleccionados =
                                        if (selected) diasSeleccionados - dia else diasSeleccionados + dia
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected)
                                        Color(0xFF446247) else Color.LightGray
                                ),
                                modifier = Modifier.weight(1f).padding(1.dp)
                            ) {
                                Text(dia, color = Color.White)
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    // Hora
                    Text("Hora de recolección", fontSize = 18.sp, color = Color(0xFF446247))
                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val (h, m) = hora.split(":").map { it.toInt() }
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    hora = "%02d:%02d".format(hour, minute)
                                },
                                h, m, true
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(hora, fontSize = 20.sp, color = Color.Black)
                    }

                    Spacer(Modifier.height(16.dp))

                    // Tipos
                    Text("Tipo(s) de residuo", fontSize = 18.sp, color = Color(0xFF446247))
                    Spacer(Modifier.height(8.dp))

                    Column {
                        tipos.forEach { tipo ->
                            val selected = tiposSeleccionados.contains(tipo)
                            Button(
                                onClick = {
                                    tiposSeleccionados =
                                        if (selected) tiposSeleccionados - tipo else tiposSeleccionados + tipo
                                },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected)
                                        Color(0xFF446247) else Color.LightGray
                                )
                            ) {
                                Text(tipo, color = Color.White)
                            }
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    // BOTONES FINALES
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // GUARDAR
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF446247)),
                            onClick = {

                                // Si es nuevo, generar id Long
                                if (docId.isBlank()) {
                                    docId = db.collection("horarios").document().id
                                    id = docId.hashCode().toLong()
                                }

                                val datos = mapOf(
                                    "id" to id,
                                    "colonia" to colonia,
                                    "dias" to diasSeleccionados.joinToString(","),
                                    "hora" to hora,
                                    "tipoResiduo" to tiposSeleccionados.joinToString(","),
                                    "activo" to 1
                                )

                                db.collection("horarios")
                                    .document(docId)
                                    .set(datos)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            context,
                                            if (horarioEditar == null) "Registrado" else "Actualizado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        onFinish()
                                        act.finish()
                                    }
                            }
                        ) {
                            Text(if (horarioEditar == null) "Registrar" else "Actualizar", color = Color.White)
                        }

                        // ELIMINAR
                        Button(
                            enabled = docId.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            onClick = {
                                db.collection("horarios")
                                    .document(docId)
                                    .update("activo", 0)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
                                        onFinish()
                                        act.finish()
                                    }
                            }
                        ) {
                            Text("Eliminar", color = Color.White)
                        }

                        // CANCELAR
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            onClick = { act.finish() }
                        ) {
                            Text("Cancelar", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
