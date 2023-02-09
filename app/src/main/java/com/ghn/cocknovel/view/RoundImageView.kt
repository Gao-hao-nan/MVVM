package com.ghn.cocknovel.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * @author 浩楠
 *
 * @date 2023/1/11   14:16.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:
 */


class RoundImageView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    AppCompatImageView(context!!, attrs, defStyle) {
    private val paint: Paint

    init {
        paint = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable
        if (null != drawable) {
            val bitmap = (drawable as BitmapDrawable).bitmap
            val b = getCircleBitmap(bitmap, 14)
            val rectSrc = Rect(0, 0, b.width, b.height)
            val rectDest = Rect(0, 0, width, height)
            paint.reset()
            canvas.drawBitmap(b, rectSrc, rectDest, paint)
        } else {
            super.onDraw(canvas)
        }
    }

    private fun getCircleBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        val x = bitmap.width
        canvas.drawCircle((x / 2).toFloat(), (x / 2).toFloat(), (x / 2).toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
}
