package mas.com.notes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import mas.com.notes.R
import mas.com.notes.ui.note.NoteActivity

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel =
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        adapter = MainAdapter {
            NoteActivity.start(this, it)
        }

        mainRecycler.adapter = adapter

        viewModel.viewState().observe(this, Observer { state ->
            state?.let { state ->
                adapter.notes = state.notes
            }
        })

        fab.setOnClickListener {
            NoteActivity.start(this)
        }

    }

}
