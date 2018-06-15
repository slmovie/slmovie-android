package cf.movie.slmovie.main.download.rn.downloadUI

import android.text.TextUtils
import cf.movie.slmovie.db.XLDownloadDao
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean
import cf.movie.slmovie.utils.ReactNativeJson
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.gson.Gson


/**
 * Created by 包俊 on 2018/6/12.
 */
class XLDownloadUIModule(val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var dao = XLDownloadDao(reactContext)

    override fun getName(): String {
        return "XLDownloadUINative"
    }

    @ReactMethod
    fun test(promise: Promise) {
        var bean1 = XLDownloadDBBean()
        bean1.Data = "1"
        bean1.Name = "12321"
        bean1.TotalSize = 123
        bean1.DownloadPath = "2135235"
        bean1.DownloadSize = 3254125
        bean1.DownloadStatus = 1
        bean1.IsTorrent = 1
        var bean2 = XLDownloadDBBean()
        bean2.Data = "1"
        bean2.Name = "12321"
        bean2.TotalSize = 123
        bean2.DownloadPath = "2135235"
        bean2.DownloadSize = 3254125
        bean2.DownloadStatus = 2
        bean2.IsTorrent = 1
        var list = ArrayList<XLDownloadDBBean>()
        list.add(bean1)
        list.add(bean2)
        var gson = Gson()
        val writableMap = ReactNativeJson.convertStringToArray(gson.toJson(list))
        promise.resolve(writableMap)
    }

    @ReactMethod
    fun test1(promise: Promise) {
        var bean1 = XLDownloadDBBean()
        bean1.Data = "1"
        bean1.Name = "12321"
        bean1.TotalSize = 3254125
        bean1.DownloadPath = "2135235"
        bean1.DownloadSize = 123
        bean1.DownloadStatus = 1
        bean1.IsTorrent = 1
        var gson = Gson()
        val writableMap = ReactNativeJson.convertStringToMap(gson.toJson(bean1))
        promise.resolve(writableMap)
    }

    @ReactMethod
    fun findAllInfo(promise: Promise) {
        var list = dao.findAll()
        if (list.size == 0) {
            promise.reject("0", "没有数据")
        } else {
            var gson = Gson()
            val writableMap = ReactNativeJson.convertStringToArray(gson.toJson(list))
            promise.resolve(writableMap)
        }
    }

    @ReactMethod
    fun find(json: String, promise: Promise) {
        var gson = Gson()
        var bean = gson.fromJson<XLDownloadDBBean>(json, XLDownloadDBBean::class.java)
        var result = dao.find(bean)
        if (!TextUtils.isEmpty(result.Name)) {
            val writableMap = ReactNativeJson.convertStringToMap(gson.toJson(result))
            promise.resolve(writableMap)
        } else
            promise.reject("0", "查询失败")
    }

    @ReactMethod
    fun insert(json: String) {
        var gson = Gson()
        var bean = gson.fromJson<XLDownloadDBBean>(json, XLDownloadDBBean::class.java)
        dao.insert(bean)
    }

    @ReactMethod
    fun delete(json: String) {
        var gson = Gson()
        var bean = gson.fromJson<XLDownloadDBBean>(json, XLDownloadDBBean::class.java)
        dao.delete(bean.Name)
    }

    @ReactMethod
    fun deleteAll() {
        dao.deleteAll()
    }

    @ReactMethod
    fun update(json: String) {
        var gson = Gson()
        var bean = gson.fromJson<XLDownloadDBBean>(json, XLDownloadDBBean::class.java)
        dao.update(bean)
    }
}