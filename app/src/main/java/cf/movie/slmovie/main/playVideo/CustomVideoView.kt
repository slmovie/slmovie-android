package cf.movie.slmovie.main.playVideo

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.VideoView

/**
 * Created by 包俊 on 2018/7/4.
 */
class CustomVideoView : VideoView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {//重写onMeasure方法，改变长宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.getDefaultSize(0, widthMeasureSpec)
        val height = View.getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun setOnPreparedListener(l: MediaPlayer.OnPreparedListener) {
        super.setOnPreparedListener(l)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return super.onKeyDown(keyCode, event)
    }

}
