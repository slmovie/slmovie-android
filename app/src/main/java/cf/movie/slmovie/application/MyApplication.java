package cf.movie.slmovie.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 包俊 on 2017/7/19.
 */

public class MyApplication extends Application {

    private MyApplication mApp;

    @Override
    public void onCreate() {
        mApp = this;
        super.onCreate();
        Fresco.initialize(this);
    }
}
