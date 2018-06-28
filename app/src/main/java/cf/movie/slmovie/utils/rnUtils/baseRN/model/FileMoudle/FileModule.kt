package cf.movie.slmovie.utils.rnUtils.baseRN.model.FileMoudle

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.io.File

/**
 * Created by 包俊 on 2018/6/27.
 */
class FileModule(reactContext: ReactApplicationContext?) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "FileNative"
    }

    @ReactMethod
    fun deleteFile(path: String) {
        var file = File(path)
        file.delete()
    }
}