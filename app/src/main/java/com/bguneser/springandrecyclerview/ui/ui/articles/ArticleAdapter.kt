package com.bguneser.springandrecyclerview.ui.ui.articles

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bguneser.springandrecyclerview.R
import com.bguneser.springandrecyclerview.ui.app.inflate
import com.bguneser.springandrecyclerview.ui.model.Article
import kotlinx.android.synthetic.main.list_item_article.view.*

class ArticleAdapter(private val articles: MutableList<Article>,private val listener: ArticleAdapterListener)
    : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() ,ItemTouchHelperListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_article))
    }

    fun addArticle(gist: Article) {
        this.articles.add(0,gist)
        notifyItemInserted(0)
    }

    fun deleteArticle ( article: Article) {
        val updatedArticles = mutableListOf<Article>()
        updatedArticles.addAll(articles)
        updatedArticles.remove(article)

        val diffCallback = ArticleDiffCallback(this.articles,updatedArticles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.articles.clear()
        this.articles.addAll(updatedArticles)
        diffResult.dispatchUpdatesTo(this)

    }

    override fun getItemCount() = articles.size

    fun updateArticles(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var article: Article

        fun bind(article: Article) {
            this.article = article
            itemView.articleName.text = article.title
        }
    }

    interface ArticleAdapterListener {
        fun deleteArticle(article: Article)
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {

        listener.deleteArticle(articles[position])
    }


}

