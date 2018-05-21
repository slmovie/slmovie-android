package cf.movie.slmovie.main.douban.presenter

import cf.movie.slmovie.main.douban.model.Top250.Top250Adapter

/**
 * Created by 包俊 on 2018/5/21.
 */
interface DoubanPresenterImpl {

    fun setAdapter(adapter: Top250Adapter?, position: Int)

    fun reqError(msg: String)

}