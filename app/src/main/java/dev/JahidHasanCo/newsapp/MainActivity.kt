package dev.JahidHasanCo.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dev.JahidHasanCo.newsapp.data.News
import dev.JahidHasanCo.newsapp.network.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getHeadLines()
    }


    private fun getHeadLines() {
        val news = NewsService.newsInstance.getHeadLines("in", 1)
        news.enqueue(object : Callback<News> {

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    Log.d("JAHIDHASAN", news.toString())
                }

            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("JAHIDHASAN", "Error in Fatching News", t)
            }

        })
    }

}