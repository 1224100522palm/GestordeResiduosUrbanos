Gestor de Residuos Urbanos

Aplicación móvil Android para el registro, consulta y análisis de reportes relacionados con residuos urbanos dentro de una comunidad.


Descripción del Proyecto

Gestor de Residuos Urbanos es una aplicación móvil desarrollada para facilitar la captura y visualización de reportes relacionados con desechos en zonas urbanas. La aplicación permite identificar puntos críticos mediante Google Maps, consultar reportes existentes y revisar estadísticas que apoyan la toma de decisiones ambientales.

El sistema está construido con una arquitectura modular que separa la interfaz de usuario, la capa de datos, las utilidades y los servicios externos. Esto permite que la aplicación sea más escalable, mantenible y fácil de extender. La integración con Google Maps SDK permite mostrar información georreferenciada de forma clara y precisa.

Este proyecto forma parte de la Unidad 4: Publicación de Aplicaciones Móviles, cuyo objetivo es documentar adecuadamente la app, preparar un repositorio profesional, implementar un proceso de publicación y validar la usabilidad con usuarios reales.


Características Principales

Visualización de reportes en un mapa mediante Google Maps SDK.

Consulta detallada de reportes registrados.

Pantalla de estadísticas con información organizada.

Navegación mediante Activities estructuradas.

Modelos de datos reutilizables y documentados.

Funciones utilitarias para validaciones y formatos.


Tecnologías Utilizadas
Lenguaje y Plataforma

Kotlin

Android Studio (IDE)

Android SDK (API 21 o superior)


Arquitectura y Componentes

Activities y ViewModels 


Paquetes organizados en:

ui/ → Pantallas y controladores

model/ → Clases modelo y entidades

utils/ → Funciones auxiliares

network/ → API Client y consumo de servicios externos


Servicios Externos

Google Maps SDK for Android

API externa o Backend REST (si aplica)

Firebase


Authentication

Firestore / Realtime Database

Analytics

Dependencias Principales
com.google.android.gms:play-services-maps
com.google.android.material:material

Imagenes de pantallas 
/docs/screenshots

Instrucciones de Instalación
Requisitos Previos

Tener Android Studio instalado en su versión actual.

Contar con Android SDK configurado (API 21 o superior).

Usar un dispositivo o emulador con Google Play Services.

Configurar una API Key de Google Maps en los archivos:

app/src/debug/res/values/google_maps_api.xml
app/src/release/res/values/google_maps_api.xml

Pasos de Instalación
1. Clonar el repositorio
git clone https://github.com/TU-USUARIO/gestor-residuos-urbanos.git

2. Abrir el proyecto en Android Studio

File → Open

Seleccionar la carpeta del proyecto

3. Configurar API Key de Google Maps

Insertar tu clave en los archivos correspondientes:

<string name="google_maps_key">TU_API_KEY_AQUI</string>

4. Ejecutar la aplicación

Opción A: Emulador Android

Crear un dispositivo virtual con Google Play Services

Seleccionarlo

Presionar el botón Run

Opción B: Dispositivo físico

Activar Depuración USB

Conectar por cable

Aceptar permisos

Presionar Run

5. Compilación manual (opcional)

Generar un APK debug:

Build → Build APK(s)

El archivo se generará en:

app/build/outputs/apk/debug/app-debug.apk

Estructura del Proyecto

GestorResiduosUrbanos/

├── app/

│   └── src/

│       └── main/

│           ├── java/mx/edu/utng/pal/gestorderesiduosurbanos/

│           │   ├── ui/

│           │   │   ├── actividades y pantallas

│           │   │   └── navegación

│           │   ├── model/

│           │   │   ├── clases modelo

│           │   │   └── entidades

│           │   ├── utils/

│           │   │   ├── funciones auxiliares

│           │   │   ├── validaciones

│           │   │   └── formateadores

│           │   └── network/

│           │       ├── clases para comunicación API

│           │       └── configuración de cliente de red

│           ├── res/

│           │   ├── layout/

│           │   ├── values/

│           │   └── drawable/

│           └── AndroidManifest.xml

│

├── docs/

│   └── screenshots/

│

├── README.md

├── .gitignore

└── build.gradle


Ejemplos de Código Documentado (KDoc)
Modelo
/**
 * Representa un reporte dentro del sistema de gestión de residuos.
 *
 * Esta clase modela la información que un usuario registra cuando reporta
 * un punto de residuos en la ciudad.
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

Archivo .gitignore
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

Video de Demostración

https://www.youtube.com/watch?v=By6weXnRxCg

Licencia

Proyecto desarrollado con fines educativos para la Universidad Tecnológica del Norte de Guanajuato dentro de la asignatura Aplicaciones Móviles – Unidad IV.
