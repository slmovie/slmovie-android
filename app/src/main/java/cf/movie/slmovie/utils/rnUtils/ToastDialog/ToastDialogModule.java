package cf.movie.slmovie.utils.rnUtils.ToastDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import java.util.ArrayList;

import cf.movie.slmovie.R;

import static android.R.string.cancel;

/**
 * Created by 包俊 on 2017/8/10.
 */

public class ToastDialogModule extends ReactContextBaseJavaModule {

    private Activity context;
    private AlertDialog dialog = null;

    public ToastDialogModule(ReactApplicationContext reactContext, Activity activity) {
        super(reactContext);
        this.context = activity;
    }

    @Override
    public String getName() {
        return "ToastDialogNative";
    }

    /**
     * @param title 标题
     * @param msg   内容
     */
    @ReactMethod
    public void show(String title, String msg, ReadableArray buttons, final Callback ok, final Callback cancel) {
        ArrayList<Object> myButtons = buttons.toArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(myButtons.get(0).toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ok.invoke();
            }
        });
        if (myButtons.size() > 1) {
            builder.setNegativeButton(myButtons.get(1).toString(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancel.invoke();
                }
            });
        }
        dialog = builder.create();
        dialog.show();
    }

    @ReactMethod
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}