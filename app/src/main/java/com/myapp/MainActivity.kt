package com.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val MIN_SEARCH_CHARACTERS = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.adapter = PageAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
        view_pager.setCurrentItem(1,false)

    }

}