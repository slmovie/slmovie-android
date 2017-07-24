package cf.movie.slmovie.base.BaseMovies.constant;

import cf.movie.slmovie.server.HtmlCode;

import static android.os.Build.VERSION_CODES.N;
import static cf.movie.slmovie.server.HtmlCode.HotMovie;

/**
 * Created by 包俊 on 2017/7/22.
 */

public class Which {
    public enum UrlType {
        HotMovie, LastUpdateMovie, ActionMovie, Comedy, LoveMovie, ScienceMovie, HorrorMovie,
        DramaMovie, WarMovie, NewTVs, ChinaTV, HongTaiTV, WestenTV, JapanKoreaTV
    }

    //热门电影
    public static final String HotMovieStr = "热门电影";
    //最近更新
    public static final String LastUpdateMovieStr = "最近更新";
    //动作
    public static final String ActionMovieStr = "动作";
    //喜剧
    public static final String ComedyStr = "喜剧";
    //爱情
    public static final String LoveMovieStr = "爱情";
    //科幻
    public static final String ScienceMovieStr = "科幻";
    //恐怖
    public static final String HorrorMovieStr = "恐怖";
    //剧情
    public static final String DramaMovieStr = "剧情";
    //战争
    public static final String WarMovieStr = "战争";

    public static final String NewTVStr = "最新";
    public static final String ChinaTVStr = "国产";
    public static final String HongTaiTVStr = "港台";
    public static final String WestenTVStr = "欧美";
    public static final String JapanKoreaTVStr = "日韩";

    public static String getUrl(UrlType type) {
        String url = null;
        switch (type) {
            case HotMovie:
                url = HtmlCode.HotMovie;
                break;
            case LastUpdateMovie:
                url = HtmlCode.NewMovies + 0;
                break;
            case ActionMovie:
                url = HtmlCode.NewMovies + 1;
                break;
            case Comedy:
                url = HtmlCode.NewMovies + 2;
                break;
            case LoveMovie:
                url = HtmlCode.NewMovies + 3;
                break;
            case ScienceMovie:
                url = HtmlCode.NewMovies + 4;
                break;
            case HorrorMovie:
                url = HtmlCode.NewMovies + 5;
                break;
            case DramaMovie:
                url = HtmlCode.NewMovies + 6;
                break;
            case WarMovie:
                url = HtmlCode.NewMovies + 7;
                break;
            case NewTVs:
                url = HtmlCode.NewTVs + 0;
                break;
            case ChinaTV:
                url = HtmlCode.NewTVs + 1;
                break;
            case HongTaiTV:
                url = HtmlCode.NewTVs + 2;
                break;
            case WestenTV:
                url = HtmlCode.NewTVs + 3;
                break;
            case JapanKoreaTV:
                url = HtmlCode.NewTVs + 4;
                break;

        }
        return url;
    }
}
