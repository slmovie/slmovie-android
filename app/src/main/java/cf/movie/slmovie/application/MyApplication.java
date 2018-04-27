package cf.movie.slmovie.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 包俊 on 2017/7/19.
 */

public class MyApplication extends Application{

    public static MyApplication mApp;

    @Override
    public void onCreate() {
        mApp = this;
        super.onCreate();
        Fresco.initialize(this);
    }
}
