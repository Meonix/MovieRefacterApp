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
import com.mionix.myapplication.model.Result
class TopRateMovieAdapter(private val topRateMovieList: MutableList<Result>,
                          val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<TopRateMovieAdapter.ViewHolder>(){
    private var data :MutableList<Result> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_main,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return topRateMovieList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val topRateMovieList= data[position]
        val moviePosterURL = POSTER_BASE_URL + topRateMovieList.posterPath

        //load image form https url into view holder (see build gradle)
            Glide.with(holder.ivPopularMovie) //1
                .load(moviePosterURL)
                .into(holder.ivPopularMovie)
        //
        holder.bind(topRateMovieList,itemClickListener)


    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val ivPopularMovie =itemView.findViewById(R.id.ivItemRecycleView) as ImageView


        fun bind(topRateMovieList: Result,clickListener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                clickListener.onItemClicked(topRateMovieList)
            }
        }
    }
    fun updateData(data: MutableList<Result>) {
        this.data = data
        notifyDataSetChanged()
    }
}