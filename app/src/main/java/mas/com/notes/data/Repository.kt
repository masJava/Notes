package mas.com.notes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mas.com.notes.data.model.Color
import mas.com.notes.data.model.Note
import java.util.*
import kotlin.random.Random

object Repository {
    private var num = 1
        get() = field++

    private val notes = mutableListOf(
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
        Note(getId(), "Заметка №$num", getNote(), getColor()),
    )

    private fun getId() = UUID.randomUUID().toString()

    private val notesLiveData = MutableLiveData<List<Note>>()

    init {
        notesLiveData.value = notes
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLiveData
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in notes.indices) {
            if (notes[i].id == note.id) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}

private fun getColor(): Color {
//    val col =
//        arrayOf(0xfff06292, 0xff9575cd, 0xff64b5f6, 0xff4db6ac, 0xffb2ff59, 0xffffeb3b, 0xffff6e40)
//    return col[Random.nextInt(col.size)].toInt()
    return when (Random.nextInt(6)) {
        0 -> Color.WHITE
        1 -> Color.YELLOW
        2 -> Color.GREEN
        3 -> Color.BLUE
        4 -> Color.RED
        5 -> Color.VIOLET
        6 -> Color.PINK
        else -> {
            Color.WHITE
        }
    }
}

private fun getNote(): String {
    val rnd = Random
    val shoppingList: MutableList<String> = mutableListOf(
        "хлеб", "молоко", "ряженка", "сметана", "сыр", "масло",
        "кукуруза", "шоколадка", "мука", "мыло", "порошок"
    )
    var str = "Купить: "
    val maxSize = rnd.nextInt(shoppingList.size) + 1
    for (i in 1..maxSize) {
        val shoppingNum = rnd.nextInt(shoppingList.size)
        str += "${shoppingList[shoppingNum]}, "
        shoppingList.remove(shoppingList[shoppingNum])
    }
    str = str.substring(0, str.length - 2) + "."

    return str
}
