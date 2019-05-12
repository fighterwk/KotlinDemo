package com.kyle.demo

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.viewPager
import kotlin.math.abs

/**
 * 作者:kyle
 * 描述:
 * 创建时间:2019-05-12 15:41
 **/
class MagicIndicatorDemo2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mUi = MagicIndicator2UI()

        mUi.setContentView(this)
        mUi.bindIndicatorAndViewPager(listOf("推荐", "关注"))

    }

}

class MagicIndicator2UI : AnkoComponent<AppCompatActivity> {

    lateinit var mMagicIndicator: MagicIndicator
    lateinit var mViewPager: ViewPager

    lateinit var mTvTitle: TextView
    lateinit var mIvBack: ImageView

    lateinit var mAppBarLayout: AppBarLayout

    override fun createView(ui: AnkoContext<AppCompatActivity>): View {
        val view = with(ui) {
            coordinatorLayout {
                fitsSystemWindows = true
                mAppBarLayout =  appBarLayout {
                    //                    id = R.id.main_appbar
                    fitsSystemWindows = true
                    collapsingToolbarLayout {
                        //                        id = R.id.main_collapsing
                        //app:contentScrim = ?attr/colorPrimary //not support attribute
                        // 显示toolbar的时候背景颜色
                        setContentScrimColor(colorAttr(R.attr.colorPrimary))
                        imageView {
                            id = R.id.main_backdrop
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            imageResource = R.drawable.img_mv_0
                            //app:layout_collapseMode = parallax //not support attribute
                            fitsSystemWindows = true
                        }.lparams(width = matchParent, height = matchParent) {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                        }
                        toolbar {
                            setContentInsetsRelative(0, 0)
                           relativeLayout {
                               mTvTitle = textView {
                                   text = "指示器"
                                   textSize = 18.toFloat()
                                   textColor = Color.WHITE
                               }.lparams(wrapContent, wrapContent) {
                                   centerInParent()
                               }

                               mIvBack = imageButton {
                                   scaleType = ImageView.ScaleType.CENTER
                                   maxWidth = dip(48)
                                   maxHeight = dip(48)
                                   padding = dip(8)
                                   backgroundColor = Color.TRANSPARENT
                                   imageResource = R.mipmap.icon_back
                                   imageTintList = ColorStateList.valueOf(Color.WHITE)
                               }.lparams(wrapContent, wrapContent) {
                                   centerVertically()
                                   marginStart = dip(12)
                               }

                           }
                               .lparams(matchParent, matchParent)

                        }.lparams( matchParent, dimenAttr(R.attr.actionBarSize)) {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                        }

                    }.lparams(width = matchParent, height = dip(200)) {
                        // 上滑到退出， 滑动View 滑动到顶部的时候，显示appbarlayout的内容
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                (AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED)
                    }

                    mMagicIndicator = magicIndicator {
                        backgroundColor = Color.parseColor("#fafafa")
                    }.lparams(matchParent, dip(50))

                }.lparams(width = matchParent, height = wrapContent)


                mViewPager = viewPager {

                }.lparams(matchParent, matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
        }

        mAppBarLayout.addOnOffsetChangedListener { appBar, verticalOffset ->
            val range = appBar.totalScrollRange
            if (0 == verticalOffset){
                mTvTitle.gone()
                mIvBack.gone()
            }else if (abs(verticalOffset) >= range / 2){
                mTvTitle.visible()
                mIvBack.visible()
            }
        }

        mIvBack.onClick {
            ui.owner.onBackPressed()
        }

        return view
    }


    /**
     * 绑定ViewPager的指示器
     */
    fun bindIndicatorAndViewPager(tabs: List<String>) {

        initIndicator(tabs)
        initViewPager(tabs.size)

        ViewPagerHelper.bind(mMagicIndicator, mViewPager)
    }

    /**
     * 初始化ViewPager，绑定ViewPager的数据
     */
    private fun initViewPager(size: Int) {
        mViewPager.adapter = object : PagerAdapter() {
            override fun isViewFromObject(p0: View, p1: Any) = (p0 == p1)

            override fun getCount() = size

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                return container.nestedScrollView {
                    textView {
                        setText(R.string.lorem)
                        padding = dip(10)
                        textSize = dip(18).toFloat()
                    }.lparams(matchParent, wrapContent)
                }
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }

        }
    }

    /**
     * 绑定指示器的数据和样式
     * 字体的大小，字体的选中和未选中的颜色
     * 指示器下标线的颜色
     */
    private fun initIndicator(tabs: List<String>) {
        val navigator = CommonNavigator(mMagicIndicator.context)
        navigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val minSize = 10.toFloat()
                val maxSize = 18.toFloat()

                val titleView = object : ColorTransitionPagerTitleView(context) {

                    override fun onLeave(
                        index: Int, totalCount: Int,
                        leavePercent: Float, leftToRight: Boolean
                    ) {
                        super.onLeave(index, totalCount, leavePercent, leftToRight)
                        // 退出 maxSize -> minSize
                        // leavePercent 0 - 1
                        textSize = when (leavePercent) {
                            0f -> maxSize
                            1f -> minSize
                            else -> maxSize - (maxSize - minSize) * leavePercent
                        }
                        // maxSize - (maxSize - minSize) * leavePercent
//                        textSize = maxSize * (1 - )
                        println("------------- tab退出的时候 $leavePercent")
                    }

                    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
                        super.onEnter(index, totalCount, enterPercent, leftToRight)
                        // 进入
                        println("------------- tab进入的时候 $enterPercent")

                        textSize = when (enterPercent) {
                            0f -> minSize
                            1f -> maxSize
                            else -> minSize + (maxSize - minSize) * enterPercent
                        }

                    }
                }
                with(titleView) {
                    selectedColor = Color.parseColor("#ff00ff")
                    normalColor = Color.parseColor("#000000")
                    text = tabs[index]
                    textSize = maxSize // 最大值默认是 18， 最小值是 14
                }

                return titleView
            }

            override fun getCount(): Int {
                return tabs.size
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)

                with(indicator) {
                    setColors(Color.RED, Color.BLACK, Color.BLUE)
                    mode = LinePagerIndicator.MODE_WRAP_CONTENT
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator()
                }

                return indicator
            }
        }
        mMagicIndicator.navigator = navigator
    }


}
