package mas.com.notes.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import mas.com.notes.R
import mas.com.notes.data.model.Note
import mas.com.notes.ui.base.BaseActivity
import mas.com.notes.ui.base.BaseViewModel
import mas.com.notes.ui.note.NoteActivity

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    override val viewModel: BaseViewModel<List<Note>?, MainViewState> by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main

    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        //mainRecycler.layoutManager = GridLayoutManager(this, 2)
        adapter = MainAdapter { note ->
            NoteActivity.start(this, note.id)
        }
        mainRecycler.adapter = adapter
        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

}
