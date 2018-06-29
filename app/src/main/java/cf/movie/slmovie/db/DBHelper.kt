package cf.movie.slmovie.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by 包俊 on 2018/6/11.
 */
class DBHelper(context: Context) : SQLiteOpenHelper(context, DBConstans.XLDownloadDBName, null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        val sql = "create table if not exists " + DBConstans.XLDownliadDBTable + " (Id integer primary key, TotalSize text, DownloadSize text, Name text, DownloadPath text, SavePath text, TorrentPath text, IsTorrent integer, Data text , DownloadStatus integer, TaskId integer , mCid String)"
        sqLiteDatabase!!.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}