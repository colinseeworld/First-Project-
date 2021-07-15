package com.example.jsone3_categoryfood.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.database.UserDao
import com.example.jsone3_categoryfood.ext.isNullHint
import com.example.jsone3_categoryfood.ext.show
import com.example.jsone3_categoryfood.models.Address
import com.example.jsone3_categoryfood.wight.SelectTypePopupWindow
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.json.JSONObject

class EditAddressActivity : AppCompatActivity() {
    var verifyEditTexts = emptyArray<TextInputEditText>()
    private var editAddress: Address? = null
    private var isRefresh = false
    private lateinit var selectTypePopupWindow: SelectTypePopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        verifyEditTexts =
            arrayOf(
                edit_address_type,
                edit_address_street,
                edit_address_city,
                edit_address_zip_code,
                edit_address_house_no
            )
        initView()
    }

    fun initView() {
        intent.getParcelableExtra<Address>(ADDRESS)?.let {
            editAddress = it
            editAddress?.apply {
                button_delete_address.visibility=View.VISIBLE
                edit_address_zip_code.setText("$pincode")
                edit_address_city.setText(city)
                edit_address_street.setText(streetName)
                edit_address_house_no.setText(houseNo)
                edit_address_type.setText(type)
            }

        }

        edit_address_type.viewTreeObserver?.addOnGlobalLayoutListener {
            selectTypePopupWindow = SelectTypePopupWindow(this, edit_address_type!!.measuredWidth)
            selectTypePopupWindow.setOnClickListener(object :
                SelectTypePopupWindow.OnClickListener {
                override fun typeName(data: String) {
                    edit_address_type.setText(data)
                }

            })
        }
        edit_address_type.setOnClickListener {
            selectTypePopupWindow.show(it)
        }

    }

    fun onClick(v: View) {
        when (v) {
            button_edit_address -> {
                if (verify()) {
                    Volley.newRequestQueue(this).add(JsonObjectRequest(
                        if (editAddress == null) Request.Method.POST else Request.Method.PUT,
                        "http://apolis-grocery.herokuapp.com/api/address" + if (editAddress == null) "" else "/${editAddress!!._id}",
                        JSONObject().apply {
                            put("pincode", edit_address_zip_code.text)
                            put("city", edit_address_city.text)
                            put("streetName", edit_address_street.text)
                            put("houseNo", edit_address_house_no.text)
                            put("type", edit_address_type.text)
                            put("userId", UserDao.getUser()!!._id)
                        }, {
                            isRefresh = true
//                            onBackPressed()
                            startActivity(Intent(this,DisplayAddressActivity::class.java))
                            "Add successfully".show
                        },
                        {
                            "Add failed".show
                        }
                    ))
                }
            }
            button_delete_address -> {
                AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Are you sure to delete address?")
                    .setPositiveButton("Confirm") { dialog, which ->
                        Volley.newRequestQueue(this).add(JsonObjectRequest(
                           Request.Method.DELETE,
                            "http://apolis-grocery.herokuapp.com/api/address/${editAddress!!._id}" ,
                            JSONObject(), {
                                dialog.dismiss()
                                isRefresh = true
                                onBackPressed()
                                "Delete successfully".show
                            },
                            {
                                "Delete failed".show
                            }
                        ))
                    }
                    .setNegativeButton("Cancel", { dialog, which -> })
                    .setIcon(R.drawable.ic_baseline_warning_amber_24)
                    .show()
            }
        }


    }

    /*
    verify if inputted
     */
    fun verify(): Boolean {
        verifyEditTexts.forEach {
            if (!it.isNullHint()) {
                return false
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (isRefresh)
            setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    companion object {
        const val ADDRESS = "address"

    }

}