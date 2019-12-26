package alexandre.ladriere.thegeneralculturequiz.utils

import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi

internal fun removeSpecialCharFromString(string: String): String {
    var outStr = string.replace("&#039;", "'")
    outStr = outStr.replace("&quot;", "\"")
    outStr = outStr.replace("&ouml;", "ö")
    outStr = outStr.replace("&deg;", "°")
    outStr = outStr.replace("&ldquo;", "“")
    outStr = outStr.replace("&rdquo;", "”")
    outStr = outStr.replace("&amp;", "&")
    outStr = outStr.replace("&divide;", "÷")
    outStr = outStr.replace("&ograve;", "ò")
    outStr = outStr.replace("&Ouml;", "Ö")
    outStr = outStr.replace("&ouml;", "ö")
    outStr = outStr.replace("&uuml;", "ü")
    outStr = outStr.replace("&pi;", "π")
    outStr = outStr.replace("&atilde;", "ã")
    return outStr
}

fun curveImageViewCorner(image: ImageView, curveRadius: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        image.outlineProvider = object : ViewOutlineProvider() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    0,
                    0,
                    (view!!.width + curveRadius).toInt(),
                    view.height,
                    curveRadius
                )
            }
        }
        image.clipToOutline = true
    }
}