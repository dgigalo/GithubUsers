package ru.githubusers.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class RecycleViewTouchListener(val ctx: Context, val recyclerView: androidx.recyclerview.widget.RecyclerView, val touchListener: TouchListener) : androidx.recyclerview.widget.RecyclerView.OnItemTouchListener{

    val gestureDetector = GestureDetector(ctx, object : GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }
    })

    override fun onInterceptTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && gestureDetector.onTouchEvent(e)) {
            touchListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    override fun onTouchEvent(rv: androidx.recyclerview.widget.RecyclerView, e: MotionEvent) {
    }

    interface TouchListener{
        fun onClick(view: View, position:Int)
    }
}