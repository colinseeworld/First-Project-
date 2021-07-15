package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.adapters.OrderHistoryAdapter
import com.example.jsone3_categoryfood.database.UserDao
import com.example.jsone3_categoryfood.models.OrderData
import com.example.jsone3_categoryfood.models.OrderHistoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_history.*
import org.json.JSONObject

class OrderHistoryActivity : AppCompatActivity(), View.OnClickListener {

    var orderList: ArrayList<OrderData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        init()

        shopping()

        var toolbar = order_history_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun init() {
        var requestQueue = Volley.newRequestQueue(this)
        Log.i("test", "http://apolis-grocery.herokuapp.com/api/orders/${UserDao.getUser()!!._id}")
        var request = JsonObjectRequest(
            Request.Method.GET,
            "http://apolis-grocery.herokuapp.com/api/orders/${UserDao.getUser()!!._id}",
            JSONObject(),
            Response.Listener {
                var orderResponse = Gson().fromJson(it.toString(), OrderHistoryResponse::class.java)
                orderResponse.data?.let{
                orderList = it as ArrayList<OrderData>
                var orderHistoryAdapter = OrderHistoryAdapter(this, orderList)
                recycler_view_for_order_history.adapter = orderHistoryAdapter
                recycler_view_for_order_history.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }

            },
            Response.ErrorListener {

            }
        )
        requestQueue.add(request)
    }

    private fun shopping(){
        tv_home.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            tv_home -> startActivity(Intent(this,CategoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}