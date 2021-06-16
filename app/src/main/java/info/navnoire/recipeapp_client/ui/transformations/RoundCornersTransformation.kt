package info.navnoire.recipeapp_client.ui.transformations

import android.content.res.Resources
import android.graphics.*
import com.squareup.picasso.Transformation

class RoundCornersTransformation(private val radiusInDp: Float) : Transformation {
    private companion object {
        val density = Resources.getSystem().displayMetrics.density
    }

    override fun transform(source: Bitmap): Bitmap {
        val radiusInPx = radiusInDp * density
        val bitmap = with(source) { Bitmap.createBitmap(width, height, config) }
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
        val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        val rect = RectF(0.0f, 0.0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rect, radiusInPx, radiusInPx, paint)
        source.recycle()

        return bitmap
    }

    override fun key(): String {
        return "round_corners"
    }

}
