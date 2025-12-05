package mx.edu.utng.pal.gestorderesiduosurbanos.data

data class Bote(
    val id: Any? = "",     // 🔥 ahora acepta String o Long sin romperse
    val colonia: String = "",
    val tipoResiduo: String = "",
    val estado: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
)
