package mas.com.notes.data

import mas.com.notes.data.entity.Note
import mas.com.notes.data.provider.RemoteDataProvider

class Repository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    suspend fun saveNote(note: Note) = remoteProvider.saveNote(note)
    suspend fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    suspend fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
}