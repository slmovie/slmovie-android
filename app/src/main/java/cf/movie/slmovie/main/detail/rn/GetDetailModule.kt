package cf.movie.slmovie.main.detail.rn

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.WritableMap

import cf.movie.slmovie.main.detail.model.DetailModel
import cf.movie.slmovie.main.detail.model.IDetailModel
import cf.movie.slmovie.main.detail.ui.IDetailView
import com.facebook.soloader.SoLoader.init

/**
 * Created by 包俊 on 2017/8/6.
 */

class GetDetailModule(reactContext: ReactApplicationContext, private var address: String, private var iDetailView: IDetailView) : ReactContextBaseJavaModule(reactContext), IDetailModel {

    private var model: DetailModel
    private var promise: Promise? = null

    init {
        model = DetailModel(reactContext, this)
    }

    override fun getName(): String {
        return "DetailNative"
    }

    @ReactMethod
    fun getMovie(promise: Promise) {
        this.promise = promise
        model!!.getMovie(address)
    }

    override fun callBack(msg: String) {
        val maps = Arguments.createMap()
        maps.putString("details", msg)
        promise!!.resolve(maps)
        iDetailView.refresh(false)
    }
}
