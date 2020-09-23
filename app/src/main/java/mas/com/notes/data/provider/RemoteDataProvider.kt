package mas.com.notes.data.provider

import androidx.lifecycle.LiveData
import mas.com.notes.data.entity.Note
import mas.com.notes.data.entity.User
import mas.com.notes.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun deleteNote(noteId: String): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
}
