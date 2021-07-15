package com.example.jsone3_categoryfood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.models.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_slide_image_adapter.view.*

class SlideImageAdapter(var mContext: Context, var imageList: ArrayList<Data>):
    RecyclerView.Adapter<SlideImageAdapter.ViewHolder>() {

    var listener: onAdapterListener? =null

    fun setOnAdapterListener(onAdapterListener: onAdapterListener) {
        listener = onAdapterListener
    }

    interface onAdapterListener{
        fun onItemClicked(data: Data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_slide_image_adapter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var slideImage = imageList[position]
        holder.bind(slideImage)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(data: Data){
            var imageUrl = "https://rjtmobile.com/grocery/images/" + data.catImage
            Picasso.get().load(imageUrl).into(itemView.iv_cover)
            itemView.setOnClickListener{
                listener?.onItemClicked(data)
            }
        }
    }
}