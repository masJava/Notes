package mas.com.notes.ui.splash

import mas.com.notes.ui.base.BaseActivity
import mas.com.notes.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<Boolean?>() {

    override val viewModel: SplashViewModel by viewModel()

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }

}

