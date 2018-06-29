package cf.movie.slmovie.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentFileInfo;
import com.xunlei.downloadlib.parameter.TorrentInfo;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.util.ArrayList;

import cf.movie.slmovie.R;

public class MainActivity2 extends AppCompatActivity {
    EditText inputUrl;
    Button btnDownload, btn_torrent, btn_stop;
    TextView tvStatus;
    long taskId;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                long taskId = (long) msg.obj;
                XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(taskId);
                tvStatus.setText(
                        "fileSize:" + taskInfo.mFileSize
                                + " downSize:" + taskInfo.mDownloadSize
                                + " speed:" + convertFileSize(taskInfo.mDownloadSpeed)
                                + "/s dcdnSpeed:" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed)
                                + "/s filePath:" + "/sdcard/" + XLTaskHelper.instance().getFileName(inputUrl.getText().toString())
                );
                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);
            }
        }
    };

    private String torrent = "123456.torrent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        XLTaskHelper.init(getApplicationContext());
        inputUrl = (EditText) findViewById(R.id.input_url);
        inputUrl.setText("magnet:?xt=urn:btih:62e6553c91ff6b36ea8414ca8c18632856284533&dn=大佛普拉斯.2017.简繁中字￡CMCT呆呆熊&tr=udp://tracker.opentrackr.org:1337/announce&tr=udp://tracker.coppersurfer.tk:6969/announce&tr=udp://tracker.openbittorrent.com:80/announce");
        btn_torrent = (Button) findViewById(R.id.btn_torrent);
        btnDownload = (Button) findViewById(R.id.btn_down);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        tvStatus = (TextView) findViewById(R.id.tv_status);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(inputUrl.getText())) {
                    try {
                        taskId = XLTaskHelper.instance().addMagentTask(inputUrl.getText().toString(), "/sdcard/", torrent);
//                        taskId = XLTaskHelper.instance(getApplicationContext()).addTorrentTask2("/sdcard/" + torrent, "/sdcard/slys", getTorrentDeselectedIndexs());
//                        taskId = XLTaskHelper.instance(getApplicationContext()).addTorrentTask("/sdcard/" + torrent, "/sdcard/slys", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(handler.obtainMessage(0, taskId));
                }
            }
        });
        btn_torrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    XLTaskHelper.instance().addMagentTask(inputUrl.getText().toString(), "/sdcard/", torrent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTorrentIndexs();
//                XLTaskHelper.instance(getApplicationContext()).stopTask(taskId);
            }
        });
    }

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f M" : "%.1f M", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f K" : "%.1f K", f);
        } else
            return String.format("%d B", size);
    }


    private TorrentInfo torrentInfo;
    private int[] torrentMediaIndexs = null;
    private int[] torrentUnmediaIndexs = null;
    private int currentPlayMediaIndex = 0;

    private void initTorrentIndexs() {
        torrentInfo = XLTaskHelper.instance().getTorrentInfo("/sdcard/" + torrent);
        this.currentPlayMediaIndex = -1;
        if (this.torrentInfo != null && this.torrentInfo.mSubFileInfo != null) {
            ArrayList<Integer> mediaIndexs = new ArrayList<>();
            ArrayList<Integer> unmediaIndexs = new ArrayList<>();
            for (int i = 0; i < torrentInfo.mSubFileInfo.length; i++) {
                TorrentFileInfo torrentFileInfo = torrentInfo.mSubFileInfo[i];
                if (isMediaFile(torrentFileInfo.mFileName)) {
                    mediaIndexs.add(torrentFileInfo.mFileIndex);
                } else {
                    unmediaIndexs.add(torrentFileInfo.mFileIndex);
                }
            }
            this.torrentMediaIndexs = new int[mediaIndexs.size()];
            this.torrentUnmediaIndexs = new int[unmediaIndexs.size()];
            for (int i = 0; i < mediaIndexs.size(); i++)
                this.torrentMediaIndexs[i] = mediaIndexs.get(i);
            for (int i = 0; i < unmediaIndexs.size(); i++)
                this.torrentUnmediaIndexs[i] = unmediaIndexs.get(i);
            this.currentPlayMediaIndex = this.torrentMediaIndexs.length > 0 ? this.torrentMediaIndexs[0] : -1;
        }
    }

    private int[] getTorrentDeselectedIndexs() {
        int[] indexs = new int[this.torrentUnmediaIndexs.length + this.torrentMediaIndexs.length - 1];
        int offset = 0;
        for (int idx : this.torrentMediaIndexs) {
            if (idx != this.currentPlayMediaIndex) {
                indexs[offset++] = idx;
            }
        }
        for (int idx : this.torrentUnmediaIndexs) {
            indexs[offset++] = idx;
        }
        return indexs;
    }

    public static boolean isMediaFile(String fileName) {
        switch (getFileExt(fileName)) {
            case ".avi":
            case ".mp4":
            case ".m4v":
            case ".mkv":
            case ".mov":
            case ".mpeg":
            case ".mpg":
            case ".mpe":
            case ".rm":
            case ".rmvb":
            case ".3gp":
            case ".wmv":
            case ".asf":
            case ".asx":
            case ".dat":
            case ".vob":
            case ".m3u8":
                return true;
            default:
                return false;
        }
    }

    public static String getFileExt(String fileName) {
        if (TextUtils.isEmpty(fileName)) return "";
        int p = fileName.lastIndexOf('.');
        if (p != -1) {
            return fileName.substring(p).toLowerCase();
        }
        return "";
    }
}
