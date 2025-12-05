Gestor de Residuos Urbanos

Aplicación móvil Android para el registro, consulta y análisis de reportes relacionados con residuos urbanos dentro de una comunidad.


📌 Descripción del Proyecto

Gestor de Residuos Urbanos es una aplicación móvil desarrollada para facilitar la captura, consulta y análisis de reportes relacionados con desechos urbanos. La aplicación permite visualizar puntos críticos mediante Google Maps, consultar reportes registrados, revisar horarios de recolección, recibir avisos municipales y generar estadísticas que apoyan la toma de decisiones ambientales.

El proyecto implementa una arquitectura modular, separando la interfaz de usuario, la capa de datos, las utilidades y los servicios externos. Esto garantiza que el sistema sea escalable, mantenible y fácil de extender. La integración con Google Maps SDK permite representar información georreferenciada de manera clara y precisa.

Este proyecto forma parte de la Unidad 4: Publicación de Aplicaciones Móviles, cuyo propósito es documentar correctamente la app, preparar un repositorio profesional, establecer un proceso de publicación y validar su funcionamiento con usuarios reales.

⭐ Características Principales

Visualización de reportes en un mapa mediante Google Maps SDK.

Consulta detallada de reportes registrados.

Pantalla de estadísticas con información organizada.

Navegación mediante Activities.

Modelos de datos reutilizables y documentados.

Funciones utilitarias para validaciones, formatos y cálculos.

🧰 Tecnologías Utilizadas
Lenguaje y Plataforma

Kotlin

Android Studio

Android SDK (API 21+)

Arquitectura

Activities y ViewModels

Paquetes organizados en:

ui/ → Pantallas y controladores

model/ → Clases modelo

utils/ → Funciones auxiliares

network/ → Cliente API y servicios externos

Servicios Externos

Google Maps SDK for Android

API externa / Backend REST (si aplica)

Firebase:

Authentication

Firestore Database

Analytics

Dependencias Principales
com.google.android.gms:play-services-maps
com.google.android.material:material
com.google.firebase:firebase-auth-ktx
com.google.firebase:firebase-firestore-ktx

🖼️ Imágenes de Pantallas

Las imágenes se encuentran en:

/docs/screenshots

![Mapa de Botes](docs/screenshots/botes.jpg)
![Avisos](docs/screenshots/avisos.jpg)
![Reportes](docs/screenshots/Reportes.jpg)
![Lista de Botes](docs/screenshots/botesUrb.jpg)
![Horarios](docs/screenshots/horarios.jpg)
![Perfil](docs/screenshots/perfil7.jpg)

🛠 Instrucciones de Instalación
Requisitos Previos

Android Studio actualizado

Android SDK API 21 o superior

Emulador o dispositivo con Google Play Services

API Key configurada en:

app/src/debug/res/values/google_maps_api.xml  
app/src/release/res/values/google_maps_api.xml

1. Clonar el repositorio
git clone https://github.com/1224100522palm/GestordeResiduosUrbanos.git

2. Abrir el proyecto en Android Studio

File → Open

Seleccionar la carpeta del proyecto

3. Configurar API Key de Google Maps

Insertar tu clave en ambos archivos:

<string name="google_maps_key">TU_API_KEY_AQUI</string>

4. Ejecutar la app
Opción A — Emulador

Crear dispositivo virtual con Google Play Services

Seleccionarlo

Presionar Run

Opción B — Dispositivo físico

Activar Depuración USB

Conectar por cable

Autorizar PC

Presionar Run

5. Generar APK (opcional)
Build → Build APK(s)


APK generado en:

app/build/outputs/apk/debug/app-debug.apk

📂 Estructura del Proyecto
GestorResiduosUrbanos/

├── app/

│   └── src/

│       └── main/

│           ├── java/mx/edu/utng/pal/gestorderesiduosurbanos/

│           │   ├── ui/

│           │   ├── model/

│           │   ├── utils/

│           │   └── network/

│           ├── res/

│           └── AndroidManifest.xml

│

├── docs/

│   └── screenshots/

│

├── README.md

├── .gitignore

└── build.gradle

📘 Código Documentado (KDoc)
Modelo
/**
 * Representa un reporte dentro del sistema de gestión de residuos.
 *
 * @property id Identificador único del reporte.
 * @property descripcion Texto descriptivo del incidente.
 * @property fecha Fecha en formato ISO (YYYY-MM-DD).
 * @property latitud Latitud asociada al reporte.
 * @property longitud Longitud asociada al reporte.
 */
data class Reporte(
    val id: Int,
    val descripcion: String,
    val fecha: String,
    val latitud: Double,
    val longitud: Double
)

Activity
/**
 * Activity encargada de mostrar estadísticas relacionadas con los reportes.
 */
class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        cargarEstadisticas()
    }

    /**
     * Recupera valores estadísticos desde la capa de datos
     * y actualiza la interfaz visual.
     */
    private fun cargarEstadisticas() {
        // Implementación pendiente
    }
}

📄 Archivo .gitignore
.gradle/
build/
**/build/
local.properties
.idea/
*.iml
*.log
*.jks
*.keystore
captures/
output.json
app/release/
app/debug/
*.apk
*.aab
.DS_Store
Thumbs.db
*.tmp
*.swp
*.lock

🎥 Video de Demostración

https://www.youtube.com/watch?v=By6weXnRxCg

📜 Licencia

Proyecto desarrollado con fines educativos para la Universidad Tecnológica del Norte de Guanajuato, dentro de la asignatura de Aplicaciones Móviles – Unidad IV.
