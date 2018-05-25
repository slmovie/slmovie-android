package cf.movie.slmovie.main.detailOS.view.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.detailOS.model.event.DetailOsEvent
import cf.movie.slmovie.main.douban.model.Top250.Top250Bean
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_detailos_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 包俊 on 2018/5/25.
 */
class InfoFragment : BaseFragment() {

    private var movie: Top250Bean.subject? = null

    override val contentLayout: Int
        get() = R.layout.fragment_detailos_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            movie = arguments?.getSerializable(ARG_PARAM1) as? Top250Bean.subject
        }
    }

    override fun initGui() {
    }

    override fun initData() {
        Glide.with(this)
                .load(movie?.images?.medium)
                .into(iv_post)
        tv_title.text = movie?.title
        tv_year_c.text = movie?.year

        var directors = ""
        if (movie?.directors?.size!! > 0) {
            movie?.directors?.forEach {
                directors = if (TextUtils.isEmpty(directors)) (it.name!!) else (directors + '、' + it.name)
            }
        }
        tv_director_c.text = directors

        var casts = ""
        if (movie?.casts?.size!! > 0) {
            movie?.casts?.forEach {
                casts = if (TextUtils.isEmpty(casts)) (it.name!!) else (casts + '、' + it.name)
            }
        }
        tv_actor_c.text = casts

        var genres = ""
        if (movie?.genres?.size!! > 0) {
            movie?.genres?.forEach {
                genres = if (TextUtils.isEmpty(genres)) it!! else (genres + '、' + it)
            }
        }
        tv_type_c.text = genres
        tv_db_c.text = movie?.id
    }

    override fun initAction() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(result: DetailOsEvent) {
        if (result.isStatus) {
            if (!TextUtils.isEmpty(result.movie!!.movies!!.describe)) {
                tv_des.text = result.movie!!.movies!!.describe
                tv_des.visibility = View.VISIBLE
            }
            if (!TextUtils.isEmpty(result.movie!!.movies!!.details!!.location)) {
                tv_location_c.text = result.movie!!.movies!!.details!!.location
                tv_location_c.visibility = View.VISIBLE
                tv_location.visibility = View.VISIBLE
            }
        } else {

        }
    }

    companion object {
        private val ARG_PARAM1 = "movies"

        fun newInstance(movie: Top250Bean.subject): InfoFragment {
            val fragment = InfoFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM1, movie)
            fragment.arguments = args
            return fragment
        }
    }
}