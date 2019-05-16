package com.kyle.demo.view

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.text.method.KeyListener
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 作者:kyle
 * 描述: 通过获取软键盘高度设置输入框的位置
 * 输入框的位置在软键盘的上方
 *
 * 需要android:windowSoftInputMode="adjustResize" 配合使用
 * 创建时间:2019-05-16 20:55
 **/
class InputFrameLayout : _FrameLayout, ViewTreeObserver.OnGlobalLayoutListener {
    var oldVisibleHeight: Int = -1
    var newVisibleHeight: Int = -1
    val mRect = Rect()

    lateinit var mEtInput: EditText
    lateinit var imm:InputMethodManager
    private var mTvAttachView:TextView? = null


    constructor(context: Context) : super(context){
        imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        visibility = View.GONE
        isClickable = true
        onClick {
            cancel()
        }

        mEtInput = editText {
            hint = "请输入文字"
            backgroundColor = Color.parseColor("#803c3c3c")
        }.lparams(matchParent, dip(48)){
            gravity = Gravity.BOTTOM
        }
    }

    fun onAttachTextView(tv:TextView){
        mTvAttachView = tv
    }

    fun show(){
        visibility = View.VISIBLE
        mEtInput.setText("")
        postDelayed({
            showImm()
        }, 60)
    }

    private fun cancel(){
        visibility = View.GONE
        mEtInput.setText("")
        val lp = mEtInput.layoutParams as MarginLayoutParams
        lp.bottomMargin = 0
        mEtInput.layoutParams = lp
        dismissImm()
    }

    fun confirm(){
        // 确定
        mTvAttachView?.text = mEtInput.text
        cancel()
    }

    private fun dismissImm(){
        imm.hideSoftInputFromWindow(mEtInput.windowToken, 0)
    }
    private fun showImm(){
        mEtInput.apply {
            requestFocus()
            isFocusable = true
            isFocusableInTouchMode = true
            imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener (this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        getWindowVisibleDisplayFrame(mRect)
        if (-1 == oldVisibleHeight){
            oldVisibleHeight = mRect.height()
            newVisibleHeight = mRect.height()
        }else{
            newVisibleHeight = mRect.height()
        }

        if (oldVisibleHeight == newVisibleHeight){
            return
        }

        val difference = oldVisibleHeight - newVisibleHeight
        if (difference > dip(100)){
            // 键盘打开
            println("------------ 键盘打开")
            // 监听子控件布局的的差异化进行设置动画
            TransitionManager.beginDelayedTransition(this)
            val lp = mEtInput.layoutParams as MarginLayoutParams
            lp.bottomMargin = difference+1000
            mEtInput.layoutParams = lp
        }else{
            // 键盘收起
            println("---------  键盘收起")
            cancel()
        }

        oldVisibleHeight = newVisibleHeight
    }
}
