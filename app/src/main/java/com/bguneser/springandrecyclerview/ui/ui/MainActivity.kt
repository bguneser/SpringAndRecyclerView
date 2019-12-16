package com.bguneser.springandrecyclerview.ui.ui

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bguneser.springandrecyclerview.R
import com.bguneser.springandrecyclerview.ui.ui.articles.ArticleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val articlesFragment = ArticleFragment()


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_articles -> articlesFragment
                else ->  ArticleFragment()
            }
            switchToFragment(fragment)
            true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        switchToFragment(articlesFragment)
        checkConnectivity()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun switchToFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment).commit()
    }

    private fun checkConnectivity(){
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork !=null && activeNetwork.isConnectedOrConnecting

        if (!isConnected){
            Toast.makeText(this,"Check network connection", Toast.LENGTH_SHORT).show()
        }
    }
}
