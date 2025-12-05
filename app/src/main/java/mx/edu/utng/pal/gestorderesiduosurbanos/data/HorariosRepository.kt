package mx.edu.utng.pal.gestorderesiduosurbanos.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HorariosRepository {

    private val ref = FirebaseFirestore.getInstance().collection("horarios")

    /** Obtener solo horarios activos */
    suspend fun obtenerTodos(): List<Horarios> =
        ref.whereEqualTo("activo", 1)
            .get()
            .await()
            .toObjects(Horarios::class.java)

    /** Insertar nuevo horario */
    suspend fun insertar(h: Horarios) {
        val doc = ref.document()
        val idLong = doc.id.hashCode().toLong()  // usamos el hash del ID

        doc.set(h.copy(id = idLong)).await()
    }

    /** Actualizar horario */
    suspend fun actualizar(docId: String, h: Horarios) {
        ref.document(docId).set(h).await()
    }

    /** Eliminar (desactivar) */
    suspend fun eliminar(docId: String) {
        ref.document(docId).update("activo", 0).await()
    }
}


