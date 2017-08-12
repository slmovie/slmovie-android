package cf.movie.slmovie.utils.rnUtils.snackbar;

import android.graphics.Color;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by 包俊 on 2017/8/7.
 */

public class SnackbarModule extends ReactContextBaseJavaModule {
    private CoordinatorLayout coordinator;
    private static final String LENGTH_SHORT = "SHORT";
    private static final String LENGTH_LONG = "LONG";
    private static final String LENGTH_INDEFINITE = "INDEFINITE";

    public SnackbarModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    public SnackbarModule(ReactApplicationContext reactContext, CoordinatorLayout coordinator) {
        super(reactContext);
        this.coordinator = coordinator;
    }


    @Override
    public String getName() {
        return "SnackbarNative";
    }

    @ReactMethod
    public void showSnackbar(String msg, int duration, final Callback callback) {
        Snackbar.make(coordinator, msg, duration)
                .setAction("重新加载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.invoke();
                    }
                })
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show();
    }

    @ReactMethod
    public void showSnackbar(String msg, int duration) {
        Snackbar.make(coordinator, msg, duration)
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show();
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(LENGTH_SHORT, BaseTransientBottomBar.LENGTH_SHORT);
        constants.put(LENGTH_LONG, BaseTransientBottomBar.LENGTH_LONG);
        constants.put(LENGTH_INDEFINITE, BaseTransientBottomBar.LENGTH_INDEFINITE);
        return constants;
    }
}
