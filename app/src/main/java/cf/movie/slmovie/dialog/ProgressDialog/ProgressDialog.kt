package cf.movie.slmovie.dialog.ProgressDialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cf.movie.slmovie.R
import kotlinx.android.synthetic.main.dialog_progress.*

/**
 * Created by 包俊 on 2018/6/8.
 */
class ProgressDialog(context: Context, var info: String) : Dialog(context) {

    var data: String = ""
        set(info) {
            this.info = info
            initData()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        initData()
    }

    fun initData() {
        if (TextUtils.isEmpty(info))
            tv_info.visibility = View.GONE
        else {
            tv_info.visibility = View.VISIBLE
            tv_info.text = info
        }


    }

}