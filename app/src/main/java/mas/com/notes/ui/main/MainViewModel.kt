package mas.com.notes.ui.main

import androidx.lifecycle.Observer
import mas.com.notes.data.Repository
import mas.com.notes.data.entity.Note
import mas.com.notes.data.model.NoteResult
import mas.com.notes.ui.base.BaseViewModel

class MainViewModel(repository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            if (t == null) return

            when (t) {
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    public override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        //super.onCleared()
    }
}
