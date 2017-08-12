package cf.movie.slmovie.main.detail.rn;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

import cf.movie.slmovie.main.detail.model.DetailModel;
import cf.movie.slmovie.main.detail.model.IDetailModel;
import cf.movie.slmovie.main.detail.ui.IDetailView;

/**
 * Created by 包俊 on 2017/8/6.
 */

public class GetDetailModule extends ReactContextBaseJavaModule implements IDetailModel {

    private DetailModel model;
    private String address;
    private Promise promise;
    private IDetailView iDetailView;

    public GetDetailModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    public GetDetailModule(ReactApplicationContext reactContext, String address, IDetailView iDetailView) {
        super(reactContext);
        this.address = address;
        this.iDetailView = iDetailView;
        model = new DetailModel(reactContext, this);
    }

    @Override
    public String getName() {
        return "DetailNative";
    }

    @ReactMethod
    public void getMovie(Promise promise) {
        this.promise = promise;
        model.getMovie(address);
        iDetailView.refresh(true);
    }

    @Override
    public void callBack(String msg) {
        WritableMap maps = Arguments.createMap();
        maps.putString("details", msg);
        promise.resolve(maps);
        iDetailView.refresh(false);
    }
}
