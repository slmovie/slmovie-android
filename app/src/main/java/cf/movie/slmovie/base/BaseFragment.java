package cf.movie.slmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 包俊 on 2017/7/21.
 */

public abstract class BaseFragment extends Fragment {

    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentLayout() != 0) {
            view = inflater.inflate(getContentLayout(), container, false);
        }
        initGui();
        initData();
        initAction();
        return view;
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int getContentLayout();

    /**
     * 初始化控件
     */
    protected abstract void initGui();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 设置监听
     */
    protected abstract void initAction();
}
