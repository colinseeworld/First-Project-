package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.jsone3_categoryfood.R
import kotlinx.android.synthetic.main.activity_empty_cart.*

class EmptyCartActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_cart)

        init()
    }

    private fun init(){
        go_shopping.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            go_shopping -> startActivity(Intent(this,CategoryActivity::class.java))
        }
    }
}