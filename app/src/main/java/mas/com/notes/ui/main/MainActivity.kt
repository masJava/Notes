package mas.com.notes.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import mas.com.notes.R
import mas.com.notes.data.entity.Note
import mas.com.notes.ui.base.BaseActivity
import mas.com.notes.ui.base.BaseViewModel
import mas.com.notes.ui.note.NoteActivity
import mas.com.notes.ui.splash.SplashActivity


class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val viewModel: BaseViewModel<List<Note>?, MainViewState> by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main

    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }

    fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?: LogoutDialog.createInstance {
            onLogout()
        }.show(supportFragmentManager, LogoutDialog.TAG)
    }

    fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

}



//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import androidx.lifecycle.ViewModelProviders
//import com.firebase.ui.auth.AuthUI
//import kotlinx.android.synthetic.main.activity_main.*
//import mas.com.notes.R
//import mas.com.notes.data.entity.Note
//import mas.com.notes.ui.base.BaseActivity
//import mas.com.notes.ui.base.BaseViewModel
//import mas.com.notes.ui.note.NoteActivity
//import mas.com.notes.ui.splash.SplashActivity

//class MainActivity : BaseActivity<List<Note>?, MainViewState>() {
//
//    companion object {
//        fun start(context: Context) {
//            val intent = Intent(context, MainActivity::class.java)
//            context.startActivity(intent)
//        }
//    }
//
//    override val viewModel: BaseViewModel<List<Note>?, MainViewState> by lazy {
//        ViewModelProviders.of(this).get(MainViewModel::class.java)
//    }
//
//    override val layoutRes = R.layout.activity_main
//
//    lateinit var adapter: MainAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setSupportActionBar(toolbar)
//        adapter = MainAdapter { note ->
//            NoteActivity.start(this, note.id)
//        }
//        mainRecycler.adapter = adapter
//        fab.setOnClickListener {
//            NoteActivity.start(this)
//        }
//    }
//
//    override fun renderData(data: List<Note>?) {
//        data?.let {
//            adapter.notes = it
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean = MenuInflater(this).inflate(R.menu.main, menu).let { true }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
//        R.id.logout -> showLogoutDialog().let { true }
//        else -> false
//    }
//
//    fun showLogoutDialog() {
//        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?: LogoutDialog.createInstance {
//            onLogout()
//        }.show(supportFragmentManager, LogoutDialog.TAG)
//    }
//
//    fun onLogout() {
//        AuthUI.getInstance()
//            .signOut(this)
//            .addOnCompleteListener {
//                startActivity(Intent(this, SplashActivity::class.java))
//                finish()
//            }
//    }
//
//}
