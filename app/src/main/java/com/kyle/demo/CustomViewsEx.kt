package com.kyle.demo

import android.view.View
import android.view.ViewGroup
import net.lucode.hackware.magicindicator.MagicIndicator
import org.jetbrains.anko.custom.ankoView

/**
 * 作者:kyle
 * 描述:
 * 创建时间:2019-05-12 16:43
 **/
inline fun ViewGroup.magicIndicator(init: MagicIndicator.() -> Unit): MagicIndicator {
    return ankoView({
        MagicIndicator(it)
    }, 0, init)
}

inline fun View.gone(){
    visibility = View.GONE
}

inline fun View.visible(){
    visibility = View.VISIBLE
}