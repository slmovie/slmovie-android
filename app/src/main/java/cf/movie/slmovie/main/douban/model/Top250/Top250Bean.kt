package cf.movie.slmovie.main.douban.model.Top250

import cf.movie.slmovie.base.BaseVo

/**
 * Created by 包俊 on 2018/5/21.
 */
class Top250Bean : BaseVo() {
    var count: Int = 0
    var start: Int = 0
    var subjects: ArrayList<subject>? = null

    inner class subject {
        var rating: rating? = null
        var genres: ArrayList<String>? = null
        var title: String? = null
        var casts: ArrayList<casts>? = null
        var directors: ArrayList<directors>? = null
        var images: images? = null
        var year: String? = null
        var id: String? = null
    }

    inner class rating {
        var max: String? = null
        var average: String? = null
        var stars: String? = null
        var min: String? = null
    }

    inner class casts {
        var name: String? = null
    }

    inner class directors {
        var name: String? = null
    }

    inner class images {
        var small: String? = null
        var large: String? = null
        var medium: String? = null
    }

}