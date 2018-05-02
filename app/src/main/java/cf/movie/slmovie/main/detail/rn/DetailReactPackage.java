package cf.movie.slmovie.main.detail.rn;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cf.movie.slmovie.main.detail.ui.IDetailView;

/**
 * Created by BJ on 2016/9/28.
 */
public class DetailReactPackage implements ReactPackage {

    private String address;
    private IDetailView iDetailView;

    public DetailReactPackage(String address, IDetailView iDetailView) {
        this.address = address;
        this.iDetailView = iDetailView;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new GetDetailModule(reactContext, address, iDetailView));
        return modules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
