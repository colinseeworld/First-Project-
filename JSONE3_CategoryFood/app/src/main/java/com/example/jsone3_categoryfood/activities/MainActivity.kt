package com.example.jsone3_categoryfood.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.database.UserDao
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.containt_main.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener,
NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (UserDao.isLogin()) {
            startActivity(Intent(this, CategoryActivity::class.java))
        }
        init()
    }

    private fun init() {
        login.setOnClickListener(this)
        register.setOnClickListener(this)

        drawerLayout = drawer_layout
        navView = nav_view

        var headerView = navView.getHeaderView(0)
        UserDao.getUser()?.let {
            headerView.text_view_header_name.text = it.firstName
            headerView.text_view_nav_header_email.text = it.email
        }


        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, my_toolbar, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_account -> startActivity(Intent(this,LoginActivity::class.java))
            R.id.item_category -> startActivity(Intent(this, CategoryActivity::class.java))
            R.id.item_orders -> startActivity(Intent(this,OrderHistoryActivity::class.java))
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            register -> startActivity(Intent(this, RegisterActivity::class.java))
            login -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
