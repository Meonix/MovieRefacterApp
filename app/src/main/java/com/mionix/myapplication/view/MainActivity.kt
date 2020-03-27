package com.mionix.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.model.Result
import com.mionix.myapplication.view.adapter.OnItemClickListener
import com.mionix.myapplication.view.adapter.PopularMovieAdapter
import com.mionix.myapplication.view.adapter.TabsAccessorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(){
    private lateinit var mytabsAccessorAdapter: TabsAccessorAdapter
    private lateinit var main_tabs : TabLayout
    private lateinit var homeViewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //myViewModel.getMovie()
        initView()

    }



    private fun initView() {
        main_tabs = findViewById(R.id.main_tabs)
        setSupportActionBar(main_toolbar as Toolbar)
        supportActionBar?.title = "MovieDB"
        mytabsAccessorAdapter = TabsAccessorAdapter(supportFragmentManager)
        homeViewPager = findViewById(R.id.homeViewPager)
        homeViewPager.adapter = mytabsAccessorAdapter

        //when we go back the main activity  we will back old position
        homeViewPager.currentItem = homeViewPager.currentItem

        main_tabs.setupWithViewPager(homeViewPager)
        val icon = intArrayOf(R.drawable.home,
            R.drawable.library,
            R.drawable.theatres,
            R.drawable.account)
        val iconChoosed = intArrayOf(R.drawable.homerun,
            R.drawable.libraryrun,
            R.drawable.theatresrun,
            R.drawable.accountrun)
        //Get reference to your Tablayout
        main_tabs.getTabAt(0)?.setIcon(iconChoosed[0])
        main_tabs.getTabAt(1)?.setIcon(icon[1])
        main_tabs.getTabAt(2)?.setIcon(icon[2])
        main_tabs.getTabAt(3)?.setIcon(icon[3])
        main_tabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        main_tabs.getTabAt(0)?.setIcon(icon[0])
                    }
                    1 ->{
                        main_tabs.getTabAt(1)?.setIcon(icon[1])
                    }
                    2 ->{
                        main_tabs.getTabAt(2)?.setIcon(icon[2])
                    }
                    3 ->{
                        main_tabs.getTabAt(3)?.setIcon(icon[3])
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        main_tabs.getTabAt(0)?.setIcon(iconChoosed[0])
                    }
                    1 -> {
                        main_tabs.getTabAt(1)?.setIcon(iconChoosed[1])
                    }
                    2 -> {
                        main_tabs.getTabAt(2)?.setIcon(iconChoosed[2])
                    }
                    3 -> {
                        main_tabs.getTabAt(3)?.setIcon(iconChoosed[3])
                    }
                }
            }

        })
    }

}
