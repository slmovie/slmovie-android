package cf.movie.slmovie.utils.rnUtils.snackbar;

import android.support.design.widget.CoordinatorLayout;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cf.movie.slmovie.main.detail.rn.GetDetailModule;

/**
 * Created by 包俊 on 2017/8/7.
 */

public class SnackbarReactPackage implements ReactPackage {

    private CoordinatorLayout coordinator;

    public SnackbarReactPackage(CoordinatorLayout coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new SnackbarModule(reactContext, coordinator));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
