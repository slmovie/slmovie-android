package cf.movie.slmovie.main.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * Created by 包俊 on 2017/8/11.
 */

public class ToastDialog extends Dialog{
    public ToastDialog(@NonNull Context context) {
        super(context);
    }

    public ToastDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ToastDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
