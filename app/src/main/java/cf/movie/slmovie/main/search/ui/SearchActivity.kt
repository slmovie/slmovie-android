package cf.movie.slmovie.main.search.ui

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View

import cf.movie.slmovie.R
import cf.movie.slmovie.base.BaseActivity
import cf.movie.slmovie.main.detail.ui.DetailActivity
import cf.movie.slmovie.main.search.bean.SearchAdapter
import cf.movie.slmovie.main.search.presenter.SearchPresenter
import cf.movie.slmovie.utils.impl.RecyclerItemClickListener

class SearchActivity : BaseActivity(), ISearchActivity {
    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var presenter: SearchPresenter? = null
    private var swipeLayout: SwipeRefreshLayout? = null
    private var adapter: SearchAdapter? = null
    private var searchView: SearchView? = null
    private var container: CoordinatorLayout? = null

    override val contentLayout: Int
        get() = R.layout.activity_search_result

    override fun initGui() {
        toolbar = findViewById(R.id.toolbar) as Toolbar
        recyclerView = findViewById(R.id.recycler) as RecyclerView
        swipeLayout = findViewById(R.id.swipeLayout) as SwipeRefreshLayout
        container = findViewById(R.id.container) as CoordinatorLayout
    }

    override fun initData() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "搜索"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        presenter = SearchPresenter(this, this)
        swipeLayout!!.setColorSchemeColors(Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.RED)
        swipeLayout!!.setProgressBackgroundColorSchemeColor(Color.WHITE)
        swipeLayout!!.setSize(SwipeRefreshLayout.DEFAULT)
        swipeLayout!!.isEnabled = false
    }

    override fun initAction() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView!!.isIconified = false
        searchView!!.queryHint = "请输入电影"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter!!.getMovies(query)
                supportActionBar!!.title = query
                searchView!!.setQuery("", false)
                searchView!!.clearFocus()
                searchView!!.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setAdapter(adapter: SearchAdapter) {
        this.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = OrientationHelper.VERTICAL
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.adapter = adapter
        recyclerView!!.addOnItemTouchListener(RecyclerItemClickListener(this, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@SearchActivity, DetailActivity::class.java)
                intent.putExtra("address", adapter.getMovies(position).id)
                intent.putExtra("name", adapter.getMovies(position).name)
                startActivity(intent)
            }

            override fun onLongItemClick(view: View?, position: Int) {

            }
        }))
    }

    override fun reqError(msg: String) {
        Snackbar.make(container!!, msg, BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("重新加载") {
                    swipeLayout!!.post {
                        swipeLayout!!.isRefreshing = true
                        presenter!!.getMovies(supportActionBar!!.title!!.toString())
                    }
                }
                .setActionTextColor(Color.parseColor("#3CC48D"))
                .show()
    }

    override fun swipe(refresh: Boolean) {
        swipeLayout!!.isRefreshing = refresh
    }
}
