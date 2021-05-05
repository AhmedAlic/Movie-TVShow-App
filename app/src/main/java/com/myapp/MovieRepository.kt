package com.myapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {

    private val api: API

    init  {
        val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        api = retrofit.create(API::class.java)
    }

    fun getTopRatedMovies (
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit ) {
        api.getTopRatedMovies().enqueue(object: Callback<GetMovies> {
            override fun onResponse(
                call : Call<GetMovies>,
                response: Response<GetMovies>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if(responseBody != null) {
                        onSuccess.invoke(responseBody.movies)
                    } else {
                        onError.invoke()
                    }
                } else {
                    onError.invoke()
                }
            }
            override fun onFailure(call: Call<GetMovies>, t: Throwable) {
                onError.invoke()
            }
        })
    }

    fun searchMovies (
        query: String,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit ) {
        api.searchMovies(query = query).enqueue(object: Callback<GetMovies> {
            override fun onResponse(
                call : Call<GetMovies>,
                response: Response<GetMovies>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if(responseBody != null) {
                        onSuccess.invoke(responseBody.movies)
                    } else {
                        onError.invoke()
                    }
                } else {
                    onError.invoke()
                }
            }
            override fun onFailure(call: Call<GetMovies>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}