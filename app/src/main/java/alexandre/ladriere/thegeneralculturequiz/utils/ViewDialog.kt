package alexandre.ladriere.thegeneralculturequiz.utils

import alexandre.ladriere.thegeneralculturequiz.R
import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget

class ViewDialog(private val activity: Activity) {

    private lateinit var dialog: Dialog

    fun showDialog() {
        dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_loading_layout)
        val gifImageView: ImageView = dialog.findViewById(R.id.custom_loading_imageView)
        val imageViewTarget =
            GlideDrawableImageViewTarget(gifImageView)
        Glide.with(activity)
            .load(R.drawable.loading)
            .placeholder(R.drawable.loading)
            .centerCrop()
            .crossFade()
            .into(imageViewTarget)
        dialog.show()
    }

    fun hideDialog() {
        dialog.dismiss()
    }
}