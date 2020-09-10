package mas.com.notes.ui.main

import androidx.lifecycle.Observer
import mas.com.notes.data.Repository
import mas.com.notes.data.model.Note
import mas.com.notes.data.model.NoteResult
import mas.com.notes.ui.base.BaseViewModel

class MainViewModel(val repository: Repository = Repository) :
    BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<NoteResult> {
        //Стандартный обсервер LiveData
        override fun onChanged(t: NoteResult?) {
            if (t == null) return

            when (t) {
                is NoteResult.Success<*> -> {
// Может понадобиться вручную импортировать класс data.model.NoteResult.Success
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
// Может понадобиться вручную импортировать класс data.model.NoteResult.Error
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

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}
