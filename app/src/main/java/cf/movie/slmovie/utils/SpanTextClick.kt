package cf.movie.slmovie.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.widget.TextView

/**
 * Created by 包俊 on 2018/5/28.
 */
class SpanTextClick {
    companion object {
        fun setSpan(tv: TextView, info: String, listener: () -> Unit) {
            var strAll = SpannableString(info)
            strAll.setSpan(TextClickSpan(listener), 0, strAll.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv.text = strAll
            tv.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}