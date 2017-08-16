package cf.movie.slmovie.utils.rnUtils.checkVersion;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cf.movie.slmovie.main.detail.ui.IDetailView;
import cf.movie.slmovie.utils.rnUtils.download.DownloadModule;

/**
 * Created by 包俊 on 2017/8/15.
 */

public class CheckVersionReactPackage implements ReactPackage {

    private IDetailView iDetailView;

    public CheckVersionReactPackage(IDetailView iDetailView) {
        this.iDetailView = iDetailView;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new CheckVersionModule(reactContext,iDetailView));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
