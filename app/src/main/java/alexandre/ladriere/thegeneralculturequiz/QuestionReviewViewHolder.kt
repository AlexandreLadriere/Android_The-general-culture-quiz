package alexandre.ladriere.thegeneralculturequiz

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionReviewViewHolder (rootView: View) :
    RecyclerView.ViewHolder(rootView) {
    var question: TextView = rootView.findViewById(R.id.item_text_view_question)
    var answer: TextView = rootView.findViewById(R.id.item_text_view_answer)
    private var container: LinearLayout =
        rootView.findViewById(R.id.item_linear_layout_root)

    init {
        container.setOnClickListener {
        }
    }
}