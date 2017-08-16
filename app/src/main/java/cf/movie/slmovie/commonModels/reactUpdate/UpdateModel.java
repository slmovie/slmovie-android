package cf.movie.slmovie.commonModels.reactUpdate;

import android.content.Context;

import java.io.File;

import cf.movie.slmovie.utils.AssetsUtils;
import cf.movie.slmovie.utils.ZipUtils;

/**
 * Created by 包俊 on 2017/8/15.
 */

public class UpdateModel {

    private Context context;

    public UpdateModel(Context context) {
        this.context = context;
    }

    /**
     * 检查本地是否已经解压了bundle
     *
     * @param zipName 压缩包名称
     * @param jsPath  rn根目录地址
     * @param bundle  bundle名
     * @return
     */
    public boolean checkZip(String zipName, String jsPath, String bundle) {
        File file = new File(context.getExternalCacheDir(), jsPath + File.separator + bundle);
        if (!file.exists()) {
            File fileRn = new File(context.getExternalCacheDir() + File.separator + "rn");
            if (!fileRn.exists())
                fileRn.mkdir();
            AssetsUtils.copy(context, zipName, context.getExternalCacheDir().getAbsolutePath());
            boolean over = ZipUtils.upZip(context.getExternalCacheDir().getAbsolutePath() + File.separator + zipName, context.getExternalCacheDir().getAbsolutePath() + File.separator + jsPath);
            if (over) {
                File fileD = new File(context.getExternalCacheDir().getAbsolutePath(), zipName);
                fileD.delete();
            }
            return over;
        } else {
            return true;
        }
    }

}
