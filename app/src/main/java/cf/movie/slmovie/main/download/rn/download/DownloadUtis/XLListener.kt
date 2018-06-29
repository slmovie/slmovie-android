package cf.movie.slmovie.main.download.rn.download.DownloadUtis

import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean

/**
 * Created by 包俊 on 2018/6/29.
 */
interface XLListener {
    fun emitTaskId(bean: XLDownloadDBBean)

    fun hasTorrent()
}