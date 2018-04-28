package cf.movie.slmovie.main.detail.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import cf.movie.slmovie.R;
import cf.movie.slmovie.base.BaseActivity;
import cf.movie.slmovie.main.detail.presenter.DetailPresenter;
import cf.movie.slmovie.main.detail.rn.DetailReactPackage;
import cf.movie.slmovie.utils.rnUtils.checkVersion.CheckVersionReactPackage;
import cf.movie.slmovie.utils.rnUtils.toastDialog.ToastDialogReactPackage;
import cf.movie.slmovie.utils.rnUtils.download.DownloadReactPackage;
import cf.movie.slmovie.utils.rnUtils.snackbar.SnackbarReactPackage;

/**
 * Created by 包俊 on 2017/8/5.
 */

public class DetailActivity extends BaseActivity implements DefaultHardwareBackBtnHandler {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    private Toolbar toolbar;
    private CoordinatorLayout container;
    private SwipeRefreshLayout swipeLayout;
    private String address;
    private DetailPresenter presenter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initGui() {
        mReactRootView = (ReactRootView) findViewById(R.id.react);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (CoordinatorLayout) findViewById(R.id.container);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        String name = intent.getStringExtra("name");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        swipeLayout.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED);
        swipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        swipeLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeLayout.setEnabled(false);
        swipeLayout.setRefreshing(true);
        presenter = new DetailPresenter(this, iDetailView);
        if (presenter.checkVersion()) {
            mReactInstanceManager = presenter.initReact().build();
            mReactRootView.startReactApplication(mReactInstanceManager, "DetailActivity", null);
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initAction() {

    }

    private IDetailView iDetailView = new IDetailView() {
        /**
         * swipeLayout刷新状态
         *
         * @param fresh
         */
        @Override
        public void refresh(final boolean fresh) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swipeLayout.setRefreshing(fresh);
                }
            });
        }

        @Override
        public void setReactPackage(ReactInstanceManagerBuilder builder) {
            builder.addPackage(new MainReactPackage())
                    .addPackage(new DetailReactPackage(address, iDetailView))
                    .addPackage(new SnackbarReactPackage(container))
                    .addPackage(new DownloadReactPackage())
                    .addPackage(new ToastDialogReactPackage(DetailActivity.this))
                    .addPackage(new CheckVersionReactPackage(iDetailView));
        }

        @Override
        public void reCreateReactNative() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mReactInstanceManager.recreateReactContextInBackground();
                }
            });
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private float up, down;

}
