package com.mionix.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.localDataBase.FavouritesTable
import com.mionix.myapplication.model.LocalSavedMovie

class FavouriesMovieAdapter (private val watchMovieList: MutableList<FavouritesTable>
) : RecyclerView.Adapter<FavouriesMovieAdapter.ViewHolder>(){
    private var data :MutableList<FavouritesTable> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_main,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return watchMovieList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val watchMovieList= data[position]
        val castPosterURL = POSTER_BASE_URL + watchMovieList.colPoster

        //load image form https url into view holder (see build gradle)
            Glide.with(holder.ivItemRecycleView) //1
                .load(castPosterURL)
                .into(holder.ivItemRecycleView)
        //
        holder.bind(watchMovieList)


    }
    fun updateData(data: MutableList<FavouritesTable>) {
        this.data = data
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivItemRecycleView = itemView.findViewById(R.id.ivItemRecycleView) as ImageView


        fun bind(watchMovieList: FavouritesTable)
        {
//            itemView.setOnClickListener {
//                clickListener.onItemClicked(listPopularMovie)
//            }
        }
    }

}