package com.shadowjoker.dynamicblur

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dynamicblur.shadowjoker.com.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*


/**
 * MainActivity
 * Created by sf-zhangpeng on 2018/9/27.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_java.setOnClickListener { startActivity(Intent(this, JavaStackBlurActivity::class.java)) }
        button_native.setOnClickListener { startActivity(Intent(this, NativeStackBlurActivity::class.java)) }
        button_renderscript.setOnClickListener { startActivity(Intent(this, RenderScriptBlurActivity::class.java)) }
    }
}