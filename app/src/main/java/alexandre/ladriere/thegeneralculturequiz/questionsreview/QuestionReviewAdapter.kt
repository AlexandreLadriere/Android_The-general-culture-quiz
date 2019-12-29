package alexandre.ladriere.thegeneralculturequiz.questionsreview

import alexandre.ladriere.thegeneralculturequiz.R
import alexandre.ladriere.thegeneralculturequiz.questions.Question
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections.addAll

/**
 * Custom Adapter for the recycler view of the QuestionReviewActivity
 */
class QuestionReviewAdapter(
    private val questions: ArrayList<Question>,
    private val favQuestion: (Int) -> Unit
) :
    RecyclerView.Adapter<QuestionReviewViewHolder>() {

    private val itemsCopy: ArrayList<Question> = ArrayList()

    init {
        itemsCopy.addAll(questions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionReviewViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_review_item, parent, false)
        return QuestionReviewViewHolder(
            row,
            favQuestion
        )
    }

    override fun onBindViewHolder(holder: QuestionReviewViewHolder, position: Int) {
        val (category, type, difficulty, question, correctAnswer, proposition1, proposition2, proposition3, correct, favorite) = this.questions[position]
        holder.question.text = question
        holder.answer.text = correctAnswer
        if (correct) {
            holder.itemView.setBackgroundResource(R.drawable.custom_rectangle_correct_cr20_empty)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.custom_rectangle_error_cr20_empty)
            holder.answer.text = "Correct answer: $correctAnswer"
        }
        if (favorite) {
            holder.favorite.setImageResource(R.drawable.ic_favorite_24px)
        } else {
            holder.favorite.setImageResource(R.drawable.ic_favorite_border_24px)
        }
    }

    override fun getItemCount(): Int {
        return this.questions.size
    }

    /**
     * Updates items list of the recycler view according to the specified string
     */
    @SuppressLint("DefaultLocale")
    fun filter(txt: String) {
        var text = txt
        questions.clear()
        if (text.isEmpty()) {
            questions.addAll(itemsCopy)
        } else {
            text = text.toLowerCase()
            for (item in itemsCopy) {
                if (item.question.toLowerCase().contains(text) || item.correctAnswer.toLowerCase().contains(
                        text
                    )
                ) {
                    questions.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    /**
     * Updates items list of the recycler view according to the specified boolean corresponding the "correct" status of the question
     */
    fun filterCorrect(para: Boolean) {
        questions.clear()
        for (item in itemsCopy) {
            if (item.correct == para) {
                questions.add(item)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * Updates items list of the recycler view according to the specified boolean corresponding to the "favorite" status of the question
     */
    fun filterFav(para: Boolean) {
        questions.clear()
        for (item in itemsCopy) {
            if (item.favorite == para) {
                questions.add(item)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * Updates items list of the recycler view according to the specified string corresponding to the question's category
     */
    fun filterCategory(para: String) {
        questions.clear()
        for (item in itemsCopy) {
            if (item.category == para) {
                questions.add(item)
            }
        }
        notifyDataSetChanged()
    }
}