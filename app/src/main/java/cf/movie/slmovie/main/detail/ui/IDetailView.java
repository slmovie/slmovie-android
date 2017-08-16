package cf.movie.slmovie.main.detail.ui;

import com.facebook.react.ReactInstanceManagerBuilder;

/**
 * Created by 包俊 on 2017/8/7.
 */

public interface IDetailView {
    /**
     * 控制刷新状态
     *
     * @param fresh
     */
    void refresh(boolean fresh);

    /**
     * 设置客户端方法包
     *
     * @param builder
     */
    void setReactPackage(ReactInstanceManagerBuilder builder);

    /**
     * 更新结束后重新加载react native
     */
    void reCreateReactNative();
}
