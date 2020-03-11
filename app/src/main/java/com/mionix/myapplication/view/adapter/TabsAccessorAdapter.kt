package com.mionix.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mionix.myapplication.view.fragment.AccountFragment
import com.mionix.myapplication.view.fragment.HomeFragment
import com.mionix.myapplication.view.fragment.InTheatresFragment
import com.mionix.myapplication.view.fragment.LibraryFragment

class TabsAccessorAdapter(fm: FragmentManager,context:Context,activity: Activity) : FragmentPagerAdapter(fm) {
    private var homeFragmentcontext = context
    private var homeFragmentactivity = activity
    override fun getItem(i: Int): Fragment {
        when (i) {
            0 -> {
                return HomeFragment(homeFragmentcontext,homeFragmentactivity)
            }
            1 -> {
                return LibraryFragment(homeFragmentcontext,homeFragmentactivity)
            }
            2 -> {
                return InTheatresFragment(homeFragmentcontext,homeFragmentactivity)
            }
            3 -> {
                return AccountFragment()
            }
            else -> return HomeFragment(homeFragmentcontext,homeFragmentactivity)
        }
    }

    override fun getCount(): Int {
        return 4
    }

    //    @Nullable
    //    @Override
    //    public CharSequence getPageTitle(int position) {
    //        switch(position)
    //        {
    //            case 0 :
    //                return "Friends Chat";
    //            case 1 :
    //                return "Groups Chat";
    //            case 2:
    //                return  "Contacts";
    //            case 3:
    //                return  "Requests";
    //            default:
    //                return null;
    //        }
    //    }
}