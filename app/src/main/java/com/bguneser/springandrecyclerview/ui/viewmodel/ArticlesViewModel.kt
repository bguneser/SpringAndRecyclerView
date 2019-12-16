package com.bguneser.springandrecyclerview.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bguneser.springandrecyclerview.ui.app.Injection
import com.bguneser.springandrecyclerview.ui.model.Article
import com.bguneser.springandrecyclerview.ui.model.ArticleRequest
import com.bguneser.springandrecyclerview.ui.model.Either

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Injection.provideRepository()
    private val allRepos = repository.getArticles()

    fun getArticles() = allRepos
    fun sendArticle(title:String , author:String,content: String): LiveData<Either<Article>> {
        val request = ArticleRequest(title,content)
        return repository.postArticle(request)
    }

    fun deleteArticle(article : Article) = repository.deleteArticle(article.id)
}