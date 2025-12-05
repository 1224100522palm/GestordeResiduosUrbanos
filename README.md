Gestor de Residuos Urbanos

El proyecto Gestor de Residuos Urbanos es una aplicación móvil Android cuyo propósito es facilitar el registro, visualización y análisis de reportes relacionados con residuos urbanos dentro de una comunidad. La aplicación permite a los usuarios consultar reportes, visualizar su ubicación mediante Google Maps y revisar estadísticas generadas a partir de los datos almacenados.

Este proyecto se desarrolló como parte de la Unidad 4: Publicación de Aplicaciones Móviles, con la finalidad de aprender el proceso completo para documentar, preparar y distribuir una aplicación Android lista para su publicación.


Descripción del Proyecto

El sistema está organizado con una estructura modular que separa la interfaz, los modelos de datos y las funciones utilitarias. A través de una navegación basada en Activities, el usuario puede explorar mapas, revisar reportes, analizar estadísticas y consultar información detallada de los puntos de residuos.

El proyecto integra servicios externos como Google Maps SDK, y fue construido siguiendo buenas prácticas de documentación, estructura de carpetas y uso de modelos de datos reutilizables.


Características Principales

Visualización de reportes en un mapa utilizando Google Maps.

Lista de reportes con información relevante.

Pantalla de estadísticas con datos resumidos.

Navegación entre actividades de manera sencilla.

Estructura de modelos clara y organizada.

Funciones utilitarias para manejo de fechas y validaciones.


Capturas de Pantalla

Las capturas se deben colocar en la carpeta:

docs/screenshots/


Ejemplos recomendados:

docs/screenshots/home.png

docs/screenshots/mapa.png

docs/screenshots/reportes.png


Tecnologías Utilizadas

Lenguaje: Kotlin

IDE: Android Studio.

Android SDK API 21 o superior.

Servicios externos usados:

Google Maps SDK for Android

Firebase 


Instalación y Ejecución
Requisitos Previos

Android Studio instalado.

Dispositivo físico o emulador con Google Play Services activo.

API Key de Google Maps configurada en los archivos:
app/src/debug/res/values/google_maps_api.xml
app/src/release/res/values/google_maps_api.xml


Instrucciones de Instalación

Clonar el repositorio:

git clone https://github.com/TU-USUARIO/gestor-residuos-urbanos.git


Abrir el proyecto en Android Studio.

Configurar la API Key de Google Maps en el archivo correspondiente.

Ejecutar la aplicación en un emulador o dispositivo físico.

Estructura del Proyecto
app/
└── src/
└── main/
├── java/mx/edu/utng/pal/gestorderesiduosurbanos/
│   ├── ui/        (pantallas y navegación)
│   ├── model/     (modelos de datos)
│   ├── utils/     (funciones utilitarias)
│   └── data/      (si se implementan repositorios)
└── res/
├── layout/
├── values/
└── drawable/
docs/
└── screenshots/

Ejemplos de Código Documentado
Ejemplo 1: Modelo Documentado
/**
* Representa un reporte registrado en la aplicación.
*
* @param id Identificador único del reporte.
* @param descripcion Descripción del incidente.
* @param fecha Fecha en la que se generó el reporte.
* @param latitud Coordenada de latitud de la ubicación del reporte.
* @param longitud Coordenada de longitud correspondiente.
  */
  public class Reporte {
  private int id;
  private String descripcion;
  private String fecha;
  private double latitud;
  private double longitud;

  public Reporte(int id, String descripcion, String fecha, double latitud, double longitud) {
  this.id = id;
  this.descripcion = descripcion;
  this.fecha = fecha;
  this.latitud = latitud;
  this.longitud = longitud;
  }
  }

Ejemplo 2: Activity Documentada
/**
* Activity que muestra estadísticas relacionadas con los reportes de residuos.
* Recupera los datos desde una fuente y los presenta en pantalla.
  */
  public class StatsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_stats);
  cargarEstadisticas();
  }

  /**
    * Carga y muestra los valores estadísticos relevantes.
      */
      private void cargarEstadisticas() {
      // Implementación futura
      }
      }

Información del Proyecto

Autor: Paulina López y Alan Ortega

Institución: Universidad Tecnológica del Norte de Guanajuato

Materia: Desarrollo de Aplicaciones Móviles

Unidad 4: Publicación de Aplicaciones


Estado del Proyecto

README completo

Código documentado

Archivo .gitignore configuradoo
