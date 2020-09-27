package mas.com.notes.ui.splash

import kotlinx.coroutines.launch
import mas.com.notes.data.Repository
import mas.com.notes.data.errors.NoAuthException
import mas.com.notes.ui.base.BaseViewModel

class SplashViewModel(val notesRepository: Repository) : BaseViewModel<Boolean?>() {
    fun requestUser() = launch {
        notesRepository.getCurrentUser()?.let {
            setData(true)
        } ?: let {
            setError(NoAuthException())
        }
    }
}
