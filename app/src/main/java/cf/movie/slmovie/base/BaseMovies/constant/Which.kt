package cf.movie.slmovie.base.BaseMovies.constant

import cf.movie.slmovie.server.HtmlCode

import android.os.Build.VERSION_CODES.N
import cf.movie.slmovie.server.HtmlCode.HotMovie

/**
 * Created by 包俊 on 2017/7/22.
 */

object Which {

    //热门电影
    val HotMovieStr = "热门电影"
    //最近更新
    val LastUpdateMovieStr = "最近更新"
    //动作
    val ActionMovieStr = "动作"
    //喜剧
    val ComedyStr = "喜剧"
    //爱情
    val LoveMovieStr = "爱情"
    //科幻
    val ScienceMovieStr = "科幻"
    //恐怖
    val HorrorMovieStr = "恐怖"
    //剧情
    val DramaMovieStr = "剧情"
    //战争
    val WarMovieStr = "战争"

    val NewTVStr = "最新"
    val ChinaTVStr = "国产"
    val HongTaiTVStr = "港台"
    val WestenTVStr = "欧美"
    val JapanKoreaTVStr = "日韩"

    enum class UrlType {
        HotMovie, LastUpdateMovie, ActionMovie, Comedy, LoveMovie, ScienceMovie, HorrorMovie,
        DramaMovie, WarMovie, NewTVs, ChinaTV, HongTaiTV, WestenTV, JapanKoreaTV
    }

    fun getUrl(type: UrlType): String {
        var url: String? = null
        when (type) {
            Which.UrlType.HotMovie -> url = HtmlCode.HotMovie
            Which.UrlType.LastUpdateMovie -> url = HtmlCode.NewMovies + 0
            Which.UrlType.ActionMovie -> url = HtmlCode.NewMovies + 1
            Which.UrlType.Comedy -> url = HtmlCode.NewMovies + 2
            Which.UrlType.LoveMovie -> url = HtmlCode.NewMovies + 3
            Which.UrlType.ScienceMovie -> url = HtmlCode.NewMovies + 4
            Which.UrlType.HorrorMovie -> url = HtmlCode.NewMovies + 5
            Which.UrlType.DramaMovie -> url = HtmlCode.NewMovies + 6
            Which.UrlType.WarMovie -> url = HtmlCode.NewMovies + 7
            Which.UrlType.NewTVs -> url = HtmlCode.NewTVs + 0
            Which.UrlType.ChinaTV -> url = HtmlCode.NewTVs + 1
            Which.UrlType.HongTaiTV -> url = HtmlCode.NewTVs + 2
            Which.UrlType.WestenTV -> url = HtmlCode.NewTVs + 3
            Which.UrlType.JapanKoreaTV -> url = HtmlCode.NewTVs + 4
        }
        return url
    }
}
