package mas.com.notes.ui.note

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import mas.com.notes.data.Repository
import mas.com.notes.data.entity.Note
import mas.com.notes.ui.base.BaseViewModel

class NoteViewModel(val notesRepository: Repository) : BaseViewModel<NoteData>() {

    private val pendingNote: Note?
        get() = getViewState().poll()?.note


    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    fun loadNote(noteId: String) = launch {
        try {
            notesRepository.getNoteById(noteId).let {
                setData(NoteData(note = it))
            }
        } catch (e: Throwable) {
            setError(e)
        }
    }

    fun deleteNote() = launch {
        try {
            pendingNote?.let { notesRepository.deleteNote(it.id) }
            setData(NoteData(isDeleted = true))
        } catch (e: Throwable) {
            setError(e)
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let {
                notesRepository.saveNote(it)
            }
        }
    }

}
