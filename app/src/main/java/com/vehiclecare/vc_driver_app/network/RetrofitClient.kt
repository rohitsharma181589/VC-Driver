package com.vehiclecare.vc_driver_app.network

import com.vehiclecare.vc_driver_app.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private lateinit var INSTANCE: ApiInterface
    private const val MS_TIMEOUT = 30L


    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun baseUrlFromJNI(): String

    fun instance(): ApiInterface {


        val logging = HttpLoggingInterceptor()

// set your desired log level
// set your desired log level
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE;
        }


        val httpClient = OkHttpClient.Builder()
// add your other interceptors …

// add logging as last interceptor
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging) // <-- this is the important line!

//        httpClient.addInterceptor(responseInterceptor)

        //TODO: handle chain
//        httpClient.addInterceptor { chain ->
//            val request = chain.request()
//            val response = chain.proceed(request)
//            val responseBodyCopy = response.peekBody(Long.MAX_VALUE)
//            val bodyResponse = responseBodyCopy.string()
//            val jsonResponse = JSONObject(bodyResponse)
////            if (jsonResponse.get("response_code").equals("300")) {
////                EventBus.getDefault().post(LogoutModel(true))
////            }
//            response
//        }


        if (!::INSTANCE.isInitialized) {

            val client = OkHttpClient.Builder().apply {
                callTimeout(MS_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                connectTimeout(MS_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                readTimeout(MS_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
                writeTimeout(MS_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
            }.build()

            val retrofit = Retrofit.Builder().apply {
                baseUrl(baseUrlFromJNI())
                client(client)
                client(httpClient.build())
                addConverterFactory(GsonConverterFactory.create())
            }.build()

            INSTANCE = retrofit.create(ApiInterface::class.java)

        }

        return INSTANCE

    }
}