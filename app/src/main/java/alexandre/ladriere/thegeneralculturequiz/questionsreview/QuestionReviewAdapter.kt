package alexandre.ladriere.thegeneralculturequiz.questionsreview

import alexandre.ladriere.thegeneralculturequiz.R
import alexandre.ladriere.thegeneralculturequiz.questions.Question
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class QuestionReviewAdapter(private val questions: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionReviewViewHolder {
        val row = LayoutInflater.from(parent.context)
            .inflate(R.layout.question_review_item, parent, false)
        return QuestionReviewViewHolder(
            row
        )
    }

    override fun onBindViewHolder(holder: QuestionReviewViewHolder, position: Int) {
        val (category, type, difficulty, question, correctAnswer, proposition1, proposition2, proposition3, correct) = this.questions[position]
        holder.question.text = question
        holder.answer.text = correctAnswer
        if(correct) {
            holder.itemView.setBackgroundResource(R.drawable.custom_rectangle_correct_cr20_empty)
        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.custom_rectangle_error_cr20_empty)
            holder.answer.text = "Correct answer: $correctAnswer"
        }
    }

    override fun getItemCount(): Int {
        return this.questions.size
    }
}