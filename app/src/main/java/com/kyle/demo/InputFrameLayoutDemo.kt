package com.kyle.demo

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.hardware.display.DisplayManagerCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import com.kyle.demo.view.InputFrameLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 作者:kyle
 * 描述:
 * 创建时间:2019-05-16 21:04
 **/
class InputFrameLayoutDemo : AppCompatActivity() {
lateinit var mInputFrameLayout:InputFrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {

            frameLayout {

                backgroundColor = Color.GRAY

                textView("测试") {
                    onClick {
                        mInputFrameLayout.onAttachTextView(this@textView)
                        mInputFrameLayout.show()
                    }
                }.lparams(matchParent, dip(40)) {
                    gravity = Gravity.BOTTOM
                    bottomMargin = dip(30)
                }

            }.lparams(matchParent, matchParent)


            mInputFrameLayout =  inputFrameLayout {  }
                .lparams(matchParent, matchParent)
        }

    }
}