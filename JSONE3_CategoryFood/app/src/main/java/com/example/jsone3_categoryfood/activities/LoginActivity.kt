package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.app.Endpoints
import com.example.jsone3_categoryfood.database.UserDao
import com.example.jsone3_categoryfood.ext.show
import com.example.jsone3_categoryfood.models.LoginResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        var toolbar = login_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun init() {
        button_login.setOnClickListener(this)
        click_go_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            button_login -> {
                var email = input_email.text.toString()
                var password = input_password.text.toString()

                var jsonObject = JSONObject()
                jsonObject.put("email", email)
                jsonObject.put("password", password)

                var requestQueue = Volley.newRequestQueue(this)
                var request = JsonObjectRequest(
                    Request.Method.POST,
                    Endpoints.getLoginUrl(),
                    jsonObject,
                    {
//                    var jsonObject = it
//                    var token = jsonObject.getString("token")
//                    var userObject = jsonObject.getJSONObject("user")
//                    var firstName = userObject.getString("firstName")
                        val loginResponse =
                            Gson().fromJson(it.toString(), LoginResponse::class.java)
                        loginResponse.user.token = loginResponse.token
                        UserDao.setUser(loginResponse.user)
                        if (UserDao.isLogin()) {
                            "login successful".show
                            startActivity(Intent(this, CategoryActivity::class.java))
                        }
                    },
                    {
                        Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
                    }
                )
                requestQueue.add(request)
            }

            click_go_register -> startActivity(Intent(this, RegisterActivity::class.java))
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