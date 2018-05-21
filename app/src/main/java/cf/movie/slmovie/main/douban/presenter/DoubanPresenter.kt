package cf.movie.slmovie.main.douban.presenter

import android.content.Context
import cf.movie.slmovie.base.BaseReqListener
import cf.movie.slmovie.main.douban.model.Top250.Top250Adapter
import cf.movie.slmovie.main.douban.model.Top250.Top250Bean
import cf.movie.slmovie.main.douban.model.Top250.Top250Model

/**
 * Created by 包俊 on 2018/5/21.
 */
class DoubanPresenter(private val context: Context, private val impl: DoubanPresenterImpl) {

    private var top250Model: Top250Model = Top250Model()
    private var movies: ArrayList<Top250Bean.subject>? = ArrayList()
    private var adapter: Top250Adapter? = null

    fun getTop250(refresh: Boolean) {
        var start = 0
        if (!refresh) {
            start = movies!!.size + 1
        }
        top250Model.start(context, start, object : BaseReqListener<Top250Bean> {
            override fun success(result: Top250Bean) {
                if (refresh)
                    movies!!.clear()
                movies!!.addAll(result.subjects!!)
                if (adapter == null || refresh) {
                    adapter = Top250Adapter(context, movies!!)
                    impl.setAdapter(adapter!!, 0)
                } else {
                    adapter!!.movies = movies!!
                    adapter!!.notifyDataSetChanged()
                    impl.setAdapter(null, movies!!.size - result.count)
                }
            }

            override fun failed(errorCode: String?, errorMsg: String?) {
                impl.reqError(errorMsg!!)
            }

        })
    }
}