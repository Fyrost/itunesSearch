package com.example.myapplication.ui.itunes.description

import android.content.Context
import android.widget.ImageView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.myapplication.data.db.entity.ITunesResult
import com.example.myapplication.data.repository.DatabaseRepository
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.Year

class DescriptionViewModel(
    private val databaseRepository: DatabaseRepository,
    private val appContext: Context
) : ViewModel() {

    private lateinit var result: ITunesResult
    
    private val _kind = MutableLiveData<String>()
    val kind: LiveData<String>
        get() = _kind
    private val _trackName = MutableLiveData<String>()
    val trackName: LiveData<String>
        get() = _trackName
    private val _artworkUrl100 = MutableLiveData<String>()
    val artworkUrl100: LiveData<String>
        get() = _artworkUrl100
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

    fun insertResult() {
        databaseRepository.insertResult(result)
    }

    fun detailDescription(iTunesResult: ITunesResult){
        result = iTunesResult
        _kind.value = iTunesResult.kind?.capitalize()
        _trackName.value = iTunesResult.trackName
        _artworkUrl100.value = iTunesResult.artworkUrl100
        _primaryGenreName.value = iTunesResult.primaryGenreName
        _releaseDate.value = getYear(iTunesResult.releaseDate)
        _trackPrice.value = priceFormat(iTunesResult.trackPrice)
        _artistName.value = iTunesResult.artistName
        _longDescription.value = iTunesResult.longDescription
        _collectionName.value = iTunesResult.collectionName
        _collectionPrice.value = priceFormat(iTunesResult.collectionPrice)

        if(iTunesResult.kind=="song" || iTunesResult.kind=="music-video"){

        }
    }
    @BindingAdapter("app:imageUrl")
    fun loadImage(view: ImageView, url: String) {
        Glide.with(view.context).load(url).into(view)
    }

    private fun getYear(string : String?) : String {
        return OffsetDateTime.parse(string).year.toString()
    }

    private fun priceFormat(double : Double?) : String {
        if (double != null) {
            if(double > 0) return "$ $double"
        }
        return "FREE"
    }
}
