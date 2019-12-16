package com.bguneser.springandrecyclerview.ui.repository

import com.bguneser.springandrecyclerview.ui.model.Article
import com.bguneser.springandrecyclerview.ui.model.ArticleRequest
import com.bguneser.springandrecyclerview.ui.model.EmptyResponse
import retrofit2.Call
import retrofit2.http.*

interface ArticleApi{

    @GET("articles")
    fun getArticles() : Call<List<Article>>

    @POST("articles")
    fun postArticle(@Body body: ArticleRequest) : Call<Article>

    @DELETE("articles/{id}")
    fun deleteArticle(@Path("id") gistId : Long) : Call<EmptyResponse>


}