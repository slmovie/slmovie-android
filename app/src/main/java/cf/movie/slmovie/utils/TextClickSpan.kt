package cf.movie.slmovie.utils

import android.text.style.ClickableSpan
import android.view.View

/**
 * Created by 包俊 on 2018/5/25.
 */
class TextClickSpan(var listener: TextClickSpanListener) : ClickableSpan() {
    override fun onClick(v: View?) {
        listener.click()
    }

    interface TextClickSpanListener {
        fun click()
    }

}