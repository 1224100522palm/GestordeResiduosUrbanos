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



# Gestor de Residuos Urbanos (Android – Jetpack Compose + Firebase)

Aplicación Android para la **gestión de residuos urbanos** que integra:

- Autenticación con Firebase Authentication
- Gestión de botes (lista, registro, edición) en Firestore
- Mapa de botes de basura (vista general)
- Avisos municipales y de la comunidad
- Reportes de incidencias
- Registro de horarios de recolección
- Pantallas de registro (botes, avisos, horarios, reportes)

GestordeResiduosUrbanos/
├─ settings.gradle.kts
├─ build.gradle.kts
└─ app/
   ├─ build.gradle.kts
   └─ src/
      └─ main/
         ├─ AndroidManifest.xml
         ├─ java/
         │  └─ mx/edu/utng/pal/gestorderesiduosurbanos/
         │     ├─ data/
         │     │  ├─ Aviso.kt
         │     │  ├─ Bote.kt
         │     │  ├─ Reporte.kt
         │     │  ├─ Usuario.kt
         │     │  ├─ Horarios.kt
         │     │  ├─ AvisoRepository.kt
         │     │  ├─ BoteRepository.kt
         │     │  ├─ ReporteRepository.kt
         │     │  └─ HorariosRepository.kt
         │     │
         │     └─ ui/
         │        ├─ LoginActivity.kt
         │        ├─ RegistroActivity.kt
         │        ├─ MainActivity.kt
         │        ├─ PantallaMapaActivity.kt
         │        ├─ PantallaBotesActivity.kt
         │        ├─ PantallaAvisoActivity.kt
         │        ├─ PantallaReportesActivity.kt
         │        ├─ PantallaRegistroBoteActivity.kt
         │        ├─ PantallaRegistroAvisoActivity.kt
         │        ├─ PantallaRegistroHorarioActivity.kt
         │        └─ PantallaRegistroReporteActivity.kt
         │
         └─ res/
            ├─ layout/
            ├─ values/
            │  ├─ colors.xml
            │  ├─ themes.xml
            │  └─ strings.xml
            └─ mipmap-*/
1. Modelos de datos (data/)
1.1. Aviso.kt
kotlin
Copiar código
package mx.edu.utng.pal.gestorderesiduosurbanos.data

/**
 * Representa un aviso o comunicado dentro del sistema de gestión de residuos urbanos.
 *
 * Esta clase modela los datos principales que se muestran en la lista de avisos y
 * que se almacenan en Firebase Firestore.
 *
 * @property id Identificador único del aviso (ID del documento en Firestore).
 * @property titulo Título breve que resume el contenido del aviso.
 * @property descripcion Descripción más detallada del aviso.
 * @property fecha Fecha en la que se creó o publicó el aviso.
 * @property tipo Tipo de aviso (informativo, emergencia, comunidad, municipio, etc.).
 * @property colonia Colonia o zona a la que va dirigido el aviso.
 * @property creadoPor Identificador o correo del usuario/administrador que creó el aviso.
 */
data class Aviso(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val tipo: String = "",
    val colonia: String = "",
    val creadoPor: String = ""
)
1.2. Bote.kt
kotlin
Copiar código
package mx.edu.utng.pal.gestorderesiduosurbanos.data

/**
 * Representa un contenedor de residuos (bote) dentro de la aplicación.
 *
 * Se utiliza tanto para mostrar información en listas como en el mapa, y
 * para registrar su ubicación geográfica.
 *
 * @property id Identificador único del bote (documento en Firestore).
 * @property colonia Colonia o zona donde se ubica el bote.
 * @property tipoResiduo Tipo de residuo que admite (orgánico, inorgánico, reciclable, etc.).
 * @property estado Estado actual del bote (lleno, vacío, reportado, en mantenimiento, etc.).
 * @property lat Latitud de la ubicación del bote (para Google Maps).
 * @property lng Longitud de la ubicación del bote (para Google Maps).
 */
data class Bote(
    val id: Any? = "",
    val colonia: String = "",
    val tipoResiduo: String = "",
    val estado: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
1.3. Reporte.kt
kotlin
Copiar código
package mx.edu.utng.pal.gestorderesiduosurbanos.data

/**
 * Modela un reporte de incidencias relacionado con los botes de residuos.
 *
 * Cada reporte permite a los usuarios informar problemas como botes llenos,
 * desbordamientos, daño al contenedor, entre otros.
 *
 * @property id Identificador interno del reporte.
 * @property usuario Usuario o correo que generó el reporte.
 * @property bote ID del bote al que está asociado el reporte.
 * @property colonia Colonia donde se encuentra el bote y ocurre la incidencia.
 * @property tipo Tipo de incidencia reportada.
 * @property descripcion Descripción detallada del problema observado.
 * @property fecha Fecha de creación del reporte.
 * @property estado Estado actual del reporte (pendiente, en proceso, atendido, etc.).
 * @property firebaseId Identificador del documento en Firestore (si se usa de forma explícita).
 */
data class Reporte(
    val id: String = "",
    val usuario: String = "",
    val bote: String = "",
    val colonia: String = "",
    val tipo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val estado: String = "",
    val firebaseId: String = ""
)
(…y así con Usuario.kt, Horarios.kt, siguiendo el mismo patrón de documentación.)

2. Repositorios (acceso a Firestore)
2.1. AvisoRepository.kt
kotlin
Copiar código
package mx.edu.utng.pal.gestorderesiduosurbanos.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

/**
 * Repositorio encargado de gestionar todas las operaciones sobre la colección
 * "avisos" en Firebase Firestore.
 *
 * Implementa un enfoque reactivo mediante [Flow] para escuchar cambios
 * en tiempo real y funciones `suspend` para operaciones puntuales.
 */
class AvisoRepository {

    /** Instancia de Firestore utilizada por el repositorio. */
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    /** Referencia directa a la colección "avisos". */
    private val ref = db.collection("avisos")

    /**
     * Devuelve un flujo que emite la lista completa de avisos cada vez que
     * ocurre un cambio en la colección de Firestore.
     *
     * @return [Flow] de listas de [Aviso].
     */
    fun getAvisosFlow(): Flow<List<Aviso>> = callbackFlow {
        val listener = ref.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                trySend(snapshot.toObjects(Aviso::class.java))
            }
        }
        awaitClose { listener.remove() }
    }

    /**
     * Inserta un nuevo aviso en la colección.
     *
     * Se genera un ID automático en Firestore y se asigna al campo [Aviso.id].
     *
     * @param aviso Aviso a registrar.
     */
    suspend fun insert(aviso: Aviso) {
        val doc = ref.document()
        val nuevoAviso = aviso.copy(id = doc.id)
        doc.set(nuevoAviso).await()
    }

    /**
     * Actualiza un aviso existente sobrescribiendo su documento completo
     * en Firestore.
     *
     * @param aviso Aviso con los datos actualizados.
     */
    suspend fun update(aviso: Aviso) {
        ref.document(aviso.id).set(aviso).await()
    }

    /**
     * Elimina el documento asociado al aviso proporcionado.
     *
     * @param aviso Aviso a eliminar de la base de datos.
     */
    suspend fun delete(aviso: Aviso) {
        ref.document(aviso.id).delete().await()
    }

    /**
     * Obtiene un aviso a partir de su identificador de documento.
     *
     * @param id ID del documento en Firestore.
     * @return El aviso encontrado o `null` si no existe.
     */
    suspend fun getById(id: String): Aviso? {
        return ref.document(id).get().await().toObject(Aviso::class.java)
    }
}
3. Pantallas de Autenticación
3.1. LoginActivity.kt (Activity + Composable)
kotlin
Copiar código
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
 * Activity principal de autenticación.
 *
 * Muestra la pantalla de inicio de sesión utilizando Jetpack Compose.
 * Si el usuario se autentica correctamente, se navega hacia [MainActivity].
 */
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaLogin()
        }
    }
}

/**
 * Composable que construye la interfaz de la pantalla de inicio de sesión.
 *
 * Esta pantalla permite al usuario introducir sus credenciales de acceso
 * y valida la autenticación contra Firebase Authentication.
 */
@Composable
fun PantallaLogin() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFE8F0E8)
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Gestor de Residuos Urbanos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF446247),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            if (correo.isBlank() || contrasena.isBlank()) {
                                snackbarHostState.showSnackbar("Completa todos los campos")
                            } else {
                                auth.signInWithEmailAndPassword(correo, contrasena)
                                    .addOnSuccessListener {
                                        val prefs = context.getSharedPreferences(
                                            "sesion",
                                            Activity.MODE_PRIVATE
                                        )
                                        prefs.edit()
                                            .putString("usuario_correo", correo)
                                            .apply()

                                        context.startActivity(
                                            Intent(context, MainActivity::class.java)
                                        )
                                    }
                                    .addOnFailureListener {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Error de autenticación"
                                            )
                                        }
                                    }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF446247),
                        contentColor = Color.White
                    )
                ) {
                    Text("Iniciar sesión")
                }

                TextButton(
                    onClick = {
                        context.startActivity(
                            Intent(context, RegistroActivity::class.java)
                        )
                    }
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
        }
    }
}
3.2. RegistroActivity.kt
kotlin
Copiar código
package mx.edu.utng.pal.gestorderesiduosurbanos.ui

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Usuario

/**
 * Activity encargada del registro de nuevos usuarios.
 *
 * Crea cuentas en Firebase Authentication y registra la información del
 * usuario en la colección "usuarios" de Firestore.
 */
class RegistroActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaRegistro()
        }
    }
}

/**
 * Composable que muestra el formulario de registro de usuario.
 *
 * Permite capturar nombre, correo y contraseña, y realiza el alta
 * del usuario utilizando Firebase.
 */
@Composable
fun PantallaRegistro() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var nombre by remember { mutableStateOf("") }
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Crear cuenta",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF446247)
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (correo.isBlank() || contrasena.isBlank() || nombre.isBlank()) {
                            snackbarHostState.showSnackbar("Completa todos los campos")
                            return@launch
                        }

                        auth.createUserWithEmailAndPassword(correo, contrasena)
                            .addOnSuccessListener { result ->
                                val uid = result.user?.uid.orEmpty()
                                val usuario = Usuario(
                                    uid = uid,
                                    correo = correo,
                                    nombre = nombre,
                                    rol = "usuario"
                                )

                                db.collection("usuarios")
                                    .document(uid)
                                    .set(usuario)

                                context.startActivity(
                                    Intent(context, LoginActivity::class.java)
                                )
                            }
                            .addOnFailureListener {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Error al registrar usuario")
                                }
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF446247),
                    contentColor = Color.White
                )
            ) {
                Text("Registrar")
            }
        }
    }
}
4. MainActivity y Mapa
4.1. MainActivity.kt
kotlin
Copiar código
package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

/**
 * Activity principal posterior al login.
 *
 * Actualmente funciona como contenedor de la pantalla de mapa,
 * pero se podría extender para manejar navegación con NavHost.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaMapa()
        }
    }
}
4.2. PantallaMapaActivity.kt + PantallaMapa
kotlin
Copiar código
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Activity encargada de mostrar la pantalla principal del mapa.
 *
 * Esta pantalla es la vista general donde se visualizan los botes
 * de residuos sobre un mapa (Google Maps u otra implementación).
 */
class PantallaMapaActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantallaMapa()
        }
    }
}

/**
 * Composable que representa la pantalla de mapa.
 *
 * Aquí se puede integrar el componente de Google Maps y agregar marcadores
 * para cada [Bote] registrado en la base de datos.
 */
@Composable
fun PantallaMapa() {
    val context = androidx.compose.ui.platform.LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBarMapa() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Mapa de Botes",
                fontSize = 22.sp,
                color = Color(0xFF446247),
                modifier = Modifier.padding(16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Aquí se muestra el mapa con los botes")
            }
        }
    }
}

/**
 * Barra de navegación inferior de la pantalla de mapa.
 *
 * Permite cambiar entre las diferentes secciones de la aplicación:
 * Mapa, Horarios, Botes, Reportes, Avisos y Perfil/Estadísticas.
 */
@Composable
fun BottomNavigationBarMapa() {
    val context = androidx.compose.ui.platform.LocalContext.current

    NavigationBar(
        containerColor = Color(0xFFF8F8F8),
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("Mapa", Icons.Default.LocationOn, PantallaMapaActivity::class.java),
            Triple("Horarios", Icons.Default.Schedule, PantallaRegistroHorarioActivity::class.java),
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
                icon = { Icon(icon, contentDescription = title, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40)) }
            )
        }
    }
}
5. Pantalla de Botes (PantallaBotesActivity)
kotlin
Copiar código
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
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote

/**
 * Activity encargada de mostrar la lista de botes registrados.
 *
 * La información se obtiene en tiempo real desde la colección "botes"
 * de Firebase Firestore.
 */
class PantallaBotesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { PantallaBotes() }
    }
}

/**
 * Composable principal de la pantalla de botes.
 *
 * Muestra:
 * - Buscador de botes por diferentes criterios.
 * - Lista de botes sincronizada con Firestore.
 * - Botón flotante para registrar nuevos botes.
 * - Barra de navegación inferior específica de la sección.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBotes() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var listaBotes by remember { mutableStateOf(listOf<Bote>()) }
    var busqueda by remember { mutableStateOf("") }

    /**
     * Escucha los cambios en tiempo real de la colección "botes".
     * Cualquier inserción, borrado o actualización se refleja en la UI.
     */
    DisposableEffect(Unit) {
        val listener = db.collection("botes")
            .addSnapshotListener { snapshot, error ->
                if (error == null && snapshot != null) {
                    listaBotes = snapshot.toObjects(Bote::class.java)
                }
            }

        onDispose {
            listener.remove()
        }
    }

    val listaFiltrada = remember(listaBotes, busqueda) {
        listaBotes.filter {
            it.id.toString().contains(busqueda, true) ||
                    it.colonia.contains(busqueda, true) ||
                    it.tipoResiduo.contains(busqueda, true) ||
                    it.estado.contains(busqueda, true)
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFE8F0E8), Color(0xFFDDE7DD))
    )

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
                    context.startActivity(
                        Intent(context, PantallaRegistroBoteActivity::class.java)
                    )
                },
                containerColor = Color(0xFF446247)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Agregar bote",
                    tint = Color.White
                )
            }
        },
        bottomBar = { BottomNavigationBarBotes() },
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

                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = { busqueda = it },
                        label = { Text("Buscar bote…") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(Modifier.height(16.dp))

                    if (listaFiltrada.isEmpty()) {
                        Text(
                            "No hay botes registrados",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    } else {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(listaFiltrada) { bote ->
                                BoteItem(bote = bote)
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Representa una tarjeta individual de un bote dentro de la lista.
 *
 * Muestra los datos más importantes y permite navegar a la pantalla
 * de edición/registro de bote al hacer clic.
 */
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                "ID: ${bote.id}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF446247)
            )
            Text("Colonia: ${bote.colonia}")
            Text("Residuo: ${bote.tipoResiduo}")
            Text("Estado: ${bote.estado}")
            Text("Lat: ${bote.lat}   Lng: ${bote.lng}")
        }
    }
}

/**
 * Barra de navegación inferior para la sección de botes.
 */
@Composable
fun BottomNavigationBarBotes() {
    val context = LocalContext.current

    NavigationBar(
        containerColor = Color(0xFFF8F8F8),
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("Mapa", Icons.Default.LocationOn, PantallaMapaActivity::class.java),
            Triple("Horarios", Icons.Default.Schedule, PantallaRegistroHorarioActivity::class.java),
            Triple("Botes", Icons.Default.Delete, PantallaBotesActivity::class.java),
            Triple("Reportes", Icons.Default.Report, PantallaReportesActivity::class.java),
            Triple("Avisos", Icons.Default.Notifications, PantallaAvisoActivity::class.java),
            Triple("Perfil", Icons.Default.Person, PantallaStatsActivity::class.java)
        )

        items.forEach { (title, icon, screen) ->
            NavigationBarItem(
                selected = title == "Botes",
                onClick = {
                    if (title != "Botes") {
                        context.startActivity(Intent(context, screen))
                    }
                },
                icon = { Icon(icon, contentDescription = title, tint = Color(0xFF3D5F40)) },
                label = { Text(title, color = Color(0xFF3D5F40)) }
            )
        }
    }
}

PantallaReportesActivity.kt
package mx.edu.utng.pal.gestorderesiduosurbanos.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Reporte

/**
* Activity encargada de mostrar la pantalla de reportes de incidencias.
*
* Esta pantalla consume la colección "reportes" de Firebase Firestore y
* muestra cada incidencia registrada por los usuarios o administradores.
* Desde aquí también se puede acceder al formulario para generar nuevos reportes.
  */
  class PantallaReportesActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
  PantallaReportes()
  }
  }
  }

/**
* Composable principal de la pantalla de reportes.
*
* Funcionalidades:
* - Escucha en tiempo real los cambios en la colección "reportes".
* - Muestra la lista de reportes en tarjetas individuales.
* - Permite acceder al formulario de registro de reporte mediante un FAB.
* - Incluye barra superior y barra de navegación inferior.
    */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaReportes() {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

var reportes by remember { mutableStateOf(listOf<Reporte>()) }

/**
    * Se crea un flujo reactivo usando callbackFlow para escuchar los cambios
    * de la colección "reportes" y se recopilan los resultados en [reportes].
      */
      LaunchedEffect(Unit) {
      callbackFlow {
      val listener = db.collection("reportes")
      .addSnapshotListener { snapshot, error ->
      if (error == null && snapshot != null) {
      trySend(snapshot.toObjects(Reporte::class.java))
      }
      }
      awaitClose { listener.remove() }
      }.collect { lista ->
      reportes = lista
      }
      }

Scaffold(
topBar = {
TopAppBar(
title = { Text("Reportes", color = Color.White) },
colors = TopAppBarDefaults.topAppBarColors(
containerColor = Color(0xFF446247)
)
)
},
floatingActionButton = {
FloatingActionButton(
onClick = {
context.startActivity(
Intent(context, PantallaRegistroReporteActivity::class.java)
)
},
containerColor = Color(0xFF446247)
) {
Icon(
imageVector = Icons.Default.Add,
contentDescription = "Agregar reporte",
tint = Color.White
)
}
},
bottomBar = { BottomNavigationBarReportes() }
) { padding ->

     LazyColumn(
         modifier = Modifier
             .fillMaxSize()
             .padding(padding)
             .padding(16.dp)
     ) {
         items(reportes) { reporte ->
             ReporteItem(reporte = reporte)
         }
     }
}
}

/**
* Tarjeta que representa visualmente un [Reporte] dentro de la lista.
*
* Muestra los datos más importantes:
* - Tipo de reporte
* - Usuario que lo generó
* - Bote y colonia asociados
* - Fecha y estado
* - Descripción de la incidencia
*
* Esta vista puede expandirse más adelante para permitir acciones como:
* cambiar estado, editar o eliminar reportes.
  */
  @Composable
  fun ReporteItem(reporte: Reporte) {

  Card(
  modifier = Modifier
  .fillMaxWidth()
  .padding(vertical = 6.dp),
  colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
  elevation = CardDefaults.cardElevation(4.dp),
  shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
  ) {
  Column(Modifier.padding(16.dp)) {
  Text(
  text = "Reporte: ${reporte.tipo}",
  fontSize = 18.sp,
  fontWeight = FontWeight.Bold,
  color = Color(0xFF446247)
  )
  Text("Usuario: ${reporte.usuario}")
  Text("Bote: ${reporte.bote}")
  Text("Colonia: ${reporte.colonia}")
  Text("Fecha: ${reporte.fecha}")
  Text("Estado: ${reporte.estado}")
  Spacer(Modifier.height(4.dp))
  Text("Descripción: ${reporte.descripcion}")
  }
  }
  }

/**
* Barra de navegación inferior específica para la pantalla de reportes.
*
* Marca como seleccionada la sección "Reportes" y permite navegar
* a las demás secciones del sistema: Mapa, Horarios, Botes, Avisos y Perfil.
  */
  @Composable
  fun BottomNavigationBarReportes() {
  val context = LocalContext.current

  NavigationBar(
  containerColor = Color(0xFFF8F8F8),
  tonalElevation = 8.dp
  ) {
  val items = listOf(
  Triple("Mapa", Icons.Default.LocationOn, PantallaMapaActivity::class.java),
  Triple("Horarios", Icons.Default.Schedule, PantallaRegistroHorarioActivity::class.java),
  Triple("Botes", Icons.Default.Delete, PantallaBotesActivity::class.java),
  Triple("Reportes", Icons.Default.Report, PantallaReportesActivity::class.java),
  Triple("Avisos", Icons.Default.Notifications, PantallaAvisoActivity::class.java),
  Triple("Perfil", Icons.Default.Person, PantallaStatsActivity::class.java)
  )

       items.forEach { (title, icon, screen) ->
           NavigationBarItem(
               selected = title == "Reportes",
               onClick = {
                   if (title != "Reportes") {
                       context.startActivity(Intent(context, screen))
                   }
               },
               icon = { Icon(icon, contentDescription = title, tint = Color(0xFF3D5F40)) },
               label = { Text(title, color = Color(0xFF3D5F40)) }
           )
       }
  }
  }

PantallaRegistroBoteActivity.kt
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
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Bote

/**
* Activity encargada de mostrar el formulario de registro y edición de botes.
*
* Si se recibe un extra "boteId", la pantalla funciona en modo edición y
* carga los datos del bote desde Firestore. En caso contrario, crea un
* nuevo registro en la colección "botes".
  */
  class PantallaRegistroBoteActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)

       val boteId = intent.getStringExtra("boteId") ?: ""

       setContent {
           PantallaRegistroBoteUI(boteId = boteId)
       }
  }
  }

/**
* Composable que construye el formulario de registro/edición de un [Bote].
*
* Campos:
* - Colonia
* - Tipo de residuo
* - Estado
* - Latitud
* - Longitud
*
* Cuando el usuario pulsa "Guardar", se realiza:
* - Inserción si [boteId] está vacío.
* - Actualización si [boteId] contiene un ID válido.
*
* @param boteId Identificador del bote a editar. Si está vacío, se registra uno nuevo.
  */
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun PantallaRegistroBoteUI(boteId: String) {
  val context = androidx.compose.ui.platform.LocalContext.current
  val db = FirebaseFirestore.getInstance()
  val scope = rememberCoroutineScope()

  var colonia by remember { mutableStateOf("") }
  var tipoResiduo by remember { mutableStateOf("") }
  var estado by remember { mutableStateOf("") }
  var lat by remember { mutableStateOf("") }
  var lng by remember { mutableStateOf("") }

  /**
    * Si [boteId] no está vacío, se cargan los datos existentes del bote
    * desde Firestore para permitir su edición.
      */
      LaunchedEffect(boteId) {
      if (boteId.isNotEmpty()) {
      val doc = db.collection("botes").document(boteId).get().await()
      val bote = doc.toObject(Bote::class.java)
      if (bote != null) {
      colonia = bote.colonia
      tipoResiduo = bote.tipoResiduo
      estado = bote.estado
      lat = bote.lat.toString()
      lng = bote.lng.toString()
      }
      }
      }

  Scaffold(
  topBar = {
  TopAppBar(
  title = {
  Text(
  text = if (boteId.isEmpty()) "Registrar Bote" else "Editar Bote",
  color = Color.White
  )
  },
  colors = TopAppBarDefaults.topAppBarColors(
  containerColor = Color(0xFF446247)
  )
  )
  }
  ) { padding ->

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

           OutlinedTextField(
               value = tipoResiduo,
               onValueChange = { tipoResiduo = it },
               label = { Text("Tipo de residuo") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = estado,
               onValueChange = { estado = it },
               label = { Text("Estado") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = lat,
               onValueChange = { lat = it },
               label = { Text("Latitud") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = lng,
               onValueChange = { lng = it },
               label = { Text("Longitud") },
               modifier = Modifier.fillMaxWidth()
           )

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
                           // Modo registro
                           val doc = db.collection("botes").document()
                           datos["id"] = doc.id
                           doc.set(datos).await()
                       } else {
                           // Modo edición
                           datos["id"] = boteId
                           db.collection("botes").document(boteId).set(datos).await()
                       }

                       (context as? Activity)?.finish()
                   }
               },
               modifier = Modifier.fillMaxWidth(),
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFF446247),
                   contentColor = Color.White
               )
           ) {
               Text("Guardar")
           }
       }
  }
  }

PantallaRegistroAvisoActivity.kt
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
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mx.edu.utng.pal.gestorderesiduosurbanos.data.Aviso

/**
* Activity encargada de mostrar el formulario para registrar o editar avisos.
*
* Cuando se recibe el extra "avisoId" se cargan los datos del aviso para
* permitir su edición. Si no se recibe, se crea un nuevo aviso.
  */
  class PantallaRegistroAvisoActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)

       val avisoId = intent.getStringExtra("avisoId") ?: ""

       setContent {
           PantallaRegistroAvisoUI(avisoId = avisoId)
       }
  }
  }

/**
* Composable del formulario de registro/edición de [Aviso].
*
* Permite capturar:
* - Título
* - Descripción
* - Fecha
* - Tipo
* - Colonia
*
* @param avisoId Identificador del aviso cuando se realiza una edición.
  */
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun PantallaRegistroAvisoUI(avisoId: String) {
  val context = androidx.compose.ui.platform.LocalContext.current
  val db = FirebaseFirestore.getInstance()
  val scope = rememberCoroutineScope()

  var titulo by remember { mutableStateOf("") }
  var descripcion by remember { mutableStateOf("") }
  var fecha by remember { mutableStateOf("") }
  var tipo by remember { mutableStateOf("") }
  var colonia by remember { mutableStateOf("") }

  /**
    * Si [avisoId] tiene valor, se obtienen los datos del aviso desde Firestore
    * para mostrarlos en el formulario y poder modificarlos.
      */
      LaunchedEffect(avisoId) {
      if (avisoId.isNotEmpty()) {
      val doc = db.collection("avisos").document(avisoId).get().await()
      val aviso = doc.toObject(Aviso::class.java)
      if (aviso != null) {
      titulo = aviso.titulo
      descripcion = aviso.descripcion
      fecha = aviso.fecha
      tipo = aviso.tipo
      colonia = aviso.colonia
      }
      }
      }

  Scaffold(
  topBar = {
  TopAppBar(
  title = {
  Text(
  text = if (avisoId.isEmpty()) "Registrar Aviso" else "Editar Aviso",
  color = Color.White
  )
  },
  colors = TopAppBarDefaults.topAppBarColors(
  containerColor = Color(0xFF446247)
  )
  )
  }
  ) { padding ->

       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(padding)
               .padding(16.dp)
       ) {

           OutlinedTextField(
               value = titulo,
               onValueChange = { titulo = it },
               label = { Text("Título") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = descripcion,
               onValueChange = { descripcion = it },
               label = { Text("Descripción") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = fecha,
               onValueChange = { fecha = it },
               label = { Text("Fecha") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = tipo,
               onValueChange = { tipo = it },
               label = { Text("Tipo de aviso") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = colonia,
               onValueChange = { colonia = it },
               label = { Text("Colonia") },
               modifier = Modifier.fillMaxWidth()
           )

           Spacer(Modifier.height(24.dp))

           Button(
               onClick = {
                   scope.launch {
                       val datos = hashMapOf(
                           "titulo" to titulo,
                           "descripcion" to descripcion,
                           "fecha" to fecha,
                           "tipo" to tipo,
                           "colonia" to colonia
                       )

                       if (avisoId.isEmpty()) {
                           val doc = db.collection("avisos").document()
                           datos["id"] = doc.id
                           doc.set(datos).await()
                       } else {
                           datos["id"] = avisoId
                           db.collection("avisos").document(avisoId).set(datos).await()
                       }

                       (context as? Activity)?.finish()
                   }
               },
               modifier = Modifier.fillMaxWidth(),
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFF446247),
                   contentColor = Color.White
               )
           ) {
               Text("Guardar")
           }
       }
  }
  }

PantallaRegistroHorarioActivity.kt
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
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
* Activity para el registro de horarios de recolección.
*
* Permite dar de alta un horario asociado a una colonia y a un tipo
* de residuo. Los datos se almacenan en la colección "horarios".
  */
  class PantallaRegistroHorarioActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
  PantallaRegistroHorarioUI()
  }
  }
  }

/**
* Composable que construye el formulario de registro de horario.
*
* Campos:
* - Colonia
* - Tipo de residuo
* - Horario (texto libre)
    */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaRegistroHorarioUI() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val scope = rememberCoroutineScope()

var colonia by remember { mutableStateOf("") }
var tipoResiduo by remember { mutableStateOf("") }
var horario by remember { mutableStateOf("") }

Scaffold(
topBar = {
TopAppBar(
title = { Text("Registro de Horario", color = Color.White) },
colors = TopAppBarDefaults.topAppBarColors(
containerColor = Color(0xFF446247)
)
)
}
) { padding ->

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

         OutlinedTextField(
             value = tipoResiduo,
             onValueChange = { tipoResiduo = it },
             label = { Text("Tipo de residuo") },
             modifier = Modifier.fillMaxWidth()
         )

         OutlinedTextField(
             value = horario,
             onValueChange = { horario = it },
             label = { Text("Horario") },
             modifier = Modifier.fillMaxWidth()
         )

         Spacer(Modifier.height(24.dp))

         Button(
             onClick = {
                 scope.launch {
                     val datos = hashMapOf(
                         "colonia" to colonia,
                         "tipoResiduo" to tipoResiduo,
                         "horario" to horario
                     )
                     db.collection("horarios").add(datos)
                     (context as? Activity)?.finish()
                 }
             },
             modifier = Modifier.fillMaxWidth(),
             colors = ButtonDefaults.buttonColors(
                 containerColor = Color(0xFF446247),
                 contentColor = Color.White
             )
         ) {
             Text("Guardar")
         }
     }
}
}

PantallaRegistroReporteActivity.kt
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
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

/**
* Activity que muestra el formulario para registrar un nuevo reporte
* de incidencia sobre los botes de residuos.
*
* Los datos se almacenan en la colección "reportes" de Firestore.
  */
  class PantallaRegistroReporteActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
  PantallaRegistroReporteUI()
  }
  }
  }

/**
* Formulario de registro de [Reporte].
*
* Campos:
* - Usuario
* - ID del bote
* - Colonia
* - Tipo de reporte
* - Descripción
* - Fecha
*
* El estado del reporte se inicializa normalmente como "pendiente".
  */
  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun PantallaRegistroReporteUI() {
  val context = androidx.compose.ui.platform.LocalContext.current
  val db = FirebaseFirestore.getInstance()
  val scope = rememberCoroutineScope()

  var usuario by remember { mutableStateOf("") }
  var bote by remember { mutableStateOf("") }
  var colonia by remember { mutableStateOf("") }
  var tipo by remember { mutableStateOf("") }
  var descripcion by remember { mutableStateOf("") }
  var fecha by remember { mutableStateOf("") }

  Scaffold(
  topBar = {
  TopAppBar(
  title = { Text("Registro de Reporte", color = Color.White) },
  colors = TopAppBarDefaults.topAppBarColors(
  containerColor = Color(0xFF446247)
  )
  )
  }
  ) { padding ->

       Column(
           modifier = Modifier
               .fillMaxSize()
               .padding(padding)
               .padding(16.dp)
       ) {

           OutlinedTextField(
               value = usuario,
               onValueChange = { usuario = it },
               label = { Text("Usuario") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = bote,
               onValueChange = { bote = it },
               label = { Text("ID del bote") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = colonia,
               onValueChange = { colonia = it },
               label = { Text("Colonia") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = tipo,
               onValueChange = { tipo = it },
               label = { Text("Tipo de reporte") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = descripcion,
               onValueChange = { descripcion = it },
               label = { Text("Descripción") },
               modifier = Modifier.fillMaxWidth()
           )

           OutlinedTextField(
               value = fecha,
               onValueChange = { fecha = it },
               label = { Text("Fecha") },
               modifier = Modifier.fillMaxWidth()
           )

           Spacer(Modifier.height(24.dp))

           Button(
               onClick = {
                   scope.launch {
                       val datos = hashMapOf(
                           "usuario" to usuario,
                           "bote" to bote,
                           "colonia" to colonia,
                           "tipo" to tipo,
                           "descripcion" to descripcion,
                           "fecha" to fecha,
                           "estado" to "pendiente"
                       )

                       db.collection("reportes").add(datos)
                       (context as? Activity)?.finish()
                   }
               },
               modifier = Modifier.fillMaxWidth(),
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFF446247),
                   contentColor = Color.White
               )
           ) {
               Text("Guardar")
           }
       }
  }
  }
🎥 Video de Demostración

https://www.youtube.com/watch?v=By6weXnRxCg

📜 Licencia

Proyecto desarrollado con fines educativos para la Universidad Tecnológica del Norte de Guanajuato, dentro de la asignatura de Aplicaciones Móviles – Unidad IV.
