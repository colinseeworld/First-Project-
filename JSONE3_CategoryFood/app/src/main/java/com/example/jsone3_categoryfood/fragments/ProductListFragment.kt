package com.example.jsone3_categoryfood.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jsone3_categoryfood.R
import com.example.jsone3_categoryfood.activities.ProductDetailActivity
import com.example.jsone3_categoryfood.adapters.ProductListAdapter
import com.example.jsone3_categoryfood.models.Product
import com.example.jsone3_categoryfood.models.ProductsListResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_list.*
import org.json.JSONObject

class ProductListFragment : Fragment(R.layout.fragment_list) {
    private var id: Int? = null
    lateinit var productListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(CATEGORY_ID)
        }
        id?.let {
            initData()

        }
    }

    private fun initAdapter(dataList: List<Product>) {
        var productListAdapter = ProductListAdapter(requireContext(), dataList
        )
        productListAdapter.setOnAdapterListener( object :ProductListAdapter.onAdapterListener{
            override fun onItemClicked(data: Product) {
                ProductDetailActivity.toThisActivity(requireActivity(),data._id)

                var intent = Intent(activity,ProductDetailActivity::class.java)
                intent.putExtra("product",data)

            }

        })
        rv_goods.adapter = productListAdapter
    }

    private fun initData() {
        Volley.newRequestQueue(requireContext()).add(JsonObjectRequest(
            Request.Method.GET,
            "http://apolis-grocery.herokuapp.com/api/products/sub/${id}",
            JSONObject(), {

                val response =
                    Gson().fromJson(it.toString(), ProductsListResponse::class.java)
                Log.i("subcategoryResponse", "${response.count}")
//                initAdapter(subcategoryResponse.data)
                initAdapter(response.data)
            },
            {

            }
        ))

    }

    companion object {
        private const val CATEGORY_ID = "category_id"
        fun newInstance(id: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATEGORY_ID, id)
                }
            }
    }

}