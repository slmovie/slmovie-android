package cf.movie.slmovie.main.search.presenter

import android.app.Activity
import cf.movie.slmovie.main.search.event.SearchEvent
import cf.movie.slmovie.main.search.model.SearchModel
import cf.movie.slmovie.main.search.ui.ISearchActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 包俊 on 2017/8/12.
 */

class SearchPresenter(context: Activity, private val iSearch: ISearchActivity) {
    private val searchModel: SearchModel

    init {
        searchModel = SearchModel(context)
        EventBus.getDefault().register(this)
    }

    fun getMovies(search: String) {
        iSearch.swipe(true)
        searchModel.getMovies(search)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: SearchEvent) {
        iSearch.swipe(false)
        if (event.isStatus) {
            iSearch.setMovies(event)
        } else {
            iSearch.reqError("请求失败，请重试")
        }
    }

}
