package com.example.jsone3_categoryfood.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_list_adapter.view.*

class ProductListAdapter(var mContext: Context, var data: List<Product>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var listener: ProductListAdapter.onAdapterListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_product_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        var categoryResponse = data[position]
        holder.bind(categoryResponse)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Product) {
            var url = "https://rjtmobile.com/grocery/images/" + data.image
            Picasso.get().load(url).into(itemView.iv_cover)

            itemView.tv_name.text = data.productName
            itemView.tv_quantity.text = "${data.unit}" //${data.quantity}
            itemView.tv_old_price.text = "＄${data.mrp}"
            itemView.tv_old_price.paint.flags = Paint. STRIKE_THRU_TEXT_FLAG
            itemView.tv_new_price.text = "＄${data.price}"

            itemView.setOnClickListener {
                listener?.onItemClicked(data)
            }
        }
    }

    fun setOnAdapterListener(onAdapterListener: ProductListAdapter.onAdapterListener) {
        listener = onAdapterListener
    }

    interface onAdapterListener {
        fun onItemClicked(data: Product)
    }
}