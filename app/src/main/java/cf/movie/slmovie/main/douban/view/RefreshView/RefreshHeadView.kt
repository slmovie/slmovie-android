package cf.movie.slmovie.main.douban.view.RefreshView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import cf.movie.slmovie.R
import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger
import com.aspsine.swipetoloadlayout.SwipeTrigger

/**
 * Created by 包俊 on 2018/5/21.
 */
class RefreshHeadView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs), SwipeRefreshTrigger, SwipeTrigger {

    private var tv: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_refresh_head, this, true)
        tv = findViewById(R.id.tv) as TextView
    }

    override fun onRefresh() {
        tv!!.text = "正在拼命加载......"
    }

    override fun onReset() {
        tv!!.text = "onReset"
    }

    override fun onComplete() {
        tv!!.text = "刷新完成"
    }

    override fun onRelease() {
        tv!!.text = "onRelease"
    }

    override fun onMove(yScrolled: Int, isComplete: Boolean, automatic: Boolean) {
        if (!isComplete) {
            if (yScrolled >= getHeight()) {
                tv!!.text = "松手刷新"
            } else {
                tv!!.text = "下拉刷新"
            }
        } else {
            tv!!.text = "正在刷新中......"
        }
    }

    override fun onPrepare() {
        tv!!.text = "onPrepare"
    }

}
