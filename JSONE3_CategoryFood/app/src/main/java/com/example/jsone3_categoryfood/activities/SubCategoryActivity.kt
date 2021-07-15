package com.example.jsone3_categoryfood.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.adapters.TabViewPageAdapter
import com.example.jsone3_categoryfood.models.SubCategory
import com.example.jsone3_categoryfood.models.SubData
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import org.json.JSONObject

class SubCategoryActivity : AppCompatActivity() {

    private var catId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        intent.getIntExtra(CAT_ID, -1).let {
            if (it > 0) {
                catId = it
                init()
            }
        }

        var toolbar = sub_category_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        val requestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://apolis-grocery.herokuapp.com/api/subcategory/${catId}" ,
            JSONObject(), {
                Log.i("subcategoryResponse", "$it")
                val subcategoryResponse =
                    Gson().fromJson(it.toString(), SubCategory::class.java)
                initAdapter(subcategoryResponse.data)
            },
            {

            }
        )
        requestQueue.add(request)
    }

    private fun initAdapter(data: List<SubData>) {
        val tabs: Array<Pair<String, Int>> = Array(data.size) { i ->
            data[i].subName to data[i].subId
        }

        for (i in data.indices) {
            tabs[i] = data[i].subName to data[i].subId
        }
        //viewPager adapter
        viewPager.adapter = TabViewPageAdapter(this, tabs)
        //Binding tabLayout viewPager
        TabLayoutMediator(
            tab_layout, viewPager, true, true
        ) { tab, position ->
            tab.text = tabs[position].first
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.shopping_cart -> startActivity(Intent(this,CartActivity::class.java))
        }
        return true
    }

    companion object {
        private const val CAT_ID = "cat_id"
        fun toThisActivity(mAty: Activity, id: Int) {
            val intent = Intent()
            intent.setClass(mAty, SubCategoryActivity::class.java).apply {
                putExtra(CAT_ID, id)
            }
            mAty.startActivity(intent)
        }
    }
}