package alexandre.ladriere.thegeneralculturequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
    }

    fun setQuestionList(questionList: ArrayList<Question>) {
        this.questionArray.clear()
        this.questionArray.addAll(questionList)
        adapter.notifyDataSetChanged()
    }
}
