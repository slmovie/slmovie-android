package cf.movie.slmovie.utils.rnUtils.baseRN.view

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Toast
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import cf.movie.slmovie.utils.rnUtils.baseRN.model.Dialog.DialogReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.model.LoadModule.LoadModule
import cf.movie.slmovie.utils.rnUtils.baseRN.model.LoadModule.LoadReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.model.checkVersion.CheckVersionReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.model.download.DownloadReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.model.snackbar.SnackbarReactPackage
import cf.movie.slmovie.utils.rnUtils.baseRN.presenter.BaseRNPresenter
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactInstanceManagerBuilder
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.react.shell.MainReactPackage
import kotlinx.android.synthetic.main.activity_rn.*
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by 包俊 on 2018/6/6.
 */
abstract class BaseRNActivity : BaseActivity(), DefaultHardwareBackBtnHandler, EasyPermissions.PermissionCallbacks {

    private var mReactInstanceManager: ReactInstanceManager? = null
    private var presenter: BaseRNPresenter? = null
    var isSwipe = true
    var title = ""

    override val contentLayout: Int
        get() = R.layout.activity_rn

    override fun initGui() {
        setSupportActionBar(toolbar)
        if (TextUtils.isEmpty(title)) {
            val intent = intent
            val name = intent.getStringExtra("name")
            supportActionBar!!.title = name
        } else {
            supportActionBar!!.title = title
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (isSwipe) {
            swipeLayout!!.setColorSchemeColors(Color.BLUE,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.RED)
            swipeLayout!!.setProgressBackgroundColorSchemeColor(Color.WHITE)
            swipeLayout!!.setSize(SwipeRefreshLayout.DEFAULT)
            swipeLayout!!.isEnabled = false
            swipeLayout!!.isRefreshing = true
        }
    }

    private val IBaseRNView = object : IBaseRNView {
        /**
         * swipeLayout刷新状态
         *
         * @param fresh
         */
        override fun refresh(fresh: Boolean) {
            if (isSwipe)
                runOnUiThread { swipeRefresh(fresh) }
        }

        override fun setReactPackage(builder: ReactInstanceManagerBuilder) {
            builder.addPackage(MainReactPackage())
                    .addPackage(SnackbarReactPackage(container!!))
                    .addPackage(DownloadReactPackage())
                    .addPackage(CheckVersionReactPackage(this))
                    .addPackage(DialogReactPackage(this@BaseRNActivity))
                    .addPackage(loadReactPackage)
            setMyReactPackage(builder)
        }

        override fun reCreateReactNative() {
            runOnUiThread { mReactInstanceManager!!.recreateReactContextInBackground() }
        }
    }


    val listener = object : LoadModule.LoadListener {
        override fun loadFinish() {
            loadFinished()
        }
    }

    //rn加载模块
    private val loadReactPackage = LoadReactPackage(listener)

    //主动调用js
    fun emit(name: String, param: WritableMap) {
        loadReactPackage.emit(name, param)
    }

    override fun initData() {
        presenter = BaseRNPresenter(this, IBaseRNView)
        if (presenter!!.checkVersion()) {
            mReactInstanceManager = presenter!!.initReact().build()
            react!!.startReactApplication(mReactInstanceManager, moduleName, null)
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_LONG).show()
        }
    }

    override fun initAction() {
    }

    fun swipeRefresh(fresh: Boolean) {
        runOnUiThread { swipeLayout!!.isRefreshing = fresh }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "用户授权成功！", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "用户授权失败！", Toast.LENGTH_SHORT).show()
    }

    //设置各自需要的方法
    protected abstract fun setMyReactPackage(builder: ReactInstanceManagerBuilder)

    //设置RN加载的名字
    protected abstract val moduleName: String

    //初始化完成回调
    protected abstract fun loadFinished()

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
            mReactInstanceManager!!.onHostDestroy(this)
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