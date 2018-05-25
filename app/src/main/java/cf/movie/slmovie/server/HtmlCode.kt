package cf.movie.slmovie.server

/**
 * Created by 包俊 on 2017/7/23.
 */

object HtmlCode {

    //热门电影
    val HotMovie = "/index/hotmovies"
    //最近更新最新电影
    val NewMovies = "/index/newmovies?index="
    //最新电视剧
    val NewTVs = "/index/newtvs?index="
    //电影详情
    val Details = "/detail?code="
    //电影搜索
    val Search = "/search/dyjy?name="
    //rn更新
    val RNUpdate = "/RNZIP/"

    //app更新
    val APPUpdate = "/appVersion/detail?version="
    //APK下载地址
    val APP = "/app/slys.apk"
    //根据豆瓣id查找电影
    val DouBanID = "/search/douban?name="
}
