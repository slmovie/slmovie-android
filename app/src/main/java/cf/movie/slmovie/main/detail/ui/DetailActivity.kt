package cf.movie.slmovie.main.detail.ui

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast

import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactInstanceManagerBuilder
import com.facebook.react.ReactRootView
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.shell.MainReactPackage

import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import cf.movie.slmovie.main.detail.presenter.DetailPresenter
import cf.movie.slmovie.main.detail.rn.DetailReactPackage
import cf.movie.slmovie.utils.rnUtils.checkVersion.CheckVersionReactPackage
import cf.movie.slmovie.utils.rnUtils.toastDialog.ToastDialogReactPackage
import cf.movie.slmovie.utils.rnUtils.download.DownloadReactPackage
import cf.movie.slmovie.utils.rnUtils.snackbar.SnackbarReactPackage

/**
 * Created by 包俊 on 2017/8/5.
 */

class DetailActivity : BaseActivity(), DefaultHardwareBackBtnHandler {
    private var mReactRootView: ReactRootView? = null
    private var mReactInstanceManager: ReactInstanceManager? = null
    private var toolbar: Toolbar? = null
    private var container: CoordinatorLayout? = null
    private var swipeLayout: SwipeRefreshLayout? = null
    private var address: String? = null
    private var presenter: DetailPresenter? = null

    override val contentLayout: Int
        get() = R.layout.activity_detail

    private val iDetailView = object : IDetailView {
        /**
         * swipeLayout刷新状态
         *
         * @param fresh
         */
        override fun refresh(fresh: Boolean) {
            runOnUiThread { swipeLayout!!.isRefreshing = fresh }
        }

        override fun setReactPackage(builder: ReactInstanceManagerBuilder) {
            builder.addPackage(MainReactPackage())
                    .addPackage(DetailReactPackage(address!!, this))
                    .addPackage(SnackbarReactPackage(container!!))
                    .addPackage(DownloadReactPackage())
                    .addPackage(ToastDialogReactPackage(this@DetailActivity))
                    .addPackage(CheckVersionReactPackage(this))
        }

        override fun reCreateReactNative() {
            runOnUiThread { mReactInstanceManager!!.recreateReactContextInBackground() }
        }
    }

    private val up: Float = 0.toFloat()
    private val down: Float = 0.toFloat()

    override fun initGui() {
        mReactRootView = findViewById(R.id.react) as ReactRootView
        toolbar = findViewById(R.id.toolbar) as Toolbar
        container = findViewById(R.id.container) as CoordinatorLayout
        swipeLayout = findViewById(R.id.swipeLayout) as SwipeRefreshLayout
    }

    override fun initData() {
        val intent = intent
        address = intent.getStringExtra("address")
        val name = intent.getStringExtra("name")
        setSupportActionBar(toolbar)
        supportActionBar!!.title = name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        swipeLayout!!.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED)
        swipeLayout!!.setProgressBackgroundColorSchemeColor(Color.WHITE)
        swipeLayout!!.setSize(SwipeRefreshLayout.DEFAULT)
        swipeLayout!!.isEnabled = false
        swipeLayout!!.isRefreshing = true
        presenter = DetailPresenter(this, iDetailView)
        if (presenter!!.checkVersion()) {
            mReactInstanceManager = presenter!!.initReact().build()
            mReactRootView!!.startReactApplication(mReactInstanceManager, "DetailActivity", null)
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_LONG).show()
        }
    }

    override fun initAction() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()

        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onHostPause(this)
        }
    }

    override fun onResume() {
        super.onResume()

        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onHostResume(this, this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onHostDestroy()
        }
    }

    override fun onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager!!.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager!!.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

}
