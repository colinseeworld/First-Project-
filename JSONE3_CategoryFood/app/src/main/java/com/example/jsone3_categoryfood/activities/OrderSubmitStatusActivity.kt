package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.jsone3_categoryfood.R

class OrderSubmitStatusActivity : BaseActivity(R.layout.layout_order_submit_status) {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.tv_home -> {
                finish()
            }
            R.id.button_order_history_page -> {
                startActivity(Intent(this,OrderHistoryActivity::class.java))
            }
        }
    }
}