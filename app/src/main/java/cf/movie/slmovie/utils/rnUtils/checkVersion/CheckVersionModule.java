package cf.movie.slmovie.utils.rnUtils.checkVersion;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;

import cf.movie.slmovie.main.detail.ui.IDetailView;
import cf.movie.slmovie.server.Constant;
import cf.movie.slmovie.server.HtmlCode;
import cf.movie.slmovie.utils.DownloadUtil;
import cf.movie.slmovie.utils.ZipUtils;

import static cf.movie.slmovie.utils.ZipUtils.upZip;

/**
 * Created by 包俊 on 2017/8/15.
 */

public class CheckVersionModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext context;
    private IDetailView iDetailView;

    public CheckVersionModule(ReactApplicationContext reactContext, IDetailView iDetailView) {
        super(reactContext);
        this.context = reactContext;
        this.iDetailView = iDetailView;
    }

    @Override
    public String getName() {
        return "CheckVersionNative";
    }

    @ReactMethod
    public void Download(final String module) {
        DownloadUtil.instance().download(Constant.WEBROOT + HtmlCode.RNUpdate + module + ".zip", context.getExternalCacheDir().getAbsolutePath() + File.separator + "rn", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                boolean ok = ZipUtils.upZip(context.getExternalCacheDir().getAbsolutePath() + File.separator + "rn" + File.separator + module + ".zip", context.getExternalCacheDir().getAbsolutePath() + File.separator + "rn" + File.separator + module);
                if (ok) {
                    File file = new File(context.getExternalCacheDir().getAbsolutePath() + File.separator + "rn" + File.separator + module + ".zip");
                    file.delete();
                }
                iDetailView.reCreateReactNative();
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {
                Toast.makeText(context, "失败", Toast.LENGTH_LONG).show();
            }
        });
    }

}
