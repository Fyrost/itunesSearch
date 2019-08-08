package com.example.myapplication.ui.itunes.description


import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.bumptech.glide.Glide

import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.repository.DatabaseRepository
import com.example.myapplication.ui.utils.getYear
import com.example.myapplication.ui.utils.largerImage
import com.example.myapplication.ui.utils.priceFormat


class DescriptionViewModel(
    private val databaseRepository: DatabaseRepository,
    private val appContext: Context
) : ViewModel() {

    private lateinit var result: ITunesResult
    lateinit var artworkUrl100: String

    var artistLabel: String = "ARTIST"
    var descriptionVisible: Boolean = true
    var albumVisible: Boolean = true

    private val _kind = MutableLiveData<String>()
    val kind: LiveData<String>
        get() = _kind
    private val _trackName = MutableLiveData<String>()
    val trackName: LiveData<String>
        get() = _trackName
    private val _primaryGenreName = MutableLiveData<String>()
    val primaryGenreName: LiveData<String>
        get() = _primaryGenreName
    private val _releaseDate = MutableLiveData<String>()
    val releaseDate: LiveData<String>
        get() = _releaseDate
    private val _trackPrice = MutableLiveData<String>()
    val trackPrice: LiveData<String>
        get() = _trackPrice
    private val _artistName = MutableLiveData<String>()
    val artistName: LiveData<String>
        get() = _artistName
    private val _longDescription = MutableLiveData<String>()
    val longDescription: LiveData<String>
        get() = _longDescription
    private val _collectionName = MutableLiveData<String>()
    val collectionName: LiveData<String>
        get() = _collectionName
    private val _collectionPrice = MutableLiveData<String>()
    val collectionPrice: LiveData<String>
        get() = _collectionPrice


    fun onfavoriteclick(v : View) {
        databaseRepository.insertResult(result)
    }

    fun detailDescription(iTunesResult: ITunesResult){
        result = iTunesResult
        _kind.value = iTunesResult.kind?.capitalize()
        _trackName.value = iTunesResult.trackName
        artworkUrl100 = iTunesResult.artworkUrl100.largerImage()
        _primaryGenreName.value = iTunesResult.primaryGenreName
        _releaseDate.value = iTunesResult.releaseDate.getYear()
        _trackPrice.value = iTunesResult.trackPrice.priceFormat()
        _artistName.value = iTunesResult.artistName
        _longDescription.value = iTunesResult.longDescription
        _collectionName.value = iTunesResult.collectionName
        _collectionPrice.value = iTunesResult.collectionPrice.priceFormat()

        descriptionVisible = !(iTunesResult.longDescription.isNullOrBlank())
        albumVisible = !(iTunesResult.collectionName.isNullOrBlank() || !(iTunesResult.kind=="song" || iTunesResult.kind=="music-video") )
        artistLabel = if(iTunesResult.kind == "tv-episode") "SHOW" else "ARTIST"
    }
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context).load(url).into(view)
}
