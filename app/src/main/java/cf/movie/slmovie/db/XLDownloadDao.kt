package cf.movie.slmovie.db

import android.content.ContentValues
import android.content.Context
import cf.movie.slmovie.main.download.model.bean.XLDownloadDBBean

/**
 * Created by 包俊 on 2018/6/11.
 */
class XLDownloadDao(var context: Context) {
    //添加数据
    fun insert(bean: XLDownloadDBBean) {
        var db = DBUtils.get(context).open()
        db.beginTransaction()
        try {
            val values = ContentValues()
            values.put("TotalSize", bean.TotalSize)
            values.put("DownloadSize", bean.DownloadSize)
            values.put("Name", bean.Name)
            values.put("DownloadPath", bean.DownloadPath)
            values.put("SavePath", bean.SavePath)
            values.put("TorrentPath", bean.TorrentPath)
            values.put("IsTorrent", bean.IsTorrent)
            values.put("Data", "1")
            values.put("TastId", bean.TastId)
            values.put("DownloadStatus", bean.DownloadStatus)
            db.insert(DBConstans.XLDownliadDBTable, null, values)
        } catch (e: Exception) {

        } finally {
            db.setTransactionSuccessful()
            db.endTransaction()
            DBUtils.get(context).close()
        }
    }

    //查询所有数据
    fun findAll(): ArrayList<XLDownloadDBBean> {
        var list = ArrayList<XLDownloadDBBean>()
        var db = DBUtils.get(context).open()
        db.beginTransaction()
        try {
            val sql = ("select * from " + DBConstans.XLDownliadDBTable
                    + " where Data = ?")
            val cursor = db.rawQuery(sql, arrayOf("1"))
            if (cursor == null) {
                DBUtils.get(context).close()
                return list
            }

            while (cursor.moveToNext()) {
                val vo = XLDownloadDBBean()
                vo.TotalSize = cursor.getLong(cursor.getColumnIndex("TotalSize"))
                vo.DownloadSize = cursor.getLong(cursor.getColumnIndex("DownloadSize"))
                vo.Name = cursor.getString(cursor.getColumnIndex("Name"))
                vo.DownloadPath = cursor.getString(cursor
                        .getColumnIndex("DownloadPath"))
                vo.SavePath = cursor.getString(cursor
                        .getColumnIndex("SavePath"))
                vo.TorrentPath = cursor.getString(cursor.getColumnIndex("TorrentPath"))
                vo.IsTorrent = cursor.getInt(cursor
                        .getColumnIndex("IsTorrent"))
                vo.TastId = cursor.getLong(cursor
                        .getColumnIndex("TastId"))
                vo.DownloadStatus = cursor.getInt(cursor.getColumnIndex("DownloadStatus"))
                vo.Data = "1"
                list.add(vo)
            }
            DBUtils.get(context).closeCursor(cursor)
        } catch (e: Exception) {

        } finally {
            db.setTransactionSuccessful()
            db.endTransaction()
            DBUtils.get(context).close()
            return list
        }
    }

    fun find(bean: XLDownloadDBBean): XLDownloadDBBean {
        var vo = XLDownloadDBBean()
        var db = DBUtils.get(context).open()
        db.beginTransaction()
        try {
            val sql = ("select * from " + DBConstans.XLDownliadDBTable
                    + " where DownloadPath = ?")
            val cursor = db.rawQuery(sql, arrayOf(bean.DownloadPath))
            if (cursor == null) {
                DBUtils.get(context).close()
                return vo
            }

            while (cursor.moveToNext()) {
                vo.TotalSize = cursor.getLong(cursor.getColumnIndex("TotalSize"))
                vo.DownloadSize = cursor.getLong(cursor.getColumnIndex("DownloadSize"))
                vo.Name = cursor.getString(cursor.getColumnIndex("Name"))
                vo.DownloadPath = cursor.getString(cursor
                        .getColumnIndex("DownloadPath"))
                vo.SavePath = cursor.getString(cursor
                        .getColumnIndex("SavePath"))
                vo.TorrentPath = cursor.getString(cursor.getColumnIndex("TorrentPath"))
                vo.IsTorrent = cursor.getInt(cursor
                        .getColumnIndex("IsTorrent"))
                vo.TastId = cursor.getLong(cursor
                        .getColumnIndex("TastId"))
                vo.DownloadStatus = cursor.getInt(cursor.getColumnIndex("DownloadStatus"))
                vo.Data = "1"
            }
            DBUtils.get(context).closeCursor(cursor)
        } catch (e: Exception) {

        } finally {
            db.setTransactionSuccessful()
            db.endTransaction()
            DBUtils.get(context).close()
            return vo
        }
    }

    fun update(bean: XLDownloadDBBean) {
        var db = DBUtils.get(context).open()
        db.beginTransaction()
        try {
            val values = ContentValues()
            values.put("TotalSize", bean.TotalSize)
            values.put("DownloadSize", bean.DownloadSize)
            values.put("Name", bean.Name)
            values.put("DownloadPath", bean.DownloadPath)
            values.put("SavePath", bean.SavePath)
            values.put("TorrentPath", bean.TorrentPath)
            values.put("IsTorrent", bean.IsTorrent)
            values.put("Data", "1")
            values.put("TastId", bean.TastId)
            values.put("DownloadStatus", bean.DownloadStatus)
            db.update(DBConstans.XLDownliadDBTable, values, "Name = ?", arrayOf(bean.Name))
        } catch (e: Exception) {

        } finally {
            db.setTransactionSuccessful()
            db.endTransaction()
            DBUtils.get(context).close()
        }
    }

    fun delete(name: String) {
        var db = DBUtils.get(context).open()
        db.beginTransaction()
        try {
            db.delete(DBConstans.XLDownliadDBTable, "Name = ?", arrayOf(name))
        } catch (e: Exception) {

        } finally {
            db.setTransactionSuccessful()
            db.endTransaction()
            DBUtils.get(context).close()
        }
    }

    //删除所有
    fun deleteAll() {
        var db = DBUtils.get(context).open()
        db.beginTransaction()
        try {
            db.delete(DBConstans.XLDownliadDBTable, "Data = ?", arrayOf("1"))
        } catch (e: Exception) {

        } finally {
            db.setTransactionSuccessful()
            db.endTransaction()
            DBUtils.get(context).close()
        }
    }
}
