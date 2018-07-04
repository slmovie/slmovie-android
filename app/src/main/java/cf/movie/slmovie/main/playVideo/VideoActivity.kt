package cf.movie.slmovie.main.playVideo

import android.net.Uri
import android.widget.MediaController
import android.widget.RelativeLayout
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import kotlinx.android.synthetic.main.activity_video.*


/**
 * Created by 包俊 on 2018/7/4.
 */
class VideoActivity : BaseActivity() {
    override val contentLayout: Int
        get() = R.layout.activity_video

    override fun initGui() {
    }

    override fun initData() {
        var url = intent.getStringExtra("url")
        var uri = Uri.parse(url)
        video.setMediaController(MediaController(this))
        video.setVideoURI(uri)
        val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)

        video.start()
    }

    override fun initAction() {
    }

}