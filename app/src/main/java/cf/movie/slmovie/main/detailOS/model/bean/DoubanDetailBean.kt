package cf.movie.slmovie.main.detailOS.model.bean

import cf.movie.slmovie.base.BaseVo
import cf.movie.slmovie.main.douban.model.Top250.Top250Bean

/**
 * Created by 包俊 on 2018/5/25.
 */
class DoubanDetailBean : BaseVo() {
    var rating: Top250Bean.rating? = null
    var year: String? = null
    var images: Top250Bean.images? = null
    var alt: String? = null
    var id: String? = null
    var mobile_url: String? = null
    var title: String? = null
    var share_url: String? = null
    var countries: ArrayList<String>? = null
    var genres: ArrayList<String>? = null
    var casts: ArrayList<Top250Bean.casts>? = null
    var summary: String? = null
    var directors: ArrayList<Top250Bean.casts>? = null
}