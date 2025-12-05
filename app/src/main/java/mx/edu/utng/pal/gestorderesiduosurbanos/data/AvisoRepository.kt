package mx.edu.utng.pal.gestorderesiduosurbanos.data

import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AvisoRepository {

    private val db = FirebaseFirestore.getInstance()
    private val ref = db.collection("avisos")

    fun getAvisosFlow(): Flow<List<Aviso>> = callbackFlow {
        val listener = ref.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                trySend(snapshot.toObjects(Aviso::class.java))
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun insert(aviso: Aviso) {
        val doc = ref.document()
        val nuevo = aviso.copy(id = doc.id)
        doc.set(nuevo).await()
    }

    suspend fun update(aviso: Aviso) {
        ref.document(aviso.id).set(aviso).await()
    }

    suspend fun delete(aviso: Aviso) {
        ref.document(aviso.id).delete().await()
    }

    suspend fun getById(id: String): Aviso? {
        return ref.document(id).get().await().toObject(Aviso::class.java)
    }

    suspend fun getAvisosList(): List<Aviso> {
        return ref.get().await().toObjects(Aviso::class.java)
    }
}
