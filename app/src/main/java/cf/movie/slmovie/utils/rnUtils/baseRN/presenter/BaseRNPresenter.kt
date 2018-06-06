package cf.movie.slmovie.utils.rnUtils.baseRN.presenter

import android.app.Activity
import cf.movie.slmovie.BuildConfig
import cf.movie.slmovie.commonModels.reactUpdate.UpdateModel
import cf.movie.slmovie.utils.rnUtils.baseRN.view.IBaseRNView
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactInstanceManagerBuilder
import com.facebook.react.common.LifecycleState

/**
 * Created by 包俊 on 2017/8/15.
 */

class BaseRNPresenter(private val context: Activity, private val impl: IBaseRNView) {
    private val jsPath = "rn"
    private val zip = "rn/detail.zip"
    private val bundle = "root.android.bundle"
    private val updateModel: UpdateModel

    init {
        updateModel = UpdateModel(context)
    }

    //初始化react native
    fun initReact(): ReactInstanceManagerBuilder {
        var builder = ReactInstanceManager.builder()
        builder.setApplication(context.application)
//        builder.setBundleAssetName("rn/detail/detail.android.bundle")
        builder.setJSBundleFile(context.externalCacheDir!!.absolutePath + "/rn/root.android.bundle")
        builder.setJSMainModulePath("slmovie/rn/android/main/root.android")
        builder.setUseDeveloperSupport(BuildConfig.DEBUG)
        builder.setInitialLifecycleState(LifecycleState.RESUMED)
        impl.setReactPackage(builder)
        return builder
    }

    fun checkVersion(): Boolean {
        return updateModel.checkZip(zip, jsPath, bundle)
    }

}
