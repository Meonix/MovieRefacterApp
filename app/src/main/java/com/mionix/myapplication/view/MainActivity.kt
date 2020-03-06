package com.mionix.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mionix.myapplication.R
import com.mionix.myapplication.view.adapter.TabsAccessorAdapter
import com.mionix.myapplication.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    //val myViewModel : MainViewModel by viewModel()
    private var mToolbar: Toolbar? = null
    private var myViewPage: ViewPager? = null
    private var myTablayout: TabLayout? = null
    private var mytabsAccessorAdapter: TabsAccessorAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //myViewModel.getMovie()
        initView()
    }

    private fun initView() {
        mToolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.title = "MovieDB"
        myViewPage = findViewById(R.id.homeViewPager)
        mytabsAccessorAdapter = TabsAccessorAdapter(supportFragmentManager)
        myViewPage!!.adapter = mytabsAccessorAdapter

        //when we go back the main activity  we will back old position
        myViewPage!!.currentItem = myViewPage!!.currentItem

        myTablayout = findViewById(R.id.main_tabs)
        myTablayout!!.setupWithViewPager(myViewPage)
        val icon = intArrayOf(R.drawable.home,
            R.drawable.library,
            R.drawable.theatres,
            R.drawable.account)
        val iconChoosed = intArrayOf(R.drawable.homerun,
            R.drawable.libraryrun,
            R.drawable.theatresrun,
            R.drawable.accountrun)
        //Get reference to your Tablayout
        myTablayout!!.getTabAt(0)!!.setIcon(iconChoosed[0])
        myTablayout!!.getTabAt(0)!!.text =getString(R.string.titleFragmentHome)
        myTablayout!!.getTabAt(1)!!.setIcon(icon[1])
        myTablayout!!.getTabAt(1)!!.text =getString(R.string.libraryTitleFragment)
        myTablayout!!.getTabAt(2)!!.setIcon(icon[2])
        myTablayout!!.getTabAt(2)!!.text =getString(R.string.theatresTitleFragment)
        myTablayout!!.getTabAt(3)!!.setIcon(icon[3])
        myTablayout!!.getTabAt(3)!!.text =getString(R.string.accountTitleFragment)
        myTablayout!!.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> {
                        myTablayout!!.getTabAt(0)!!.setIcon(icon[0])
                    }
                    1 ->{
                        myTablayout!!.getTabAt(1)!!.setIcon(icon[1])
                    }
                    2 ->{
                        myTablayout!!.getTabAt(2)!!.setIcon(icon[2])
                    }
                    3 ->{
                        myTablayout!!.getTabAt(3)!!.setIcon(icon[3])
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> {
                        myTablayout!!.getTabAt(0)!!.setIcon(iconChoosed[0])
                    }
                    1 -> {
                        myTablayout!!.getTabAt(1)!!.setIcon(iconChoosed[1])
                    }
                    2 -> {
                        myTablayout!!.getTabAt(2)!!.setIcon(iconChoosed[2])
                    }
                    3 -> {
                        myTablayout!!.getTabAt(3)!!.setIcon(iconChoosed[3])
                    }
                }
            }

        })
    }
}
