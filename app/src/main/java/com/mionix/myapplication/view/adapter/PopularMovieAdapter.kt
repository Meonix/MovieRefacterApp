package com.mionix.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import com.mionix.myapplication.model.Result
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import okhttp3.OkHttpClient

class PopularMovieAdapter(private val activity:Activity,
                          private val popularMovieList: MutableList<Result>,
                          val context: Context,
                          val itemClickListener: OnItemClickListener
) :RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_main,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return popularMovieList.size
    }

        override fun getItemId(position: Int): Long {
            return super.getItemId(position)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val listPopularMovie= popularMovieList[position]
        val moviePosterURL = POSTER_BASE_URL + listPopularMovie.posterPath

        //load image form https url into view holder (see build gradle)
        Glide.with(activity) //1
            .load(moviePosterURL)
            .into(holder.ivPopularMovie)
        //
            holder.bind(listPopularMovie,itemClickListener)


    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
            val ivPopularMovie =itemView.findViewById(R.id.ivItemRecycleView) as ImageView


        fun bind(listPopularMovie: Result,clickListener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                clickListener.onItemClicked(listPopularMovie)
            }
        }
    }

}

interface OnItemClickListener{
    fun onItemClicked(listPopularMovie: Result)
}
