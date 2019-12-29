package alexandre.ladriere.thegeneralculturequiz.questionsreview

import alexandre.ladriere.thegeneralculturequiz.R
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Custom View Holder for the recycler view of the QuestionReviewActivity
 */
class QuestionReviewViewHolder(rootView: View, private val favQuestion: (Int) -> Unit) :
    RecyclerView.ViewHolder(rootView) {
    var question: TextView = rootView.findViewById(R.id.item_text_view_question)
    var answer: TextView = rootView.findViewById(R.id.item_text_view_answer)
    var favorite: ImageButton = rootView.findViewById(R.id.item_image_button_fav)
    private var container: LinearLayout =
        rootView.findViewById(R.id.item_linear_layout_root)

    init {
        container.setOnClickListener {
        }
        favorite.setOnClickListener {
            favQuestion(adapterPosition)
        }
    }
}