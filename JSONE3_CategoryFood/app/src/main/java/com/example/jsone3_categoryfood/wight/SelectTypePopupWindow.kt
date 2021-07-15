package com.example.jsone3_categoryfood.wight

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.example.jsone3_categoryfood.R

class SelectTypePopupWindow(val mContext: Activity, width: Int) : PopupWindow() {
    private var rootView: View =
        LayoutInflater.from(mContext).inflate(R.layout.popup_select_type, null)
    private var onClickListener:OnClickListener?=null
    init {
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.width = width

        isOutsideTouchable = true
        this.contentView = rootView

        rootView.findViewById<TextView>(R.id.tv_1).setOnClickListener {
            onClickListener?.typeName((it as TextView).text.toString())
            dismiss()
        }
        rootView.findViewById<TextView>(R.id.tv_2).setOnClickListener {
            onClickListener?.typeName((it as TextView).text.toString())
            dismiss()
        }
        rootView.findViewById<TextView>(R.id.tv_3).setOnClickListener {
            onClickListener?.typeName((it as TextView).text.toString())
            dismiss()
        }
    }

    private fun backgroundAlpha(bgAlpha: Float) {
        mContext.window.attributes?.apply {
            alpha = bgAlpha
            mContext.window.attributes = this
        }
    }
    interface OnClickListener{
        fun typeName(data: String)
    }
    fun setOnClickListener(listener: OnClickListener){
        onClickListener = listener
    }

    fun show(view: View) {
        backgroundAlpha(0.8F)
        showAsDropDown(view)
    }

    override fun dismiss() {
        backgroundAlpha(1F)
        super.dismiss()
    }
}