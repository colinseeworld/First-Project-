package com.example.jsone3_categoryfood.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.database.DBHelper
import com.example.jsone3_categoryfood.models.CartItemsResponse
import kotlinx.android.synthetic.main.row_cart_adapter2.view.*

class CartAdapter2(var mContext: Context, var cartList: MutableList<CartItemsResponse>) :
    RecyclerView.Adapter<CartAdapter2.MyViewHolder>() {

    var dbHelper = DBHelper(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter2, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var cartItems = cartList[position]
        holder.bind(cartItems)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cartItems: CartItemsResponse) {

            itemView.tv_name.text = "${cartItems.name}"
            itemView.tv_quantity.text = "${cartItems.quantity}"
            itemView.tv_old_price.text = "＄${cartItems.price}"
            itemView.tv_old_price.paint.flags = Paint. STRIKE_THRU_TEXT_FLAG
            itemView.tv_new_price.text = "＄${cartItems.mrp}"
        }
    }
}

