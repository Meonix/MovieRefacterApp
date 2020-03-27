package com.mionix.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mionix.myapplication.R
import com.mionix.myapplication.view.fragment.AccountFragment
import com.mionix.myapplication.view.fragment.HomeFragment
import com.mionix.myapplication.view.fragment.InTheatresFragment
import com.mionix.myapplication.view.fragment.LibraryFragment

class TabsAccessorAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(i: Int): Fragment {
        when (i) {
            0 -> {
                return HomeFragment()
            }
            1 -> {
                return LibraryFragment()
            }
            2 -> {
                return InTheatresFragment()
            }
            3 -> {
                return AccountFragment()
            }
            else -> return HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageWidth(position: Int): Float {
        return super.getPageWidth(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Home"
            }
            1->{
                "Library"
            }
            2 ->{
                "In Theatres"
            }
            else -> "Account"
        }
    }



}