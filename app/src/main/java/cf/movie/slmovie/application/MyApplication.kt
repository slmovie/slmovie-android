package cf.movie.slmovie.application

import android.app.Application

import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by 包俊 on 2017/7/19.
 */

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}
