package com.example.jsone3_categoryfood.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.database.DBHelper
import com.example.jsone3_categoryfood.models.Product
import com.example.jsone3_categoryfood.models.ProductResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.json.JSONObject

class ProductDetailActivity : AppCompatActivity() {

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        intent.getStringExtra(PRODUCT_ID)?.let {
            id = it
            init()
        }

        var toolbar = productDetail_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        Volley.newRequestQueue(this).add(JsonObjectRequest(
            Request.Method.GET,
            "http://apolis-grocery.herokuapp.com/api/products/${id}",
            JSONObject(), {
                Log.i("apolis", "http://apolis-grocery.herokuapp.com/api/products/${id}")
                Log.i("apolis", "${it.toString()}")
                val response =
                    Gson().fromJson(it.toString(), ProductResponse::class.java)
                if (!response.error) {
                    response.data?.let {
                        showInfo(it)
                    }
                }
            },
            {
            }
        ))
    }

    private fun showInfo(product: Product) {
        var url = "https://rjtmobile.com/grocery/images/" + product.image
        Picasso.get().load(url).into(iv_cover)
        tv_name.text = product.productName
        tv_quantity.text = "${product.unit}" //${data.quantity}
        tv_old_price.text = "＄${product.mrp}"
        tv_old_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tv_new_price.text = "＄${product.price}"
        tv_description.text = product.description

        btn_add.setOnClickListener{
            var dbHelper: DBHelper
            dbHelper = DBHelper(this)

            dbHelper.addProduct(product)

            startActivity(Intent(this,CartActivity::class.java))
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shopping_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.shopping_cart ->  startActivity(Intent(this,CartActivity::class.java))
        }
        return true
    }

    companion object {
        private const val PRODUCT_ID = "product_id"
        fun toThisActivity(mAty: Activity, id: String) {
            val intent = Intent()
            intent.setClass(mAty, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, id)
            }
            mAty.startActivity(intent)
        }
    }
}