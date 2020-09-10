package mas.com.notes.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_note.*
import mas.com.notes.R
import mas.com.notes.data.model.Color
import mas.com.notes.data.model.Note
import mas.com.notes.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, noteId: String? = null) = Intent(
            context,
            NoteActivity::class.java
        ).run {
            noteId?.let {
                putExtra(EXTRA_NOTE, noteId)
            }
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    private val colorList =
        listOf(Color.VIOLET, Color.YELLOW, Color.RED, Color.PINK, Color.GREEN, Color.BLUE)

    private var newColor = getRandColor(colorList)
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
    override val layoutRes = R.layout.activity_note


    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let { id ->
            viewModel.loadNote(id)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                toolbar.setBackgroundColor(getColor(convertColor(newColor)))
            }
        }
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.note_menu, menu)
        return true
    }

    private fun getRandColor(colorList: List<Color>): Color {
        return colorList[Random.nextInt(colorList.size)]
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let { note ->
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note.lastChanged)
        } ?: let {
            getString(R.string.new_note_title)
        }
        initView()
    }

    private fun initView() {
        titleEt.removeTextChangedListener(textChangeListener)
        bodyEt.removeTextChangedListener(textChangeListener)

        note?.let { note ->
            titleEt.setText(note.title)
            bodyEt.setText(note.textNote)

            val color = convertColor(note.color)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                toolbar.setBackgroundColor(getColor(color))
            }
        }

        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)

    }

    private fun convertColor(col: Color): Int {
        return when (col) {
            Color.WHITE -> R.color.color_white
            Color.VIOLET -> R.color.color_violet
            Color.YELLOW -> R.color.color_yello
            Color.RED -> R.color.color_red
            Color.PINK -> R.color.color_pink
            Color.GREEN -> R.color.color_green
            Color.BLUE -> R.color.color_blue
        }
    }

    private fun saveNote() {
        if (titleEt.text == null || titleEt.text!!.length < 3) return

        note = note?.copy(
            title = titleEt.text.toString(),
            textNote = bodyEt.text.toString(),
            lastChanged = Date(),
            color = newColor
        ) ?: Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString())

        note?.let {
            viewModel.save(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun colorChange(item: MenuItem) {
        newColor = getNextColor(colorList)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            toolbar.setBackgroundColor(getColor(convertColor(newColor)))
        }
        saveNote()
    }

    private fun getNextColor(colorList: List<Color>): Color {
        for (i in colorList.indices) {
            if (newColor == colorList[i]) {
                if (i != colorList.lastIndex)
                    return colorList[i + 1]
            }
        }
        return colorList[0]
    }
}