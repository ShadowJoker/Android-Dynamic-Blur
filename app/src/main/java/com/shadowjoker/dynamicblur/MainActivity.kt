package com.shadowjoker.dynamicblur

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
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
    private var lastTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScrollListener()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initScrollListener() {
        this.scrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (SystemClock.elapsedRealtime() - lastTime < 50) {
                return@setOnScrollChangeListener
            }
            lastTime = SystemClock.elapsedRealtime()
            this.scrollView.isDrawingCacheEnabled = true
            this.scrollView.buildDrawingCache()
            val bitmap = this.scrollView.drawingCache ?: return@setOnScrollChangeListener//截取区域视图
            val bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, 400)
            doBlur(bitmap1, this.iv_blur, 10)
            bitmap1.recycle()
            this.scrollView.isDrawingCacheEnabled = false
        }
    }

    private fun doBlur(bitmap: Bitmap, imageView: ImageView, radius: Int) {
        overlay?.recycle()
//        overlay = bitmap
//        overlay = StackBlurManager(overlay).process(radius) // Java StackBlur高斯模糊
        overlay = NativeStackBlur.process(bitmap, radius) // Native StackBlur高斯模糊
        imageView.setImageBitmap(overlay)
    }
}