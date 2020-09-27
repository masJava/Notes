package mas.com.notes.data.provider

import kotlinx.coroutines.channels.ReceiveChannel
import mas.com.notes.data.entity.Note
import mas.com.notes.data.entity.User
import mas.com.notes.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}
