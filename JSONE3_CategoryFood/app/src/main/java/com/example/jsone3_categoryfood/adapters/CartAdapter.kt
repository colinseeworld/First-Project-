package com.example.jsone3_categoryfood.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.activities.CartActivity
import com.example.jsone3_categoryfood.database.DBHelper
import com.example.jsone3_categoryfood.models.CartItemsResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class CartAdapter(var mContext: Context, var cartList: MutableList<CartItemsResponse>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    var dbHelper = DBHelper(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter, parent, false)
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
            var url = "https://rjtmobile.com/grocery/images/" + cartItems.image
            Picasso.get().load(url).into(itemView.select_pro_img)
            itemView.select_pro_name.text = cartItems.name
            itemView.select_pro_quantity.text = "${cartItems.quantity}"
            itemView.select_pro_old_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.select_pro_old_price.text = "＄${cartItems.price}"

            itemView.select_pro_new_price.text = "＄${cartItems.mrp}"

            itemView.select_pro_delete.setOnClickListener{
                var itemName = cartItems.name
                var alertDialog = AlertDialog.Builder(mContext)
                    .setTitle("Warning")
                    .setMessage("Are you sure to delete: $itemName ?")
                    .setPositiveButton("Confirm") { dialog, which ->
                        if (CartActivity.dbHelper.deleteProduct(cartItems.id)) {
                            cartList.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemChanged(position, cartList.size)

                            (mContext as CartActivity).finish()
                            (mContext as CartActivity).overridePendingTransition(0, 0)
                            mContext.startActivity(Intent(mContext, CartActivity::class.java))
                            (mContext as CartActivity).overridePendingTransition(0, 0)

                        } else
                            Toast.makeText(mContext, "Error deleting", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel", { dialog, which -> })
                    .setIcon(R.drawable.ic_baseline_warning_amber_24)
                    .show()
            }

            itemView.btnPlus.setOnClickListener{
                dbHelper.increaseQuantity(cartItems.id,cartItems.quantity)

                (mContext as CartActivity).finish()
                (mContext as CartActivity).overridePendingTransition(0,0)
                mContext.startActivity(Intent(mContext,CartActivity::class.java))
                (mContext as CartActivity).overridePendingTransition(0,0)
            }

            itemView.btnMinus.setOnClickListener{
                dbHelper.decreaseQuantity(cartItems.id,cartItems.quantity)

                (mContext as CartActivity).finish()
                (mContext as CartActivity).overridePendingTransition(0,0)
                mContext.startActivity(Intent(mContext,CartActivity::class.java))
                (mContext as CartActivity).overridePendingTransition(0,0)
            }
        }
    }
}

