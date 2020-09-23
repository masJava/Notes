package mas.com.notes.data

import android.content.Context
import androidx.core.content.ContextCompat
import mas.com.notes.R
import mas.com.notes.data.entity.Note


fun Note.Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, getColorRes()
    )

fun Note.Color.getColorRes(): Int = when (this) {
    Note.Color.WHITE -> R.color.color_white
    Note.Color.VIOLET -> R.color.color_violet
    Note.Color.YELLOW -> R.color.color_yello
    Note.Color.RED -> R.color.color_red
    Note.Color.GREEN -> R.color.color_green
    Note.Color.BLUE -> R.color.color_blue
    else -> R.color.color_white
}