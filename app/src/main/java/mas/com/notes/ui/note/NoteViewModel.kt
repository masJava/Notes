package mas.com.notes.ui.note

import androidx.lifecycle.ViewModel
import mas.com.notes.data.Repository
import mas.com.notes.data.model.Note


class NoteViewModel : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            Repository.saveNote(it)
        }
    }

}
