package cf.movie.slmovie.utils.rnUtils.download;

import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by 包俊 on 2017/8/9.
 */

public class DownloadModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext context;

    public DownloadModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "DownloadNative";
    }

    @ReactMethod
    public void pushDownload(String url, Promise promise) {
        boolean isFind = true;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addCategory("android.intent.category.DEFAULT");
            context.startActivity(intent);
        } catch (Exception e) {
            isFind = false;
            e.printStackTrace();
        }
        WritableMap maps = Arguments.createMap();
        maps.putBoolean("result", isFind);
        promise.resolve(maps);
    }
}
