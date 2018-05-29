package cf.movie.slmovie.main.detail.presenter

import android.app.Activity

import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactInstanceManagerBuilder
import com.facebook.react.common.LifecycleState

import cf.movie.slmovie.BuildConfig
import cf.movie.slmovie.commonModels.reactUpdate.UpdateModel
import cf.movie.slmovie.main.detail.ui.IDetailView

/**
 * Created by 包俊 on 2017/8/15.
 */

class DetailPresenter(private val context: Activity, private val impl: IDetailView) {
    private val jsPath = "rn/detail"
    private val zip = "rn/detail.zip"
    private val bundle = "detail.android.bundle"
    private val updateModel: UpdateModel

    init {
        updateModel = UpdateModel(context)
    }

    //初始化react native
    fun initReact(): ReactInstanceManagerBuilder {
        val builder = ReactInstanceManager.builder()
        builder.setApplication(context.application)
//        builder.setBundleAssetName("rn/detail/detail.android.bundle")
        builder.setJSBundleFile(context.externalCacheDir!!.absolutePath + "/rn/detail/detail.android.bundle")
        builder.setJSMainModuleName("slmovie/rn/android/main/detail/detail.android")
        builder.setUseDeveloperSupport(BuildConfig.DEBUG)
        builder.setInitialLifecycleState(LifecycleState.RESUMED)
        impl.setReactPackage(builder)
        return builder
    }

    fun checkVersion(): Boolean {
        return updateModel.checkZip(zip, jsPath, bundle)
    }

}
