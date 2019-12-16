package com.bguneser.springandrecyclerview.ui.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.bguneser.springandrecyclerview.R
import com.bguneser.springandrecyclerview.ui.model.*
import com.bguneser.springandrecyclerview.ui.viewmodel.ArticlesViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() , ArticleAdapter.ArticleAdapterListener {

    private lateinit var articlesViewModel: ArticlesViewModel

    private val adapter = ArticleAdapter(mutableListOf(),this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_article, container, false)

        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel::class.java)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesRecyclerView.layoutManager = LinearLayoutManager(context)
        articlesRecyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(articlesRecyclerView)

        articlesViewModel.getArticles().observe(this, Observer<Either<List<Article>>> { either ->
            if (either?.status == Status.SUCCESS && either.data != null) {
                adapter.updateArticles(either.data)
            } else {

                if (either?.error == ApiError.ARTICLES) {
                    Toast.makeText(
                        context,
                        getString(R.string.error_retrieving_articles),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        })

        fab.setOnClickListener {
            showArticleDialog()
        }
    }

    internal fun sendArticle(description: String, filename: String, content: String) {
        articlesViewModel.sendArticle(description,filename,content).observe(this,Observer<Either<Article>>{ either->

            if(either?.status==Status.SUCCESS && either.data!=null) {

                adapter.addArticle(either.data)

            } else {

                if(either?.error == ApiError.POST_ARTICLE) {

                    Toast.makeText(context,getString(R.string.error_posting_article),Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun deleteArticle(article: Article) {

        articlesViewModel.deleteArticle(article).observe(this, Observer<Either<EmptyResponse>>{ either ->

            if(either?.status==Status.SUCCESS){

                adapter.deleteArticle(article)
            } else{

                if(either?.error == ApiError.DELETE_ARTICLE){

                    Toast.makeText(context,getString(R.string.error_deleting_article),Toast.LENGTH_SHORT).show()
                }

            }

        })
    }


}