package mas.com.notes.ui.main

import mas.com.notes.data.model.Note
import mas.com.notes.ui.base.BaseViewState


class MainViewState(notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)
