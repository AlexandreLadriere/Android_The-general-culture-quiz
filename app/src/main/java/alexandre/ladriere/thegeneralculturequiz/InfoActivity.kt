package alexandre.ladriere.thegeneralculturequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val backB = a_info_image_button_back.setOnClickListener {
            this.finish()
        }
        curveImageViewCorner(a_info_image_view_author, 30F)
    }
}
