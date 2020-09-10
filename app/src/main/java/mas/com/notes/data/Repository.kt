package mas.com.notes.data

import mas.com.notes.data.model.Note
import mas.com.notes.data.provider.FireStoreProvider
import mas.com.notes.data.provider.RemoteDataProvider

object Repository {
    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
}