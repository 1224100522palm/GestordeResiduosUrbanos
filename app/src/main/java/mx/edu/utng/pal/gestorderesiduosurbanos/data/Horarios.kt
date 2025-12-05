package mx.edu.utng.pal.gestorderesiduosurbanos.data

data class Horarios(
    val id: Long = 0,
    val colonia: String = "",
    val dias: String = "",
    val hora: String = "",
    val tipoResiduo: String = "",
    val activo: Int = 1
)
