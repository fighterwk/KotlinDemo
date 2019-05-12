package com.kyle.demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class MainActivity : AppCompatActivity() {
    lateinit var mUI: UI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mUI = UI()
        mUI.setContentView(this)
//        mUI.setTitle("我的标题")

        with(mUI.mToolbar){
            setSupportActionBar(this)

            setNavigationIcon(R.mipmap.icon_back)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

    }
}


class UI : AnkoComponent<AppCompatActivity> {

    lateinit var mToolbar :android.support.v7.widget.Toolbar

    override fun createView(ui: AnkoContext<AppCompatActivity>): View {
        return with(ui) {
            coordinatorLayout {
                fitsSystemWindows = true
                themedAppBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
                    id = R.id.main_appbar
                    fitsSystemWindows = true
                    collapsingToolbarLayout {
                        id = R.id.main_collapsing
                        //app:contentScrim = ?attr/colorPrimary //not support attribute
                        // 显示toolbar的时候背景颜色
                        setContentScrimColor(resources.getColor(R.color.colorPrimary))
                        //app:expandedTitleGravity = center|bottom //not support attribute
//                        expandedTitleGravity = Gravity.CENTER
                        //app:toolbarId = @id/main.toolbar //not support attribute
                        //app:title = @string/app_name //not support attribute
                        expandedTitleMarginStart = dip(18)
                        // 设置扩大字体的样式
                        setExpandedTitleTextAppearance(R.style.expanded_title)

                        title = "Hello CoordinatorLayout"
                        imageView {
                            id = R.id.main_backdrop
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            imageResource = R.drawable.img_mv_0
                            //app:layout_collapseMode = parallax //not support attribute
                            fitsSystemWindows = true
                        }.lparams(width = matchParent, height = matchParent) {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                        }
                        mToolbar = toolbar {
                            id = R.id.main_toolbar
                            //app:layout_collapseMode = pin //not support attribute
                            popupTheme = R.style.ThemeOverlay_AppCompat_Light
                        }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize)) {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                        }

                    }.lparams(width = matchParent, height = matchParent) {
                        // 上滑到退出， 滑动View 滑动到顶部的时候，显示appbarlayout的内容
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                (AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED)
                        // 上滑到退出，下滑就显示
//                       scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
//                               (AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                    }
                }.lparams(width = matchParent, height = dip(300))
                nestedScrollView {
                    textView {
                        textSize = 20f //sp
                        //android:lineSpacingExtra = 8dp //not support attribute
                        setLineSpacing(dip(8).toFloat(), 1.toFloat())
                        text = resources.getString(R.string.lorem)
                        padding = dip(15)
                    }.lparams(width = matchParent) {

                    }
                }.lparams(width = matchParent, height = matchParent) {
                    behavior = android.support.design.widget.AppBarLayout.ScrollingViewBehavior()
                }
            }

        }
    }

    fun showSnack() {
//        Snackbar.make(mTvSplit, "内容提示", Snackbar.LENGTH_SHORT)
//            .show()
    }
}


//class UI : AnkoComponent<AppCompatActivity>{
//    lateinit var mCollapsing : CollapsingToolbarLayout
//    override fun createView(ui: AnkoContext<AppCompatActivity>):View {
//       val view = with(ui) {
//            include<View>(R.layout.activity_coordinator_demo)
//        }
//
//        with(view){
//            mCollapsing = findViewById(R.id.main_collapsing)
//        }
//
//        return view
//    }
//
//    fun setTitle(str:String){
//        mCollapsing.title = str
//    }
//}