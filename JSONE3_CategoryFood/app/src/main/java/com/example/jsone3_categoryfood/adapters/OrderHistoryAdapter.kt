package com.example.jsone3_categoryfood.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.models.OrderData
import kotlinx.android.synthetic.main.row_order_history_adapter.view.*

class OrderHistoryAdapter(var mContext: Context, var orderList:List<OrderData>):
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_history_adapter,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var orderHistoryResponse = orderList[position]
        holder.bind(orderHistoryResponse)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(data: OrderData) {
            itemView.date.text = data.date
            itemView.order_summary.text = data.orderSummary?.orderAmount.toString()
            itemView.payment_id.text = data.payment?._id.toString()
            itemView.payment_method.text = data.payment?.paymentMode.toString()
        }
    }
}