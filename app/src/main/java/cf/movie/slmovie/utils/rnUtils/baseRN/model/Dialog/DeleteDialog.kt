package cf.movie.slmovie.utils.rnUtils.baseRN.model.Dialog

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import cf.movie.slmovie.R
import kotlinx.android.synthetic.main.dialog_delete_download.*


/**
 * Created by 包俊 on 2018/6/26.
 */
class DeleteDialog(var context: Activity, val listener: onButtonListener) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_delete_download)
        initAction()
    }

    private fun initAction() {
        btn.setOnClickListener {
            listener.click(this, rb.isChecked)
        }
    }

    interface onButtonListener {
        fun click(dialog: DeleteDialog, chosen: Boolean)
    }

}