package com.example.weather_app

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okio.IOException
import kotlin.math.log
//

class ViewModelClass:ViewModel() {

//
    val apiInstance =Retrofit_Instance.getInstance().create(Api_Interface::class.java)
    val key="e659d4494b5349a48e973315242706 "
    val _weatherResult= MutableLiveData<NetworkState<WeatherDataModel>>()
    val weatherResult:LiveData<NetworkState<WeatherDataModel>> =_weatherResult

    fun getdefault(connection:Boolean){





        try {
            if (connection) {
//                _weatherResult.value=NetworkState.Loading
                viewModelScope.launch {
                    val Default_Response = apiInstance.getWeatherData(key, "Delhi India ")
                    if (Default_Response.isSuccessful) {
                        Default_Response.body()?.let {
                            _weatherResult.value = NetworkState.Default(it)
                        }
                    }
                }
            }
            else{
                _weatherResult.value=NetworkState.Connetivity_error("Check your internet connetion ")
            }
        }
        catch (e:Exception){

        }






    }


    fun getcity(city:String,connection: Boolean){
        if (city.isEmpty()){
        }
        else {

            if (connection) {
                _weatherResult.value = NetworkState.Loading
                viewModelScope.launch {
                    val response = apiInstance.getWeatherData(key, city)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _weatherResult.value = NetworkState.Success(it)
                        }
                    } else {

                        _weatherResult.value = NetworkState.Error("Sorry! Data not Found")
                    }
                }

            }
            else{
                _weatherResult.value=NetworkState.Connetivity_error("Check your internet connetion ")
            }
        }

    }
}