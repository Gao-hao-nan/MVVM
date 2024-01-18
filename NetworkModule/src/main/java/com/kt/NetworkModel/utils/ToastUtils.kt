package com.kt.NetworkModel.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kt.ktmvvm.lib.R


/**
 * @author 浩楠
 *
 * @date 2023/8/30-17:02.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Description: TODO
 */
class ToastUtils(
    private val context: Context?=null
) {

    private var tipsText: TextView? = null
    private var toastimage: ImageView? = null
    private var toast: Toast? = null

    @SuppressLint("MissingInflatedId")
    fun InitToast() {
        if (toast == null) {
            toast = Toast(context)
            val view = LayoutInflater.from(context).inflate(LayoutID, null, false)
            tipsText = view.findViewById(R.id.toast_message)
            toastimage=view.findViewById(R.id.toast_image)
            toast!!.setView(view)

        }
    }

    fun setGravity(gravity: Int) {
        toast!!.setGravity(gravity, 0, 0)
    }

    fun setText(tips: String?) {
        tipsText!!.text = tips
    }
    fun setImage(url: Int?){
        toastimage?.visibility= View.VISIBLE
        toastimage?.let { Glide.with(context!!).load(url).into(it) }
    }

    fun show() {
        toast!!.show()
    }

    fun setShowTime(time: Int) {
        toast!!.duration = time
    }

    fun setTextColor(color: Int) {
        tipsText!!.setTextColor(context?.resources?.getColor(color)!!)
    }

    fun setTextSize(size: Float) {
        tipsText!!.textSize = size
    }

    companion object {
        private val LayoutID: Int = R.layout.toast_utlis
    }
}
