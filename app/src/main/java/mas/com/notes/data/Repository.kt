package mas.com.notes.data

import mas.com.notes.data.model.Note
import kotlin.random.Random

object Repository {
    private var num = 1
        get() = field++

    val notes: List<Note> = listOf(
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
        Note("Заметка №$num", getNote(), getColor()),
    )

}

private fun getColor(): Int {
    val col =
        arrayOf(0xfff06292, 0xff9575cd, 0xff64b5f6, 0xff4db6ac, 0xffb2ff59, 0xffffeb3b, 0xffff6e40)
    return col[Random.nextInt(col.size)].toInt()
}

private fun getNote(): String {
    val rnd = Random
    val shoppingList :MutableList<String> = mutableListOf(
        "хлеб", "молоко", "ряженка", "сметана", "сыр", "масло",
        "кукуруза", "шоколадка", "мука", "мыло", "порошок"
    )
    var str ="Купить: "
    val maxSize = rnd.nextInt(shoppingList.size)+1
    for (i in 1..maxSize){
        val shoppingNum = rnd.nextInt(shoppingList.size)
        str += "${shoppingList[shoppingNum]}, "
        shoppingList.remove(shoppingList[shoppingNum])
    }
    str = str.substring(0, str.length - 2)+".";

    return str
}
