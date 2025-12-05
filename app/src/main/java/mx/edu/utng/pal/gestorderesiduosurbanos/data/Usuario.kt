package mx.edu.utng.pal.gestorderesiduosurbanos.data

data class Usuario(
    var id: String = "",
    var nombreCompleto: String = "",
    var nombreUsuario: String = "",
    var numeroTelefono: String = "",
    var contrasena: String = "",
    var rol: String = "usuario"   // valor por defecto
)

