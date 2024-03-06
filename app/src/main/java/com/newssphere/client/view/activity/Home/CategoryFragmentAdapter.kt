package com.newssphere.client.view.activity.Home

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.ALL
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.BUSINESS
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.ENTERTAINMENT
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.GENERAL
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.HEALTH
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.SCIENCE
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.SPORTS
import com.newssphere.client.model.Constants.COUNTRY_CONSTANTS.CATEGORY_CONSTANTS.Companion.TECHNOLOGY
import com.newssphere.client.view.activity.Home.Fragment.AllFragment
import com.newssphere.client.view.activity.Home.Fragment.BusinessFragment
import com.newssphere.client.view.activity.Home.Fragment.EntertainmentFragment
import com.newssphere.client.view.activity.Home.Fragment.GeneralFragment
import com.newssphere.client.view.activity.Home.Fragment.HealthFragment
import com.newssphere.client.view.activity.Home.Fragment.ScienceFragment
import com.newssphere.client.view.activity.Home.Fragment.SportsFragment
import com.newssphere.client.view.activity.Home.Fragment.TechnologyFragment

class CategoryFragmentAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 8
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment?= null
        when(position){
//            0 -> fragment = AllFragment.newInstance(ALL)
//            1 -> fragment = EntertainmentFragment.newInstance(ENTERTAINMENT)
//            2 -> fragment = HealthFragment.newInstance(HEALTH)
//            3 -> fragment = SportsFragment.newInstance(SPORTS)
//            4 -> fragment = TechnologyFragment.newInstance(TECHNOLOGY)
            0 -> fragment = AllFragment.newInstance(ALL)
            1 -> fragment = BusinessFragment.newInstance(BUSINESS)
            2 -> fragment = EntertainmentFragment.newInstance(ENTERTAINMENT)
            3 -> fragment = GeneralFragment.newInstance(GENERAL)
            4 -> fragment = HealthFragment.newInstance(HEALTH)
            5 -> fragment = ScienceFragment.newInstance(SCIENCE)
            6 -> fragment = SportsFragment.newInstance(SPORTS)
            7 -> fragment = TechnologyFragment.newInstance(TECHNOLOGY)
        }
        return fragment as Fragment
    }

}