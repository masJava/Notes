package mas.com.notes.ui.splash

import mas.com.notes.data.Repository
import mas.com.notes.data.errors.NoAuthException
import mas.com.notes.ui.base.BaseViewModel

class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        Repository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}
