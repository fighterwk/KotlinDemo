package com.kyle.demo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.view.ViewParent
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.viewPager
import kotlin.math.max

/**
 * 作者:kyle
 * 描述:
 * 创建时间:2019-05-12 15:41
 **/
class MagicIndicatorDemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mUi = MagicIndicatorUI()

        mUi.setContentView(this)
        mUi.bindIndicatorAndViewPager(listOf("推荐", "关注"))

    }

}

class MagicIndicatorUI : AnkoComponent<AppCompatActivity> {

    lateinit var mMagicIndicator: MagicIndicator
    lateinit var mViewPager: ViewPager

    override fun createView(ui: AnkoContext<AppCompatActivity>): View {
        return with(ui) {
            verticalLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                backgroundColor = Color.parseColor("#ffffff")

                mMagicIndicator = magicIndicator {
                    backgroundColor = Color.parseColor("#fafafa")
                }.lparams(matchParent, dip(50))

                mViewPager = viewPager {

                }.lparams(matchParent, matchParent)
            }
        }
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


