package com.example.jsone3_categoryfood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.models.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*
import kotlin.collections.ArrayList

class CategoryAdapter(var mContext: Context, var mList: ArrayList<Data>):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var listener: onAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var categoryResponse = mList[position]
        holder.bind(categoryResponse)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(data:Data){
            var url ="https://rjtmobile.com/grocery/images/"+data.catImage
            Picasso.get().load(url).into(itemView.image_view)

            itemView.text_view_name.text = data.catName

            itemView.setOnClickListener{
                listener?.onItemClicked(data)
            }
        }
    }

    fun setOnAdapterListener(onAdapterListener: onAdapterListener){
        listener = onAdapterListener
    }
    interface onAdapterListener{
        fun onItemClicked(data: Data)
    }
}