package com.example.carbooking

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2

class ClickListener {
    fun text_input_click_listener(view_pager: ViewPager2){
        val params = view_pager.layoutParams as ConstraintLayout.LayoutParams
        params.matchConstraintPercentHeight = 1.0f
        view_pager.layoutParams = params
    }
}