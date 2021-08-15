package com.muhammad89a.amiiboapp.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 *  Helper function that either loads image or placeholder
 */


fun ImageView.showImageByGlide(url:String) {
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .placeholder(android.R.color.background_light)
        .into(this)

}


fun ImageView.showOrNot(show:Boolean) {
   if(show){
       this.visibility = View.VISIBLE
   }else{
       this.visibility = View.GONE
   }
}


fun View.setOnClickListenerDebounced(threshold: Long = 500, listener: () -> Unit) {
    var lastClick: Long? = null
    setOnClickListener {
        val now = System.currentTimeMillis()
        val lastClickLocal = lastClick
        if (lastClickLocal == null || now - lastClickLocal > threshold) {
            lastClick = now
            listener()
        }
    }
}
