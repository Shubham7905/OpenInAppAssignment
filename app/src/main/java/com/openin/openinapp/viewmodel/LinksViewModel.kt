package com.openin.openinapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.openin.openinapp.model.CardModel
import com.openin.openinapp.model.Link
import com.openin.openinapp.repository.LinksRepository
import kotlinx.coroutines.launch

class LinksViewModel(private val repository: LinksRepository) : ViewModel() {

    private val _recentLinks = MutableLiveData<List<Link>>()
    val recentLinks: LiveData<List<Link>> get() = _recentLinks

    private val _topLinks = MutableLiveData<List<Link>>()
    val topLinks: LiveData<List<Link>> get() = _topLinks

    private val _users = MutableLiveData<List<CardModel>>()
    val users: LiveData<List<CardModel>> get() = _users

    private val _greeting = MutableLiveData<String>()
    val greeting: LiveData<String> get() = _greeting

    private val _lineChartData = MutableLiveData<List<Entry>>()
    val lineChartData: LiveData<List<Entry>> get() = _lineChartData

    init {
        _users.value = repository.getUsers()
        _greeting.value = repository.getGreetingMessage()
    }

    fun fetchLineChartData() {
        _lineChartData.value = repository.getLineChartData()
    }

    fun fetchLinks(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLinks(token)
                if (response.status) {
                    _recentLinks.postValue(response.data.recent_links)
                    _topLinks.postValue(response.data.top_links)
                }
            } catch (e: Exception) {

            }
        }
    }
}
