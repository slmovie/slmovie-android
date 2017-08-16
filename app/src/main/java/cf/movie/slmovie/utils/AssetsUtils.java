package cf.movie.slmovie.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 包俊 on 2017/8/15.
 */

public class AssetsUtils {

    /**
     * assets目录下文件复制到sd卡
     *
     * @param context
     * @param assets  assets文件名
     * @param path    复制地址
     */
    public static void copy(Context context, String assets, String path) {
        try {
            File outFile = new File(path + File.separator + assets);
            InputStream is = context.getAssets().open(assets);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
