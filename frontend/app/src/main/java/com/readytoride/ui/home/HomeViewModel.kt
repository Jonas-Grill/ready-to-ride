package com.readytoride.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.NewsApi.NewsApi
import com.readytoride.network.NewsApi.NewsEntity
import com.readytoride.network.NewsApi.PostNewsEntity
import com.readytoride.network.StableApi.StableApi
import com.readytoride.network.StableApi.StableEntity
import com.readytoride.network.UserApi.UserApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.Exception

class HomeViewModel : ViewModel() {

    private val _news = MutableLiveData<MutableList<NewsEntity>>()
    private val _newNews = MutableLiveData<NewsEntity>()
    private val _stables = MutableLiveData<StableEntity>()
    val news: LiveData<MutableList<NewsEntity>> = _news
    val newNews: LiveData<NewsEntity> = _newNews

    init {
        getAllStables()
    }

    internal fun getAllNews() {
        viewModelScope.launch {
            try {
                val listResult = NewsApi.retrofitService.getNews()
                _news.value = listResult
            } catch (e: Exception){
                println("Failure: ${e.message}")
            }
        }
    }

    internal fun postNews(token: String, news: PostNewsEntity) {
        viewModelScope.launch {
            try {
                val newsResult = NewsApi.retrofitService.postNews("Bearer $token", news)
                async { getAllNews() }
                _newNews.value = newsResult
            } catch (e: Exception) {
                println("Failure: ${e.message}")
            }
        }
    }

    private fun getAllStables() {
        viewModelScope.launch {
            try {
                val listResult = StableApi.retrofitService.getStables()
                _stables.value = listResult
            } catch (e: Exception){
                println("Failure: ${e.message}")
            }
        }
    }
}