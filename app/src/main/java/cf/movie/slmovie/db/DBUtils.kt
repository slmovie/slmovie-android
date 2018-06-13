package cf.movie.slmovie.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**
 * Created by 包俊 on 2018/6/11.
 */
class DBUtils(context: Context) {

    private var helper: DBHelper? = null
    private var db: SQLiteDatabase? = null

    init {
        if (helper == null)
            helper = DBHelper(context)
    }

    companion object {
        private var instance: DBUtils? = null

        @Synchronized
        fun get(context: Context): DBUtils {
            if (instance == null) instance = DBUtils(context)
            return instance!!
        }
    }

    fun open(): SQLiteDatabase {
        if (db == null) {
            db = helper!!.getWritableDatabase()
        }
        return db!!
    }

    fun close() {
        if (db != null) {
            db!!.close()
            db = null
        }
    }

    fun closeCursor(cursor: Cursor?) {
        cursor?.close()
    }

    fun destroy() {
        if (db != null) {
            db!!.close()
            db = null
        }
    }
}