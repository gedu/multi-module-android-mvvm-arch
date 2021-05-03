package com.teddy.brunch

import android.os.Bundle
import com.teddy.brunch.base.BaseActivity

class MainActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }
}