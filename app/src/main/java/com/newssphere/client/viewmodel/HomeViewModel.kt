package com.newssphere.client.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newssphere.client.model.data_class.NewsCollection
import com.newssphere.client.model.remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    private var _newsCollection = MutableLiveData<NewsCollection>()
    val newsCollection : LiveData<NewsCollection> = _newsCollection

    private var _newsCollection2 = MutableLiveData<NewsCollection>()
    val newsCollection2 : LiveData<NewsCollection> = _newsCollection2

    fun getNewsCollection(category: String?= null, page: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCollectionTopHeadlineNews(category = category, page = page)

        client.enqueue(object: retrofit2.Callback<NewsCollection>{
            override fun onResponse(
                call: Call<NewsCollection>,
                response: Response<NewsCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _isFail.value = false
                    _newsCollection.value = response.body()
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getNewsCollectionMore(category: String?= null, page: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCollectionTopHeadlineNews(category = category, page = page)

        client.enqueue(object: retrofit2.Callback<NewsCollection>{
            override fun onResponse(
                call: Call<NewsCollection>,
                response: Response<NewsCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _isFail.value = false
                    _newsCollection2.value = response.body()
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}