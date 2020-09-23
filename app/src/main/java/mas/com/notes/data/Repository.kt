package mas.com.notes.data

import mas.com.notes.data.entity.Note
import mas.com.notes.data.provider.RemoteDataProvider

class Repository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}