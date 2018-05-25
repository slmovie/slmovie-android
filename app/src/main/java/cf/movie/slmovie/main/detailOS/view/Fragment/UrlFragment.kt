package cf.movie.slmovie.main.detailOS.view.Fragment

import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseFragment
import cf.movie.slmovie.main.detailOS.model.event.DetailOsEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by 包俊 on 2018/5/25.
 */
class UrlFragment : BaseFragment() {
    override val contentLayout: Int
        get() = R.layout.fragment_detailos_download

    override fun initGui() {
    }

    override fun initData() {
    }

    override fun initAction() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(result: DetailOsEvent) {
        if (result.isStatus) {

        } else {

        }
    }

}