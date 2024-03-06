package com.newssphere.client.view.activity.Home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MenuItem.OnMenuItemClickListener
import android.view.View
import android.view.View.OnClickListener
import android.widget.PopupMenu
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
import com.newssphere.client.view.activity.About.AboutActivity

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
        binding.apply{
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

            btnMore.setOnClickListener(object: OnClickListener {
                override fun onClick(v: View?) {
                    val popUpMenu = PopupMenu(this@HomeActivity, v!!)
                    popUpMenu.menuInflater.inflate(R.menu.home_menu, popUpMenu.menu)

                    popUpMenu.setOnMenuItemClickListener(object: PopupMenu.OnMenuItemClickListener{
                        override fun onMenuItemClick(item: MenuItem?): Boolean {
                            if(item!!.itemId == R.id.menuAbout){
                                startActivity(
                                    AboutActivity.newIntent(this@HomeActivity)
                                )
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                            }
                            return true
                        }

                    })

                    popUpMenu.show()
                }
            })
        }
    }

    override fun startLoading() {
        binding.pbHome.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        binding.pbHome.visibility = View.GONE
    }
}