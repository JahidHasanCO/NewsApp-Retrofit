package dev.JahidHasanCo.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.JahidHasanCo.newsapp.adapter.HeadlineAdapter
import dev.JahidHasanCo.newsapp.data.News
import dev.JahidHasanCo.newsapp.network.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var headlineRecView: RecyclerView
    lateinit var headlineAdapter: HeadlineAdapter

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headlineRecView = findViewById(R.id.headlineRecView)
        swipeRefreshLayout = findViewById(R.id.swipeContainer_ActivityMain)
        getHeadLines()

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getHeadLines()
        }

    }


    private fun getHeadLines() {
        val news = NewsService.newsInstance.getHeadLines("in", 1)
        news.enqueue(object : Callback<News> {

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    headlineAdapter = HeadlineAdapter(this@MainActivity)
                    headlineAdapter.clear()
                    headlineAdapter.addAll(news.articles)
                    headlineRecView.adapter = headlineAdapter
                    headlineRecView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL, false)

                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("JAHIDHASAN", "Error in Fatching News", t)
            }

        })
    }

}