package com.shadowjoker.dynamicblur

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.commit451.nativestackblur.NativeStackBlur
import dynamicblur.shadowjoker.com.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * MainActivity
 * Created by sf-zhangpeng on 2018/3/15.
 */
class MainActivity : AppCompatActivity() {

    private var overlay: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScrollListener()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initScrollListener() {
        this.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            this.scrollView.isDrawingCacheEnabled = true
            this.scrollView.buildDrawingCache()
            val bitmap = this.scrollView.drawingCache ?: return@setOnScrollChangeListener//截取区域视图
            val bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, 400)

            if (bitmap != null) {
                doBlur(bitmap1, this.iv_blur, 8) //模糊处理
            }
//            bitmap1.recycle()
            this.scrollView.isDrawingCacheEnabled = false
        }
    }

    private fun doBlur(bitmap: Bitmap, imageView: ImageView, radius: Int) {
        val scaleFactor = 8
        if (overlay != null) {
//            overlay?.recycle()
        }
        overlay = Bitmap.createScaledBitmap(bitmap, bitmap.width / scaleFactor, bitmap.height / scaleFactor, false)
//        overlay = StackBlurManager(overlay).process(3) // Java StackBlur高斯模糊
        overlay = NativeStackBlur.process(overlay, 3) // NativeStackBlur高斯模糊
        imageView.setImageBitmap(overlay)
    }
}