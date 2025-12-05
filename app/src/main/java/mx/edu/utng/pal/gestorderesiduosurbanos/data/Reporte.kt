package mx.edu.utng.pal.gestorderesiduosurbanos.data

data class Reporte(
    val id: String = "",
    val usuario: String = "",
    val bote: String = "",
    val colonia: String = "",
    val tipo: String = "",
    val descripcion: String = "",
    val fecha: String = "",
    val estado: String = "",
    val firebaseId: String
)
