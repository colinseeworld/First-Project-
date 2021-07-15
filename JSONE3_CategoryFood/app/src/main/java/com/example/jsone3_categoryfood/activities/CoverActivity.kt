package com.example.jsone3_categoryfood.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.jsone3_categoryfood.R
import kotlinx.android.synthetic.main.activity_cover.*


class CoverActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover)

        init()
    }

    private fun init(){
        button_start.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            button_start -> startActivity(Intent(this, MainActivity::class.java))
        }
    }

}