package mas.com.notes.ui

import android.app.Application
import mas.com.notes.data.di.appModule
import mas.com.notes.data.di.mainModule
import mas.com.notes.data.di.noteModule
import mas.com.notes.data.di.splashModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }

}