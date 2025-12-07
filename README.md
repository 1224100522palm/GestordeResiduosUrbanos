Gestor de Residuos Urbanos es una aplicación móvil diseñada para mejorar la forma en que los ciudadanos reportan, registran y visualizan problemas relacionados con los residuos urbanos en su comunidad. Su propósito es ofrecer un medio accesible y rápido para notificar incidentes como acumulación de basura, puntos críticos de contaminación o contenedores desbordados. A través de una interfaz intuitiva y funcionalidades modernas, la aplicación busca fomentar la participación ciudadana y facilitar la comunicación entre la población y los servicios municipales responsables del mantenimiento urbano.

La plataforma integra herramientas como geolocalización, registro fotográfico y almacenamiento en la nube para asegurar que cada reporte sea documentado de forma precisa y en tiempo real. Al centralizar esta información en un sistema digital, se habilita una mejor organización de los reportes y una mayor capacidad de análisis por parte de las autoridades, permitiendo identificar patrones, zonas con mayor demanda de atención y oportunidades para optimizar rutas de limpieza.

Además, el proyecto tiene un enfoque ambiental y social, promoviendo prácticas responsables en el manejo de residuos y generando conciencia sobre la importancia de mantener espacios libres de basura. Su diseño modular y escalable permite añadir futuras mejoras, como estadísticas avanzadas, notificaciones o integración con sistemas municipales. En conjunto, Gestor de Residuos Urbanos representa una herramienta tecnológica eficiente para apoyar la mejora continua del entorno urbano.

- Gestión de botes de residuos
Registro, edición y eliminación de botes.
Campos: colonia, tipo de residuo, estado del bote, latitud y longitud.
Uso de IDs consecutivos y búsqueda de botes por ID local.

-Gestión de horarios de recolección
Módulo para registrar horarios por colonia.
Selección de días de la semana (Lu, Ma, Mi, Ju, Vi, Sa, Do).
Selección de tipos de residuo (orgánico, inorgánico, vidrio, plástico, papel, etc.).
Elección de hora con TimePicker y control de “activo/inactivo” en Firestore.

- Módulo de reportes de incidentes
Registro de reportes asociados a un bote y a una colonia.
Campos como usuario, tipo, descripción, fecha y estado del reporte.
Pantalla con lista de reportes, pestañas por estado (por ejemplo “Abiertos”), búsqueda por texto y actualización de estado.

- Módulo de avisos informativos
Registro y edición de avisos con título, descripción, fecha, tipo (Información, Alerta, Recordatorio), colonia y “creadoPor”.
Carga de colonias desde la colección de horarios para que solo se usen colonias registradas.
Soporte para edición de avisos existentes y manejo de ID consecutivo (idInt).

- Mapa de botes en Google Maps
Pantalla de mapa que carga los botes desde Firestore y los muestra georreferenciados.
Uso de Google Maps (LatLng, CameraPosition) para centrar la vista en la zona urbana y visualizar la distribución de botes.

- Gestión de usuarios y roles (admin/usuario)
Pantalla de registro de usuarios con nombre, teléfono, correo y contraseña usando Firebase Authentication.
Al registrarse, si el correo es admin@gmail.com se asigna rol admin, si no, rol usuario.
Los datos del usuario se guardan en la colección usuarios de Firestore y se usan preferencias compartidas para la sesión.

Imágenes de Pantallas
![Mapa de Botes](docs/screenshots/botes.jpg)
![Avisos](docs/screenshots/avisos.jpg)
![Reportes](docs/screenshots/Reportes.jpg)
![Lista de Botes](docs/screenshots/botesUrb.jpg)
![Horarios](docs/screenshots/horarios.jpg)
![Perfil](docs/screenshots/perfil7.jpg)

Tecnologías Utilizadas
La aplicación Gestor de Residuos Urbanos fue desarrollada utilizando un conjunto de tecnologías modernas orientadas a la creación de aplicaciones móviles robustas, escalables y fáciles de mantener. En la capa de presentación se empleó Android Studio como entorno principal de desarrollo, junto con Kotlin como lenguaje base debido a su eficiencia, seguridad en tipos y compatibilidad plena con el ecosistema Android actual. La interfaz de usuario fue implementada mediante Jetpack Compose, el framework declarativo de Android que permite construir pantallas dinámicas, reactivas y con menos código, logrando así una experiencia visual más moderna y fluida para el usuario.
Para el almacenamiento y gestión de datos, la aplicación utiliza Firebase Firestore, una base de datos NoSQL en la nube que permite sincronización en tiempo real y consultas eficientes. Esta tecnología es fundamental para funciones como la actualización instantánea de reportes, avisos, botes y horarios, lo cual permite que la información se mantenga siempre actualizada entre todos los usuarios de la aplicación. Asimismo, la autenticación se gestiona mediante Firebase Authentication, lo que facilita el registro y el inicio de sesión seguro, diferenciando entre usuarios administradores y usuarios estándar.
En cuanto a la visualización geográfica, se integró Google Maps SDK for Android, que permite mostrar la ubicación de los botes en un mapa interactivo mediante marcadores. Esta tecnología brinda herramientas esenciales para manejar coordenadas, mover la cámara, personalizar iconos y ofrecer una visualización clara de la distribución de puntos de recolección de residuos.
Además, la aplicación hace uso de componentes clave de Android Jetpack, como ViewModel, LiveData/StateFlow, coroutines y navegación por pantallas, lo que asegura una arquitectura organizada, con separación de responsabilidades y capacidad para escalar a nuevas funcionalidades. Finalmente, para persistencia local y manejo de sesión, se implementaron SharedPreferences, permitiendo recordar datos del usuario, rol y ajustes internos sin necesidad de acceder constantemente a la base de datos. En conjunto, estas tecnologías garantizan que Gestor de Residuos Urbanos sea una aplicación moderna, estable, rápida, segura y preparada para crecer con nuevas funciones en futuras versiones.


#  Gestor de Residuos Urbanos - Manual Completo
## Android con Jetpack Compose + Firebase

> **Manual profesional para estudiantes de desarrollo móvil**


---

##  Arquitectura del Proyecto

```
GestordeResiduosUrbanos/
├─ app/
│  ├─ data/
│  │  ├─ Aviso.kt
│  │  ├─ Bote.kt
│  │  ├─ Reporte.kt
│  │  ├─ Usuario.kt
│  │  ├─ Horarios.kt
│  │  └─ Repositories/
│  └─ ui/
│     ├─ auth/
│     │  ├─ LoginActivity.kt
│     │  └─ RegistroActivity.kt
│     ├─ main/
│     │  ├─ MainActivity.kt
│     │  ├─ PantallaMapaActivity.kt
│     │  ├─ PantallaBotesActivity.kt
│     │  ├─ PantallaAvisoActivity.kt
│     │  └─ PantallaReportesActivity.kt
│     └─ registro/
│        ├─ PantallaRegistroBoteActivity.kt
│        ├─ PantallaRegistroAvisoActivity.kt
│        ├─ PantallaRegistroHorarioActivity.kt
│        └─ PantallaRegistroReporteActivity.kt
```

---

## 🔧 Configuración Inicial

### build.gradle.kts (Module: app)

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "mx.edu.utng.pal.gestorderesiduosurbanos"
    compileSdk = 34

    defaultConfig {
        applicationId = "mx.edu.utng.pal.gestorderesiduosurbanos"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    // Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.8.2")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    
    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:4.3.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}
```

---

## 📦 Modelos de Datos

### Aviso.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.data

/**
 * Modelo de datos para avisos municipales
 * 
 * Analogía: Como un anuncio en el tablón de la comunidad
 */
data class Aviso(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val tipo: String = "", // Información, Alerta, Recordatorio
    val colonia: String = "",
    val creadoPor: String = ""
)
```

### Bote.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.data

/**
 * Modelo de datos para contenedores de residuos
 * 
 * Analogía: Como un punto de servicio en tu ciudad
 */
data class Bote(
    val id: Any? = "",
    val colonia: String = "",
    val tipoResiduo: String = "", // Orgánico, Inorgánico, Reciclable
    val estado: String = "", // Vacío, Medio, Lleno, Mantenimiento
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
```

### Reporte.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.data

/**
 * Modelo de datos para reportes de incidencias
 * 
 * Analogía: Como una solicitud de servicio municipal
 */
data class Reporte(
    val id: String = "",
    val usuario: String = "",
    val bote: String = "",
    val colonia: String = "",
    val tipo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val estado: String = "", // Pendiente, En Proceso, Resuelto
    val firebaseId: String = ""
)
```

---

## 🗄️ Repositorios

### AvisoRepository.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para gestionar avisos
 * 
 * Patrón Repository: Abstrae el acceso a datos
 * Beneficios:
 * - Única fuente de verdad
 * - Fácil de testear
 * - Desacoplamiento de la UI
 */
class AvisoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val ref = db.collection("avisos")

    /**
     * Obtiene avisos en tiempo real
     * 
     * Flow: Emisión continua de datos
     * Se actualiza automáticamente cuando cambia Firestore
     */
    fun getAvisosFlow(): Flow<List<Aviso>> = callbackFlow {
        val listener = ref.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            snapshot?.let {
                trySend(it.toObjects(Aviso::class.java))
            }
        }
        awaitClose { listener.remove() }
    }

    /**
     * Inserta un nuevo aviso
     * 
     * suspend: Función asíncrona
     * No bloquea el hilo principal
     */
    suspend fun insert(aviso: Aviso) {
        val doc = ref.document()
        val nuevo = aviso.copy(id = doc.id)
        doc.set(nuevo).await()
    }

    suspend fun update(aviso: Aviso) {
        ref.document(aviso.id).set(aviso).await()
    }

    suspend fun delete(aviso: Aviso) {
        ref.document(aviso.id).delete().await()
    }

    suspend fun getById(id: String): Aviso? {
        return ref.document(id).get().await().toObject(Aviso::class.java)
    }
}
```

---

##  Autenticación

### LoginActivity.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

/**
 * Pantalla de inicio de sesión
 * 
 * Flujo:
 * 1. Usuario ingresa credenciales
 * 2. Firebase Authentication valida
 * 3. Guardar sesión en SharedPreferences
 * 4. Navegar a MainActivity
 */
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaLogin()
        }
    }
}

@Composable
fun PantallaLogin() {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFE8F0E8)
    ) { padding ->
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            // Título
            Text(
                text = "Gestor de Residuos Urbanos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF446247)
            )
            
            Spacer(Modifier.height(32.dp))
            
            // Campo de correo
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(Modifier.height(16.dp))
            
            // Campo de contraseña
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            
            Spacer(Modifier.height(24.dp))
            
            // Botón de inicio de sesión
            Button(
                onClick = {
                    scope.launch {
                        if (correo.isBlank() || contrasena.isBlank()) {
                            snackbarHostState.showSnackbar("Completa todos los campos")
                        } else {
                            auth.signInWithEmailAndPassword(correo, contrasena)
                                .addOnSuccessListener {
                                    // Guardar sesión
                                    val prefs = context.getSharedPreferences("sesion", Activity.MODE_PRIVATE)
                                    prefs.edit().putString("usuario_correo", correo).apply()
                                    
                                    // Navegar
                                    context.startActivity(Intent(context, MainActivity::class.java))
                                }
                                .addOnFailureListener {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Error de autenticación")
                                    }
                                }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF446247)
                )
            ) {
                Text("Iniciar sesión")
            }
            
            // Enlace a registro
            TextButton(
                onClick = {
                    context.startActivity(Intent(context, RegistroActivity::class.java))
                }
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
```

---

##  Pantallas Principales

### PantallaBotesActivity.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote

/**
 * Pantalla de lista de botes
 * 
 * Características:
 * - Lista en tiempo real desde Firestore
 * - Búsqueda por múltiples criterios
 * - Navegación a detalle/edición
 * - FAB para agregar nuevos botes
 */
class PantallaBotesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaBotes() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBotes() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    
    var listaBotes by remember { mutableStateOf(listOf<Bote>()) }
    var busqueda by remember { mutableStateOf("") }
    
    // Listener en tiempo real
    DisposableEffect(Unit) {
        val listener = db.collection("botes")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    listaBotes = it.toObjects(Bote::class.java)
                }
            }
        onDispose { listener.remove() }
    }
    
    // Filtrado de búsqueda
    val listaFiltrada = remember(listaBotes, busqueda) {
        listaBotes.filter {
            it.id.toString().contains(busqueda, true) ||
            it.colonia.contains(busqueda, true) ||
            it.tipoResiduo.contains(busqueda, true) ||
            it.estado.contains(busqueda, true)
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Botes", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF446247)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.startActivity(Intent(context, PantallaRegistroBoteActivity::class.java))
                },
                containerColor = Color(0xFF446247)
            ) {
                Icon(Icons.Default.Add, "Agregar", tint = Color.White)
            }
        },
        bottomBar = { BottomNavigationBarBotes() }
    ) { padding ->
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            
            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar bote...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true
            )
            
            Spacer(Modifier.height(16.dp))
            
            // Lista de botes
            if (listaFiltrada.isEmpty()) {
                Text(
                    "No hay botes registrados",
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(listaFiltrada) { bote ->
                        BoteItem(bote = bote)
                    }
                }
            }
        }
    }
}

@Composable
fun BoteItem(bote: Bote) {
    val context = LocalContext.current
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                val intent = Intent(context, PantallaRegistroBoteActivity::class.java)
                intent.putExtra("boteId", bote.id.toString())
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "ID: ${bote.id}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF446247),
                fontSize = 16.sp
            )
            Spacer(Modifier.height(4.dp))
            Text("📍 Colonia: ${bote.colonia}")
            Text("🗑️ Tipo: ${bote.tipoResiduo}")
            Text("⚡ Estado: ${bote.estado}")
            Text("🌍 Ubicación: ${bote.lat}, ${bote.lng}", fontSize = 12.sp)
        }
    }
}

@Composable
fun BottomNavigationBarBotes() {
    val context = LocalContext.current
    
    NavigationBar(
        containerColor = Color(0xFFF8F8F8)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { context.startActivity(Intent(context, PantallaMapaActivity::class.java)) },
            icon = { Icon(Icons.Default.LocationOn, "Mapa") },
            label = { Text("Mapa") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Delete, "Botes") },
            label = { Text("Botes") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { context.startActivity(Intent(context, PantallaReportesActivity::class.java)) },
            icon = { Icon(Icons.Default.Report, "Reportes") },
            label = { Text("Reportes") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { context.startActivity(Intent(context, PantallaAvisoActivity::class.java)) },
            icon = { Icon(Icons.Default.Notifications, "Avisos") },
            label = { Text("Avisos") }
        )
    }
}
```

---

##  Formularios de Registro

### PantallaRegistroBoteActivity.kt

```kotlin
package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote

/**
 * Formulario para registrar/editar botes
 * 
 * Modos:
 * - Registro: boteId vacío
 * - Edición: boteId con valor
 */
class PantallaRegistroBoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val boteId = intent.getStringExtra("boteId") ?: ""
        setContent {
            PantallaRegistroBoteUI(boteId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroBoteUI(boteId: String) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()
    
    var colonia by remember { mutableStateOf("") }
    var tipoResiduo by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf("") }
    var lng by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    // Cargar datos si es edición
    LaunchedEffect(boteId) {
        if (boteId.isNotEmpty()) {
            isLoading = true
            val doc = db.collection("botes").document(boteId).get().await()
            doc.toObject(Bote::class.java)?.let { bote ->
                colonia = bote.colonia
                tipoResiduo = bote.tipoResiduo
                estado = bote.estado
                lat = bote.lat.toString()
                lng = bote.lng.toString()
            }
            isLoading = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (boteId.isEmpty()) "Registrar Bote" else "Editar Bote",
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF446247)
                )
            )
        }
    ) { padding ->
        
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                
                OutlinedTextField(
                    value = colonia,
                    onValueChange = { colonia = it },
                    label = { Text("Colonia") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = tipoResiduo,
                    onValueChange = { tipoResiduo = it },
                    label = { Text("Tipo de residuo") },
                    placeholder = { Text("Ej: Orgánico, Inorgánico, Reciclable") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = estado,
                    onValueChange = { estado = it },
                    label = { Text("Estado") },
                    placeholder = { Text("Ej: Vacío, Medio, Lleno") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = lat,
                        onValueChange = { lat = it },
                        label = { Text("Latitud") },
                        modifier = Modifier.weight(1f)
                    )
                    
                    OutlinedTextField(
                        value = lng,
                        onValueChange = { lng = it },
                        label = { Text("Longitud") },
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        scope.launch {
                            val latDouble = lat.toDoubleOrNull() ?: 0.0
                            val lngDouble = lng.toDoubleOrNull() ?: 0.0
                            
                            val datos = hashMapOf(
                                "colonia" to colonia,
                                "tipoResiduo" to tipoResiduo,
                                "estado" to estado,
                                "lat" to latDouble,
                                "lng" to lngDouble
                            )
                            
                            if (boteId.isEmpty()) {
                                // Nuevo bote
                                val doc = db.collection("botes").document()
                                datos["id"] = doc.id
                                doc.set(datos).await()
                            } else {
                                // Actualizar
                                datos["id"] = boteId
                                db.collection("botes").document(boteId).set(datos).await()
                            }
                            
                            (context as? Activity)?.finish()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF446247)
                    ),
                    enabled = colonia.isNotBlank() && tipoResiduo.isNotBlank()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}
```

---

## Video Demostrativo

[Ver video en YouTube](https://www.youtube.com/watch?v=By6weXnRxCg)

---

## Licencia

Proyecto educativo desarrollado para la **Universidad Tecnológica del Norte de Guanajuato**  
Asignatura: Aplicaciones Móviles – Unidad IV

---

## Tecnologías Utilizadas

- **Android Studio** - IDE oficial
- **Kotlin** - Lenguaje moderno y seguro
- **Jetpack Compose** - UI declarativa
- **Firebase Firestore** - Base de datos NoSQL
- **Firebase Authentication** - Autenticación segura
- **Google Maps SDK** - Mapas interactivos
- **Coroutines** - Programación asíncrona
- **Material Design 3** - Sistema de diseño moderno

---
