package com.example.jsone3_categoryfood.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.models.Address
import kotlinx.android.synthetic.main.row_address_adapter.view.*

class AddressAdapter(var mContext: Context, var data: List<Address>) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    var selectId: String = ""
    var listener: onAdapterListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressAdapter.ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_address_adapter, parent, false)
        val viewHolder = ViewHolder(view)

        view.iv_select.setOnClickListener {
            selectId = if (selectId != data[viewHolder.adapterPosition]._id) {
                data[viewHolder.adapterPosition]._id
            } else {
                ""
            }
            notifyDataSetChanged()
        }
        viewHolder.itemView.setOnClickListener {
            listener?.onItemClicked(data[viewHolder.adapterPosition])

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
        if (data[position]._id == selectId) {
            holder.itemView.iv_select.setImageResource(R.mipmap.ic_select)
        } else {
            holder.itemView.iv_select.setImageResource(R.mipmap.ic_not_select)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(d: List<Address>) {
        data = d
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Address) {
            itemView.address_street.text = data.streetName
            itemView.address_city.text = data.city
            itemView.address_zip_code.text = "${data.pincode}"
            itemView.address_type.text = data.type
            itemView.address_house_no.text = data.houseNo
        }
    }
    interface onAdapterListener{
        fun onItemClicked(data: Address)
    }

}