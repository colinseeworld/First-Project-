package com.example.jsone3_categoryfood.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.adapters.CartAdapter
import com.example.jsone3_categoryfood.database.DBHelper
import com.example.jsone3_categoryfood.models.CartItemsResponse
import kotlinx.android.synthetic.main.activity_cart.*
import java.math.RoundingMode

class CartActivity : AppCompatActivity() {

    companion object {
        lateinit var dbHelper: DBHelper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dbHelper = DBHelper(this)
        init()

        var toolbar = shopping_cart_toolbar
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkout()
    }

    private fun init() {
        var cartList: MutableList<CartItemsResponse> = dbHelper.readProduct()
        cartList.let {
            if (it.size == 0) {
                startActivity(Intent(this, EmptyCartActivity::class.java))
                finish()
            }
        }
        var cartAdapter = CartAdapter(this, cartList)

        var oldTotalPrice = 0f
        var newTotalPrice = 0f
        var discount = 0f

        for (i in cartList) {
            oldTotalPrice += i.price * i.quantity
            newTotalPrice += i.mrp * i.quantity
            discount = oldTotalPrice - newTotalPrice
        }
        pro_total_old_price.text = "$ " + oldTotalPrice.toString()
        pro_total_new_price.text = "$ " + newTotalPrice.toString()
        pro_discount.text = "-$ "+ discount.toString().toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

        recycler_view.adapter = cartAdapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_clear_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.clear_cart -> {
                AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Are you sure to empty your basket?")
                    .setPositiveButton("Confirm") { dialog, which ->
                        if (dbHelper.deleteAll()) {
                            this.finish()
                            this.overridePendingTransition(0, 0)
                            startActivity(Intent(this, EmptyCartActivity::class.java))
                            this.overridePendingTransition(0, 0)

                        } else
                            Toast.makeText(applicationContext, "Error deleting", Toast.LENGTH_SHORT)
                                .show()
                    }
                    .setNegativeButton("Cancel") { dialog, which -> }
                    .setIcon(R.drawable.ic_baseline_warning_amber_24)
                    .show()
            }
        }
        return true
    }

    private fun checkout() {
        select_address.setOnClickListener {
            startActivity(Intent(this, DisplayAddressActivity::class.java))
            finish()
        }
    }
}