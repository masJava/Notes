package mas.com.notes.ui.note

import mas.com.notes.data.entity.Note
import mas.com.notes.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error)