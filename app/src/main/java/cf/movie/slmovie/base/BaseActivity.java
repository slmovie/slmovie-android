package cf.movie.slmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 包俊 on 2017/7/19.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
        }
        initGui();
        initAction();
        initData();
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
