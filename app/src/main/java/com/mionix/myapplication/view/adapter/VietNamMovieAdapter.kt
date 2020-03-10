package com.mionix.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.mionix.myapplication.model.Result
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL

class VietNamMovieAdapter (private val activity: Activity,
                           private val vietNamMovieList: MutableList<Result>,
                           val context: Context,
                           val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VietNamMovieAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview_main,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return vietNamMovieList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val vietNamMovieList= vietNamMovieList[position]
        val moviePosterURL = POSTER_BASE_URL + vietNamMovieList.posterPath

        //load image form https url into view holder (see build gradle)
        Glide.with(activity) //1
            .load(moviePosterURL)
            .into(holder.ivPopularMovie)
        //
        holder.bind(vietNamMovieList,itemClickListener)


    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val ivPopularMovie =itemView.findViewById(R.id.ivItemRecycleView) as ImageView


        fun bind(vietNamMovieList: Result,clickListener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                clickListener.onItemClicked(vietNamMovieList)
            }
        }
    }

}