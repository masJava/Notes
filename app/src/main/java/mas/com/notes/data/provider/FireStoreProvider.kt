package mas.com.notes.data.provider

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import mas.com.notes.data.entity.Note
import mas.com.notes.data.entity.User
import mas.com.notes.data.errors.NoAuthException
import mas.com.notes.data.model.NoteResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class FireStoreProvider(val store: FirebaseFirestore, val auth: FirebaseAuth) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USER_COLLECTION = "users"

    }

    private val currentUser
        get() = auth.currentUser

    val userNotesCollection
        get() = currentUser?.let { store.collection(USER_COLLECTION).document(it.uid).collection(NOTES_COLLECTION) } ?: throw NoAuthException()

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        currentUser?.let { User(it.displayName ?: "", it.email ?: "") }.let { continuation.resume(it) }
    }

    override fun subscribeToAllNotes(): ReceiveChannel<NoteResult> = Channel<NoteResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null

        try {
            registration = userNotesCollection.addSnapshotListener { snapshot, e ->
                val value = e?.let {
                    NoteResult.Error(it)
                } ?: snapshot?.let {
                    val notes = snapshot.documents.map { doc ->
                        doc.toObject(Note::class.java)
                    }
                    NoteResult.Success(notes)
                }

                value?.let { offer(it) }
            }
        } catch (e: Throwable) {
            offer(NoteResult.Error(e))
        }

        invokeOnClose {
            registration?.remove()
        }
    }

    override suspend fun getNoteById(id: String): Note = suspendCoroutine { continuation ->
        try {
            userNotesCollection.document(id).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.toObject(Note::class.java)!!)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun saveNote(note: Note): Note = suspendCoroutine { continuation ->
        try {
            userNotesCollection.document(note.id).set(note)
                .addOnSuccessListener {
                    continuation.resume(note)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun deleteNote(noteId: String): Unit = suspendCoroutine { continuation ->
        try {
            userNotesCollection.document(noteId).delete()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }


}
