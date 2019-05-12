package anko

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import org.jetbrains.anko.design.*
import org.jetbrains.anko.appcompat.v7.*
import android.support.design.widget.*
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import android.view.Gravity
import android.widget.*
import org.jetbrains.anko.support.v4.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.*

import com.kyle.demo.R

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.3.4
 */
class CoordinatorDemoActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		coordinatorLayout {
			fitsSystemWindows = true
			themedAppBarLayout(R.style.ThemeOverlay_AppCompat_Dark_ActionBar) {
				id = R.id.main_appbar
				fitsSystemWindows = true
				collapsingToolbarLayout {
					id = R.id.main_collapsing
					//app:contentScrim = ?attr/colorPrimary //not support attribute
					setContentScrimColor(resources.getColor(R.color.colorPrimary))
					//app:expandedTitleGravity = center|bottom //not support attribute
					expandedTitleGravity = Gravity.CENTER
					//app:toolbarId = @id/main.toolbar //not support attribute
					//app:title = @string/app_name //not support attribute
					title = "-------------"
					imageView {
						id = R.id.main_backdrop
						scaleType = ImageView.ScaleType.CENTER_CROP
						imageResource = R.drawable.img_mv_0
						//app:layout_collapseMode = parallax //not support attribute
						fitsSystemWindows = true
					}.lparams(width = matchParent, height = matchParent){
						collapseMode = COLLAPSE_MODE_PARALLAX
					}
					toolbar {
						id = R.id.main_toolbar
						//app:layout_collapseMode = pin //not support attribute
						popupTheme = R.style.ThemeOverlay_AppCompat_Light
					}.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize)){
						collapseMode = COLLAPSE_MODE_PIN
					}

				}.lparams(width = matchParent, height = matchParent) {
					scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
				}
			}.lparams(width = matchParent, height = dip(300))
			nestedScrollView {
				textView {
					textSize = 20f //sp
					//android:lineSpacingExtra = 8dp //not support attribute
					setLineSpacing(dip(8).toFloat(), dip(0).toFloat())
					text = resources.getString(R.string.lorem)
					padding = dip(15)
				}.lparams(width = matchParent){

				}
			}.lparams(width = matchParent, height = matchParent) {
				behavior = android.support.design.widget.AppBarLayout.ScrollingViewBehavior()
			}
		}
	}
}
