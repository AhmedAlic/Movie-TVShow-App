package  com.myapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

object TVShowRepository {

    private val api: API

    init  {
        val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        api = retrofit.create(API::class.java)
    }

    fun getTopRatedTVShows (
        onSuccess: (shows: List<TVShow>) -> Unit,
        onError: () -> Unit ) {
        api.getTopRatedTVShows().enqueue(object: Callback<GetTVShows> {
            override fun onResponse(
                call : Call<GetTVShows>,
                response: Response<GetTVShows>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if(responseBody != null) {
                        onSuccess.invoke(responseBody.shows)
                    } else {
                        onError.invoke()
                    }
                } else {
                    onError.invoke()
                }
            }
            override fun onFailure(call: Call<GetTVShows>, t: Throwable) {
                onError.invoke()
            }
        })
    }

    fun searchTVShows (
        query: String,
        onSuccess: (shows: List<TVShow>) -> Unit,
        onError: () -> Unit ) {
        api.searchTVShows(query = query).enqueue(object: Callback<GetTVShows> {
            override fun onResponse(
                call : Call<GetTVShows>,
                response: Response<GetTVShows>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    if(responseBody != null) {
                        onSuccess.invoke(responseBody.shows)
                    } else {
                        onError.invoke()
                    }
                } else {
                    onError.invoke()
                }
            }
            override fun onFailure(call: Call<GetTVShows>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}