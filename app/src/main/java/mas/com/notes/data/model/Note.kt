package mas.com.notes.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: String,
    val title: String,
    val textNote: String,
    val color: Color = Color.WHITE,
    val lastChanged: Date = Date()
) : Parcelable

enum class Color {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}