package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.adapters.CategoryAdapter
import com.example.jsone3_categoryfood.adapters.SlideImageAdapter
import com.example.jsone3_categoryfood.app.Endpoints
import com.example.jsone3_categoryfood.models.CategoryResponse
import com.example.jsone3_categoryfood.models.Data
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import org.json.JSONObject

class CategoryActivity : AppCompatActivity(),
    CategoryAdapter.onAdapterListener,
    SlideImageAdapter.onAdapterListener{

    var categoryList: ArrayList<Data> = ArrayList()

    var lastPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        init()

        var toolbar = category_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init(){
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            Endpoints.getCategoryUrl(),
            JSONObject(),
            Response.Listener {
                var categoryResponse = Gson().fromJson(it.toString(),CategoryResponse::class.java)

                categoryList = categoryResponse.data as ArrayList<Data>
                var categoryAdapter = CategoryAdapter(this,categoryList)
                categoryAdapter.setOnAdapterListener(this)
                Log.i("categoryResponse",it.toString())
                recycler_view.adapter = categoryAdapter
                recycler_view.layoutManager = GridLayoutManager(this,2)
                slideImage()
            },
            Response.ErrorListener {

            }
        )
        requestQueue.add(request)
    }

    private fun slideImage(){
        indicatorDots()
        var slideImageAdapter = SlideImageAdapter(this,categoryList)
        slideImageAdapter.setOnAdapterListener(this)
        upper_image_view.adapter = slideImageAdapter

        // upper image switch monitor
        upper_image_view.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                container_indicator.getChildAt(lastPosition)
                    .setBackgroundResource(R.drawable.shape_dot_not_selected)
                container_indicator.getChildAt(position)
                    .setBackgroundResource(R.drawable.shape_dot_selected)
                lastPosition = position
            }
        })

    }
    private fun indicatorDots(){
        for (i in 0 until categoryList.size){
            container_indicator.addView(
                ImageView(this).apply {
                    setBackgroundResource(if (i == 0)
                        R.drawable.shape_dot_selected
                    else R.drawable.shape_dot_not_selected)
                }
            )
        }
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

    override fun onItemClicked(data: Data) {
        SubCategoryActivity.toThisActivity(this,data.catId)
    }
}