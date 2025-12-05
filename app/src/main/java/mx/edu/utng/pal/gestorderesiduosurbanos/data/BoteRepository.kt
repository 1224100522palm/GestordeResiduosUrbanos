package mx.edu.utng.pal.gestorderesiduosurbanos.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BoteRepository {

    private val ref = FirebaseFirestore.getInstance().collection("botes")

    /** 🔥 Escucha en tiempo real */
    fun obtenerBotesFlow(): Flow<List<Bote>> = callbackFlow {
        val listener = ref.addSnapshotListener { snap, _ ->
            if (snap != null) trySend(snap.toObjects(Bote::class.java))
        }
        awaitClose { listener.remove() }
    }

    /** 🔥 Insertar bote */
    suspend fun insertar(bote: Bote) {
        val doc = ref.document()

        doc.set(
            bote.copy(id = doc.id)   // siempre se guarda como String
        ).await()
    }

    /** 🔥 Actualizar bote */
    suspend fun actualizar(bote: Bote) {
        val docId = bote.id.toString()   // convierte Any? → String

        ref.document(docId).set(bote.copy(id = docId)).await()
    }

    /** 🔥 Eliminar bote */
    suspend fun eliminar(id: Any?) {
        val docId = id.toString()

        ref.document(docId).delete().await()
    }

    /** 🔥 Obtener todos los botes */
    suspend fun obtenerTodos(): List<Bote> =
        ref.get().await().toObjects(Bote::class.java)
}
