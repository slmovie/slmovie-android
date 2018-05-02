package cf.movie.slmovie.main.views

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.annotation.StyleRes

/**
 * Created by 包俊 on 2017/8/11.
 */

class ToastDialog : Dialog {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, @StyleRes themeResId: Int) : super(context, themeResId) {}

    protected constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener) {}
}
