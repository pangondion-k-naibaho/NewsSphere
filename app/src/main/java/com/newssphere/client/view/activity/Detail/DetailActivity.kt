package com.newssphere.client.view.activity.Detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.newssphere.client.R
import com.newssphere.client.databinding.ActivityDetailBinding
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.ALL
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.BUSINESS
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.ENTERTAINMENT
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.GENERAL
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.HEALTH
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.SCIENCE
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.SPORTS
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.TECHNOLOGY
import com.newssphere.client.model.data_class.Article
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var binding: ActivityDetailBinding
    private var retrievedArticle: Article?= null
    private var retrievedCategory: String = ""

    companion object{
        private const val EXTRA_ARTICLE = "EXTRA_ARTICLE"
        private const val EXTRA_CATEGORY = "EXTRA_CATEGORY"
        fun newIntent(context: Context, deliveredCategory: String, deliveredArticle: Article): Intent = Intent(context, DetailActivity::class.java)
            .putExtra(EXTRA_CATEGORY, deliveredCategory)
            .putExtra(EXTRA_ARTICLE, deliveredArticle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrievedArticle = intent.extras!!.get(EXTRA_ARTICLE) as Article
        retrievedCategory = intent.extras!!.getString(EXTRA_CATEGORY) as String

        Log.d(TAG, "retrievedArticle: $retrievedArticle")

        setUpView()
    }

    private fun setUpView(){
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(retrievedArticle!!.urlToImage)
                .into(ivNewsBackground)

            ivBack.setOnClickListener{
                finish()
            }

//            tvCategory.apply {
//                text = retrievedCategory
//                background = when(retrievedCategory){
//                    ALL -> {
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_yellow)
//                    }
//                    BUSINESS -> {
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_black)
//                    }
//                    ENTERTAINMENT ->{
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_grey)
//                    }
//                    GENERAL ->{
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_blue)
//                    }
//                    HEALTH ->{
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_green)
//                    }
//                    SCIENCE ->{
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_red)
//                    }
//                    SPORTS ->{
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_purple)
//                    }
//                    else ->{
//                        ContextCompat.getDrawable(this@DetailActivity, R.drawable.bg_ribbon_orange)
//                    }
//                }
//            }

            tvNewsTitle.text = retrievedArticle!!.title
            tvNewsDescription.text = retrievedArticle!!.description
            tvNewsContent.text = retrievedArticle!!.content
            tvNewsPublishedBy.apply {
                if(retrievedArticle!!.source == null){
                    visibility = View.GONE
                }else{
                    visibility = View.VISIBLE
                    text = String.format(getString(R.string.tvDummy_NewsPublishedBy, retrievedArticle!!.source!!.name))
                }
            }

            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(retrievedArticle!!.publishedAt!!)
            val formattedDate = outputFormat.format(date!!)

            tvNewsPublishedAt.text = formattedDate

        }
    }
}