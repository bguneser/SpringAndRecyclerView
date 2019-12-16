package com.bguneser.springandrecyclerview.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bguneser.springandrecyclerview.ui.app.Injection
import com.bguneser.springandrecyclerview.ui.model.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RemoteRepository : Repository {

    private const val TAG = "RemoteRepository"

    private val api = Injection.provideArticleApi()


    override fun getArticles(): LiveData<Either<List<Article>>> {

        val liveData = MutableLiveData<Either<List<Article>>>()


            api.getArticles().enqueue(object : Callback<List<Article>> {

                override fun onResponse(call: Call<List<Article>>?, response: Response<List<Article>>?) {

                    if (response != null) {
                        liveData.value = Either.success(response.body())
                    } else {

                        liveData.value = Either.error(ApiError.ARTICLES, null)
                    }

                }
                override fun onFailure(call: Call<List<Article>>?, t: Throwable?) {

                    liveData.value = Either.error(ApiError.ARTICLES, null)

                }


            })

        return liveData
    }

    override fun postArticle(request: ArticleRequest): LiveData<Either<Article>> {

        val liveData = MutableLiveData<Either<Article>>()

        api.postArticle(request).enqueue(object : Callback<Article>{
            override fun onResponse(call: Call<Article>, response: Response<Article>) {

                if(response!=null && response.isSuccessful) {
                    liveData.value=Either.success(response.body())
                }else {

                    liveData.value=Either.error(ApiError.ARTICLES,null)
                }


            }

            override fun onFailure(call: Call<Article>, t: Throwable) {

                liveData.value=Either.error(ApiError.POST_ARTICLE,null)

            }



        })
        return liveData
    }

    override fun deleteArticle(articleId: Long): LiveData<Either<EmptyResponse>> {


        val liveData = MutableLiveData<Either<EmptyResponse>>()

        api.deleteArticle(articleId).enqueue(object : Callback<EmptyResponse>{

            override fun onResponse(call: Call<EmptyResponse>, response: Response<EmptyResponse>) {

                if(response!=null && response.isSuccessful){
                    liveData.value=Either.success(response.body())
                }else {
                    liveData.value = Either.error(ApiError.DELETE_GIST,null)
                }
            }
            override fun onFailure(call: Call<EmptyResponse>, t: Throwable) {
                liveData.value = Either.error(ApiError.DELETE_GIST,null)
            }
        })

        return liveData
    }


}