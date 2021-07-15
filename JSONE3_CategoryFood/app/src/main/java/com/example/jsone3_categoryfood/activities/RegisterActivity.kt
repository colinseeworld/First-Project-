package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.app.Endpoints
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.input_email
import kotlinx.android.synthetic.main.activity_register.input_password
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

        var toolbar = register_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    private fun init(){
        button_register.setOnClickListener {
            var firstName = input_name.text.toString()
            var email = input_email.text.toString()
            var password = input_password.text.toString()
            var mobile = input_mobile.text.toString()

            var jsonObject = JSONObject()
            jsonObject.put("firstName", firstName)
            jsonObject.put("email", email)
            jsonObject.put("mobile", mobile)
            jsonObject.put("password", password)

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getRegisterUrl(),
                jsonObject,
                Response.Listener {
                    Log.d("abc", it.toString())
                    startActivity(Intent(this, LoginActivity::class.java))
                },
                Response.ErrorListener {
                    Log.e("abc", it.message.toString())
                }
            )
            requestQueue.add(request)
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