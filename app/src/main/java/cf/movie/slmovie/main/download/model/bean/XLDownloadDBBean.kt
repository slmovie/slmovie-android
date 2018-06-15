package cf.movie.slmovie.main.download.model.bean

import cf.movie.slmovie.base.BaseVo

/**
 * Created by 包俊 on 2018/6/11.
 */
class XLDownloadDBBean : BaseVo() {
    var TotalSize: Long = 0L
    var DownloadSize: Long = 0L
    var Name: String = ""
    var DownloadPath: String = ""
    var TorrentPath: String = ""
    var SavePath: String = ""
    var TastId: Long = 0L
    //0连接中1下载中 2下载完成 3失败
    var DownloadStatus: Int = 0
    var IsTorrent: Int = 0
    var Data: String = "1"
    var Speed: Long = 0
}