package com.example.healthylifestyleapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.healthylifestyleapp.R

class SlidingImage_Adapter(private val context: Context) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private val images = arrayOf(
        R.drawable.download_img,
        R.drawable.download,
        R.drawable.ic_launcher_background
    )

    override fun getCount(): Int {
        return images.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slidingimages_layout, null)
        val imageView = view.findViewById<View>(R.id.imageView) as ImageView
        imageView.setImageResource(images[position])


        val vp = container as ViewPager
        vp.addView(view, 0)
        return view


    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)

    }
}
