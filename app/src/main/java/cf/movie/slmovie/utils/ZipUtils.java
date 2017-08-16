package cf.movie.slmovie.utils;

import android.widget.Toast;

import net.lingala.zip4j.core.ZipFile;

import java.io.File;

/**
 * Created by 包俊 on 2017/8/15.
 */

public class ZipUtils {

    public static boolean upZip(String from, String to) {
        boolean over = true;
        try {
            File zip = new File(from);
            ZipFile zipFile = new ZipFile(zip);
            if (zipFile.isValidZipFile())
                zipFile.extractAll(to);
        } catch (Exception e) {
            over = false;
            e.printStackTrace();
        }
        return over;
    }

}
