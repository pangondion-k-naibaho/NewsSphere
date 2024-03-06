package com.newssphere.client.view.activity.About

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.newssphere.client.R
import com.newssphere.client.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private val TAG = AboutActivity::class.java.simpleName
    private lateinit var binding: ActivityAboutBinding

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, AboutActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAbout)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_socialist)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}