package cf.movie.slmovie.main.detail.presenter;

import android.app.Activity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.common.LifecycleState;

import cf.movie.slmovie.BuildConfig;
import cf.movie.slmovie.commonModels.reactUpdate.UpdateModel;
import cf.movie.slmovie.main.detail.ui.IDetailView;

/**
 * Created by 包俊 on 2017/8/15.
 */

public class DetailPresenter {

    private Activity context;
    private final String jsPath = "rn/detail";
    private final String zip = "rn/detail.zip";
    private final String bundle = "detail.android.bundle";
    private IDetailView impl;
    private UpdateModel updateModel;

    public DetailPresenter(Activity context, IDetailView impl) {
        this.context = context;
        this.impl = impl;
        updateModel = new UpdateModel(context);
    }

    //初始化react native
    public ReactInstanceManagerBuilder initReact() {
        ReactInstanceManagerBuilder builder = ReactInstanceManager.builder();
        builder.setApplication(context.getApplication());
//                .setBundleAssetName("rn/detail/detail.android.bundle")
        builder.setJSBundleFile(context.getExternalCacheDir().getAbsolutePath() + "/rn/detail/detail.android.bundle");
        builder.setJSMainModuleName("slmovie/rn/android/detail/detail.android");
        builder.setUseDeveloperSupport(BuildConfig.DEBUG);
        builder.setInitialLifecycleState(LifecycleState.RESUMED);
        impl.setReactPackage(builder);
        return builder;
    }

    public boolean checkVersion() {
        return updateModel.checkZip(zip, jsPath, bundle);
    }

}
