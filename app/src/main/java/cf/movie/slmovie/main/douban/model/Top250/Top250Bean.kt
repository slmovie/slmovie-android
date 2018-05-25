package cf.movie.slmovie.main.douban.model.Top250

import cf.movie.slmovie.base.BaseVo

/**
 * Created by 包俊 on 2018/5/21.
 */
class Top250Bean : BaseVo() {
    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var subjects: ArrayList<subject>? = null

    inner class subject : BaseVo() {
        var rating: rating? = null
        var genres: ArrayList<String>? = null
        var title: String? = null
        var casts: ArrayList<casts>? = null
        var collect_count: Int? = null
        var original_title: String? = null
        var directors: ArrayList<casts>? = null
        var year: String? = null
        var images: images? = null
        var alt: String? = null
        var id: String? = null
    }

    inner class rating : BaseVo() {
        var max: String? = null
        var average: String? = null
        var stars: String? = null
        var min: String? = null
    }

    inner class casts : BaseVo() {
        var name: String? = null
        var alt: String? = null
        var id: Int? = null
        var avatars: images? = null
    }

    inner class images : BaseVo() {
        var small: String? = null
        var large: String? = null
        var medium: String? = null
    }

}