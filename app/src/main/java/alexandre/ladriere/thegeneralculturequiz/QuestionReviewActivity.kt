package alexandre.ladriere.thegeneralculturequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_question_review.*

class QuestionReviewActivity : AppCompatActivity() {

    private var questionArray: ArrayList<Question> = ArrayList()
    private val adapter = QuestionReviewAdapter(questionArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_review)
        questionArray = intent.getSerializableExtra(QUESTIONS_ARRAY) as ArrayList<Question>
        val recyclerView = findViewById<RecyclerView>(R.id.a_question_review_rcv)
        val layoutManager = LinearLayoutManager(this)
        val adapter = QuestionReviewAdapter(questionArray)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val backB = a_question_review_image_button_back.setOnClickListener {
            this.finish()
        }
    }

    private fun setQuestionList(questionList: ArrayList<Question>) {
        this.questionArray.clear()
        this.questionArray.addAll(questionList)
        adapter.notifyDataSetChanged()
    }
}
