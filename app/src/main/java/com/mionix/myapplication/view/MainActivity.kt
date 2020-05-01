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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //myViewModel.getMovie()
        initView()

    }



    private fun initView() {
        val mytabsAccessorAdapter = TabsAccessorAdapter(supportFragmentManager)
        val mainTabs : TabLayout = findViewById(R.id.main_tabs)
        val homeViewPager: ViewPager = findViewById(R.id.homeViewPager)
        setSupportActionBar(main_toolbar as Toolbar)
        supportActionBar?.title = "MovieDB"
        homeViewPager.adapter = mytabsAccessorAdapter

        //when we go back the main activity  we will back old position
        homeViewPager.currentItem = homeViewPager.currentItem

        mainTabs.setupWithViewPager(homeViewPager)
        val icon = intArrayOf(R.drawable.home,
            R.drawable.library,
            R.drawable.theatres,
            R.drawable.account)
        val iconChoosed = intArrayOf(R.drawable.homerun,
            R.drawable.libraryrun,
            R.drawable.theatresrun,
            R.drawable.accountrun)
        //Get reference to your Tablayout
        mainTabs.getTabAt(0)?.setIcon(iconChoosed[0])
        mainTabs.getTabAt(1)?.setIcon(icon[1])
        mainTabs.getTabAt(2)?.setIcon(icon[2])
        mainTabs.getTabAt(3)?.setIcon(icon[3])
        mainTabs.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        mainTabs.getTabAt(0)?.setIcon(icon[0])
                    }
                    1 ->{
                        mainTabs.getTabAt(1)?.setIcon(icon[1])
                    }
                    2 ->{
                        mainTabs.getTabAt(2)?.setIcon(icon[2])
                    }
                    3 ->{
                        mainTabs.getTabAt(3)?.setIcon(icon[3])
                    }
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        mainTabs.getTabAt(0)?.setIcon(iconChoosed[0])
                    }
                    1 -> {
                        mainTabs.getTabAt(1)?.setIcon(iconChoosed[1])
                    }
                    2 -> {
                        mainTabs.getTabAt(2)?.setIcon(iconChoosed[2])
                    }
                    3 -> {
                        mainTabs.getTabAt(3)?.setIcon(iconChoosed[3])
                    }
                }
            }

        })
    }

}
