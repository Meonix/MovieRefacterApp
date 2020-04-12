package com.mionix.myapplication.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mionix.myapplication.R
import com.mionix.myapplication.api.POSTER_BASE_URL
import com.mionix.myapplication.model.Cast

class CastAdapter (private val activity: Activity,
                   private val castList: MutableList<Cast>,
                   val context: Context
) : RecyclerView.Adapter<CastAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cast_recycleview,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val castList= castList[position]
        val castPosterURL = POSTER_BASE_URL + castList.profilePath

        //load image form https url into view holder (see build gradle)
        Glide.with(context) //1
            .load(castPosterURL)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.ivCast)
        //
        holder.bind(castList)


    }
    fun update(){
            this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val ivCast = itemView.findViewById(R.id.ivItemCast) as ImageView
        val tvNameCast = itemView.findViewById(R.id.tvNameCast) as TextView


        fun bind(castList: Cast)
        {
            tvNameCast.text = castList.name
//            itemView.setOnClickListener {
//                clickListener.onItemClicked(listPopularMovie)
//            }
        }
    }

}