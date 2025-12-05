package mx.edu.utng.pal.gestorderesiduosurbanos.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReporteRepository {

    private val ref = FirebaseFirestore.getInstance().collection("reportes")

    suspend fun obtenerTodos() =
        ref.get().await().toObjects(Reporte::class.java)

    suspend fun insertar(reporte: Reporte) {
        val doc = ref.document()
        doc.set(reporte.copy(id = doc.id)).await()
    }

    suspend fun actualizarEstado(id: String, estado: String) {
        ref.document(id).update("estado", estado).await()
    }
}
