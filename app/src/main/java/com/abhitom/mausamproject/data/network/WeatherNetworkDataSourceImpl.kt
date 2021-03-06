package com.abhitom.mausamproject.data.network

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import com.abhitom.mausamproject.internal.ToastMaker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherNetworkDataSourceImpl(context: Context,
    private val toastMaker: ToastMaker
    ) : WeatherNetworkDataSource {

    private val appContext=context.applicationContext
    private var _downloadedCurrentWeather = MutableLiveData<OneCallResponse>()
    override val downloadedCurrentWeather: LiveData<OneCallResponse>
        get() = _downloadedCurrentWeather

    override fun fetchCurrentWeather(lat: Double, lon: Double, units: String) {

        OpenWeatherAPIRetrofitClient.instance.openWeatherAPIService.oneCallApi(lat,lon , units)
                .enqueue(object : Callback<OneCallResponse> {
                    override fun onResponse(
                            call: Call<OneCallResponse>,
                            response: Response<OneCallResponse>
                    ) {
                        if (response.isSuccessful) {
                            _downloadedCurrentWeather.postValue(response.body())
                        } else {
                            //toastMaker.toastMaker("ERROR CODE - ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<OneCallResponse>, t: Throwable) {
                        //toastMaker.toastMaker("NO INTERNET")
                    }
                })
    }
}