package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.adapters.CartAdapter2
import com.example.jsone3_categoryfood.database.DBHelper
import com.example.jsone3_categoryfood.database.UserDao
import com.example.jsone3_categoryfood.ext.show
import com.example.jsone3_categoryfood.models.Address
import com.example.jsone3_categoryfood.models.CartItemsResponse
import kotlinx.android.synthetic.main.activity_cart.recycler_view
import kotlinx.android.synthetic.main.layout_order_submit.*
import org.json.JSONArray
import org.json.JSONObject

class OrderSubmitActivity : BaseActivity(R.layout.layout_order_submit) {
    private var address: Address? = null
    lateinit var dbHelper: DBHelper

    var oldTotalPrice = 0f
    var newTotalPrice = 0f

    lateinit var cartList: MutableList<CartItemsResponse>
    override fun initView(savedInstanceState: Bundle?) {
        intent.getParcelableExtra<Address>(ADDRESS)?.let {
            address = it
        }

//        var toolbar = shopping_cart_toolbar
//        toolbar.title = ""
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun initData() {
        dbHelper = DBHelper(this)
        cartList = dbHelper.readProduct()
        var cartAdapter = CartAdapter2(this, cartList)

        for (i in cartList) {
            oldTotalPrice += i.price * i.quantity
            newTotalPrice += i.mrp * i.quantity
        }
//        pro_total_old_price.text = oldTotalPrice.toString()
        tv_price.text = "$ ${newTotalPrice}"

        recycler_view.adapter = cartAdapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    fun submit(v: View) {
        Volley.newRequestQueue(this).add(JsonObjectRequest(
            Request.Method.POST,
            "http://apolis-grocery.herokuapp.com/api/orders",
            JSONObject().apply {
                put("userId", UserDao.getUser()!!._id)
                put("orderSummary", JSONObject().apply {
                    put("totalAmount", oldTotalPrice)
                    put("ourPrice", newTotalPrice)
                    put("discount", oldTotalPrice - newTotalPrice)
                    put("deliveryCharges", 0)
                    put("orderAmount", newTotalPrice)
                })
                
                put("shippingAddress", address)
                put("payment", JSONObject().apply {
                    put(
                        "paymentMode",
                        (findViewById<RadioButton>(rg_pay_mode.checkedRadioButtonId)).text
                    )
                    put("paymentStatus", "completed")
                })
                val products = JSONArray()
                cartList.forEach {
                    products.put(JSONObject().apply {
//                        put("_id", it.id)
                        put("mrp", it.mrp)
                        put("price", it.price)
                        put("quantity", it.quantity)
                        put("image", it.image)
                    })
                }
                put("products", products)
            }, {
                "Add successfully".show
                startActivity(Intent(this, OrderSubmitStatusActivity::class.java))
                dbHelper.deleteAll()
                finish()
            },
            {
                "Add failed".show
                Log.i("Volley", "test：${String(it.networkResponse.data)}")
            }
        ))
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.shopping_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
////            android.R.id.home -> finish()
//            R.id.shopping_cart -> startActivity(Intent(this,CartActivity::class.java))
//        }
//        return true
//    }

    companion object {
        const val ADDRESS = "address"
    }
}