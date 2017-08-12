package cf.movie.slmovie.server;

/**
 * Created by 包俊 on 2017/7/21.
 */

public class Constant {
    private static String ENVIRONMENT_TYPE = "P";
    public static boolean Log = true;
    public static String WEBROOT;

    static {
        if (ENVIRONMENT_TYPE.equals("T")) {
            WEBROOT = "http://192.168.1.104:3000";
        }
        if (ENVIRONMENT_TYPE.equals("P")) {
            WEBROOT = "http://www.slys.cf";
        }
    }

}
