package mas.com.notes.ui.note

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_note.*
import mas.com.notes.R
import mas.com.notes.data.entity.Note
import mas.com.notes.data.entity.Note.Color
import mas.com.notes.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class NoteActivity : BaseActivity<NoteViewState.Data, NoteViewState>() {

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

    private var colorNote = getRandColor(colorList)
    override val viewModel: NoteViewModel by viewModel()
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
            toolbar.setBackgroundColor(getColor(convertColor(colorNote)))
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

    override fun renderData(data: NoteViewState.Data) {
        if(data.isDeleted) finish()
        this.note = data.note
        initView()
    }

    private fun initView() {
        titleEt.removeTextChangedListener(textChangeListener)
        bodyEt.removeTextChangedListener(textChangeListener)

        note?.let { note ->
            titleEt.setText(note.title)
            bodyEt.setText(note.textNote)
            titleEt.setSelection(titleEt.text?.length ?: 0)
            bodyEt.setSelection(bodyEt.text?.length ?: 0)
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(note.lastChanged)
            colorNote = note.color
            toolbar.setBackgroundColor(getColor(convertColor(colorNote)))
        }?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
        }

        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)

        colorPicker.onColorClickListener = {
            colorNote = it
            toolbar.setBackgroundColor(convertColor(colorNote))
            saveNote()
        }

    }

    override fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    fun togglePallete() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
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
            color = colorNote
        ) ?: Note(UUID.randomUUID().toString(), titleEt.text.toString(), bodyEt.text.toString())

        note?.let {
            viewModel.save(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.deleteNote -> deleteNote().let { true }
        R.id.colorChange -> togglePallete().let { true }
        else -> super.onOptionsItemSelected(item)
    }

//    fun colorChange(item: MenuItem) {
//        colorNote = getNextColor(colorList)
//        toolbar.setBackgroundColor(getColor(convertColor(colorNote)))
//        saveNote()
//    }

    private fun getNextColor(colorList: List<Color>): Color {
        for (i in colorList.indices) {
            if (colorNote == colorList[i]) {
                if (i != colorList.lastIndex)
                    return colorList[i + 1]
            }
        }
        return colorList[0]
    }

    private fun deleteNote() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.note_del_message))
            .setPositiveButton(R.string.note_del_ok) { dialog, which ->
                viewModel.deleteNote()
                onBackPressed() }
            .setNegativeButton(R.string.note_del_cancel) { dialog, which -> dialog.dismiss() }
            .show()
    }
}