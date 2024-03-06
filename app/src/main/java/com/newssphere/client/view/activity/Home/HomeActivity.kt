package com.newssphere.client.view.activity.Home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.newssphere.client.R
import com.newssphere.client.databinding.ActivityHomeBinding
import com.newssphere.client.model.Constants
import com.newssphere.client.viewmodel.HomeViewModel
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.CATEGORY_ENUM

class HomeActivity : AppCompatActivity(), CategoriesHomeCommunicator {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = HomeActivity::class.java.simpleName
    private val homeViewModel by viewModels<HomeViewModel>()
    private var selectedFragment: Fragment?= null

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        initView()
        setUpView()
    }

    private fun initView(){
        homeViewModel.getNewsCollection(page = 1)
        homeViewModel.newsCollection.observe(this@HomeActivity, {collectionNews ->
            Log.d(TAG, "News Collection : ${collectionNews.articles}")
        })
    }

    private fun setUpView(){
        with(binding){
            val newsCategoryAdapter = CategoryFragmentAdapter(this@HomeActivity)
            vpCategoryNews.apply {
                adapter = newsCategoryAdapter
                registerOnPageChangeCallback(object: OnPageChangeCallback(){
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        selectedFragment = supportFragmentManager.findFragmentByTag("f$position")
                    }
                })
            }

            val categoryTitleArray: Array<String> = CATEGORY_ENUM.values().map { it.name.toLowerCase().capitalize() }.toTypedArray()

            TabLayoutMediator(tlCategoryNews, vpCategoryNews, false, true){tab, position ->
                tab.text = categoryTitleArray[position]
            }.attach()
        }
    }

    override fun startLoading() {
        binding.pbHome.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        binding.pbHome.visibility = View.GONE
    }
}