package com.newssphere.client.model.remote

import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.Companion.US
import com.newssphere.client.model.Constants.KEY.Companion.NEWSAPI_KEY
import com.newssphere.client.model.data_class.NewsCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    fun getCollectionTopHeadlineNews(
        @Query("apiKey") apiKey: String = NEWSAPI_KEY,
        @Query("country") country: String?= US,
        @Query("category") category: String? = null,
        @Query("q") searchInput: String?= null,
        @Query("page") page: Int
    ): Call<NewsCollection>
}