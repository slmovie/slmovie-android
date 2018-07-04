package cf.movie.slmovie.main.home.ui

import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import cf.movie.slmovie.base.BaseMovies.constant.Which
import cf.movie.slmovie.base.BaseMovies.ui.BaseMoviesFragment
import cf.movie.slmovie.main.douban.view.DoubanView
import cf.movie.slmovie.main.download.view.DownloadRNActivity
import cf.movie.slmovie.main.home.presenter.MainActivityPresenter
import cf.movie.slmovie.main.newMovies.ui.NewMoviesFragment
import cf.movie.slmovie.main.newMovies.ui.NewTVsFragment
import cf.movie.slmovie.main.search.ui.SearchActivity
import cf.movie.slmovie.utils.LogUtils
import cf.movie.slmovie.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by 包俊 on 2017/7/19.
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var fragmentManager: FragmentManager? = null
    private var hotMoviesFragment: BaseMoviesFragment? = null
    private var newMoviesFragment: NewMoviesFragment? = null
    private var newTVsFragment: NewTVsFragment? = null
    private var top250: DoubanView? = null
    private var tv_name: TextView? = null
    private var lastClickTime: Long = 0
    private var presenter: MainActivityPresenter? = null

    companion object {
        val WRITE_EXTERNAL_STORAGE: Int = 5001
        val XLDownload: Int = 5002
    }


    override val contentLayout: Int
        get() = R.layout.activity_main


    /**
     * @return boolean
     * @Title: isFastDoubleClick
     * @Description: 判断事件出发时间间隔是否超过预定值
     */
    val isFastDoubleClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            val timeD = time - lastClickTime
            if (0 < timeD && timeD < 1500) {
                return true
            }
            lastClickTime = time
            return false
        }

    override fun initGui() {
        val HeadView = nav_view!!.getHeaderView(0)
        tv_name = HeadView.findViewById(R.id.tv_name) as TextView
        LogUtils.e("viewPage", "Main>>>" + tv_name!!.id)
    }

    override fun initData() {
        presenter = MainActivityPresenter(this)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "热门电影"
        toolbar!!.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.search -> {
                    val intent = Intent(this@MainActivity, SearchActivity::class.java)
                    startActivity(intent)
                }
                R.id.download -> {
                    val intent = Intent(this, DownloadRNActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout!!.setDrawerListener(toggle)
        toggle.syncState()
        nav_view!!.menu.getItem(0).isChecked = true
        fragmentManager = supportFragmentManager
        hotMoviesFragment = BaseMoviesFragment.newInstance(Which.UrlType.HotMovie)
        newTVsFragment = NewTVsFragment.newInstance()
        newMoviesFragment = NewMoviesFragment.newInstance()
        top250 = DoubanView()
        fragmentManager!!.beginTransaction().add(R.id.frameLayout, hotMoviesFragment).add(R.id.frameLayout, newMoviesFragment).add(R.id.frameLayout, newTVsFragment).add(R.id.frameLayout, top250).commitAllowingStateLoss()
        fragmentManager!!.beginTransaction().hide(newTVsFragment).hide(newMoviesFragment).hide(top250).show(hotMoviesFragment).commitAllowingStateLoss()
        presenter!!.checkUpdate()
    }

    override fun initAction() {
        nav_view!!.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            if (isFastDoubleClick) {
                System.exit(0)
            } else {
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_LONG).show()
            }
        }
    }

    //加载菜单
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.movie_new) {
            supportActionBar!!.title = "最新电影"
            fragmentManager!!.beginTransaction().hide(newTVsFragment).hide(hotMoviesFragment).show(newMoviesFragment).hide(top250).commitAllowingStateLoss()
        } else if (id == R.id.movie_hot) {
            supportActionBar!!.title = "热门电影"
            fragmentManager!!.beginTransaction().hide(newTVsFragment).hide(newMoviesFragment).show(hotMoviesFragment).hide(top250).commitAllowingStateLoss()
        } else if (id == R.id.tv_new) {
            supportActionBar!!.title = "最新电视剧"
            fragmentManager!!.beginTransaction().show(newTVsFragment).hide(newMoviesFragment).hide(hotMoviesFragment).hide(top250).commitAllowingStateLoss()
        } else if (id == R.id.tv_top250) {
            supportActionBar!!.title = "豆瓣Top250"
            fragmentManager!!.beginTransaction().hide(newTVsFragment).hide(newMoviesFragment).hide(hotMoviesFragment).show(top250).commitAllowingStateLoss()
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.requestResult(this, grantResults, permissions, {
            presenter!!.checkUpdate()
        }, {
        })
    }

}
