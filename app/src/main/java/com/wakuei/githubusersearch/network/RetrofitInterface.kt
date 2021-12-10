package com.wakuei.githubusersearch.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    @GET("search/users")
    fun searchUsers(
        @Query("q") q: String?,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<SearchUserResponse>


    companion object {
        var retrofitInterface: RetrofitInterface? = null

        fun getInStance(): RetrofitInterface {
            if (retrofitInterface == null) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient = OkHttpClient().newBuilder().addInterceptor(logging).build()
                val retrofit = Retrofit.Builder().baseUrl(Config.URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
                retrofitInterface = retrofit.create(RetrofitInterface::class.java)
            }
            return retrofitInterface!!
        }
    }
}