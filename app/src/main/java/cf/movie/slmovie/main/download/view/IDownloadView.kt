package cf.movie.slmovie.main.download.view

import com.facebook.react.ReactInstanceManagerBuilder

/**
 * Created by 包俊 on 2017/8/7.
 */

interface IDownloadView {
    /**
     * 控制刷新状态
     *
     * @param fresh
     */
    fun refresh(fresh: Boolean)

    /**
     * 设置客户端方法包
     *
     * @param builder
     */
    fun setReactPackage(builder: ReactInstanceManagerBuilder)

    /**
     * 更新结束后重新加载react native
     */
    fun reCreateReactNative()
}
