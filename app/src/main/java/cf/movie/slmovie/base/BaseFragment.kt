package cf.movie.slmovie.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by 包俊 on 2017/7/21.
 */

abstract class BaseFragment : Fragment() {

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract val contentLayout: Int

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var MyView: View? = null
        if (contentLayout != 0) {
            MyView = inflater!!.inflate(contentLayout, container, false)
        }
        return MyView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initGui()
        initData()
        initAction()
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
