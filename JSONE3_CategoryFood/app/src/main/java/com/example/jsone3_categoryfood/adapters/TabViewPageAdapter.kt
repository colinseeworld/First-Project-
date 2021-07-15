package com.example.jsone3_categoryfood.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jsone3_categoryfood.fragments.ProductListFragment

class TabViewPageAdapter(fa: FragmentActivity, private val tabs: Array<Pair<String, Int>>) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return ProductListFragment.newInstance(tabs[position].second)
    }

}