package com.example.jsone3_categoryfood.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.adapters.AddressAdapter
import com.example.jsone3_categoryfood.database.UserDao
import com.example.jsone3_categoryfood.ext.show
import com.example.jsone3_categoryfood.models.Address
import com.example.jsone3_categoryfood.models.AddressResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_display_address.*
import org.json.JSONObject

class DisplayAddressActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var addressAdapter: AddressAdapter
    val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it?.let {
                if (it.resultCode == Activity.RESULT_OK) {
                    initData()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_address)
        init()

    }

    private fun init() {
        button_add_address.setOnClickListener(this)
        initAdapter()
        initData()
    }

    override fun onClick(v: View?) {
        when (v) {
            button_add_address -> startActivity(Intent(this, EditAddressActivity::class.java))
            save_and_continue -> {
                if (addressAdapter.selectId.isEmpty()) {
                    "No shipping address selected".show
                } else {
                    val intent = Intent()
                    intent.setClass(this@DisplayAddressActivity, OrderSubmitActivity::class.java)
                        .apply {
                            putExtra(EditAddressActivity.ADDRESS, data)
                        }
                   startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun initAdapter() {
        addressAdapter = AddressAdapter(this, mutableListOf())
        addressAdapter.listener = object : AddressAdapter.onAdapterListener {
            override fun onItemClicked(data: Address) {
                val intent = Intent()
                intent.setClass(this@DisplayAddressActivity, EditAddressActivity::class.java)
                    .apply {
                        putExtra(EditAddressActivity.ADDRESS, data)
                    }
                requestActivity.launch(intent)
            }

        }
        recycler_view_for_address.adapter = addressAdapter
    }

    private fun initData() {
        addressAdapter.updateData(mutableListOf())
        Volley.newRequestQueue(this).add(JsonObjectRequest(
            Request.Method.GET,
            "http://apolis-grocery.herokuapp.com/api/address/${UserDao.getUser()!!._id}",
            JSONObject(), {
                val response = Gson().fromJson(it.toString(), AddressResponse::class.java)
                if (!response.error) {
                    addressAdapter.updateData(response.data)
                }
            },
            {

            }
        ))
    }
}