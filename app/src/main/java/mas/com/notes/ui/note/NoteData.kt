package mas.com.notes.ui.note

import mas.com.notes.data.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)