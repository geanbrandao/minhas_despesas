package com.geanbrandao.minhasdespesas.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView: RecyclerView {

    private var canScroll: Boolean = false

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onInterceptHoverEvent(event: MotionEvent?): Boolean {
        return false
    }
}