package cf.movie.slmovie.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 包俊 on 2017/7/19.
 */

abstract class BaseActivity : AppCompatActivity() {

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract val contentLayout: Int

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (contentLayout != 0) {
            setContentView(contentLayout)
        }
        initGui()
        initAction()
        initData()
    }

    @Subscribe
    fun onEvent(`object`: Any) {

    }

    /**
     * 初始化控件
     */
    protected abstract fun initGui()

    /**
     * 设置数据
     */
    protected abstract fun initData()

    /**
     * 设置监听
     */
    protected abstract fun initAction()
}
