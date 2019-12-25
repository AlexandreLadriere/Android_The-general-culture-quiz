package alexandre.ladriere.thegeneralculturequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    private var questionArray: ArrayList<Question> = ArrayList()
    private val adapter = QuestionReviewAdapter(questionArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val questionDao = AppDatabase.getAppDatabase(this).getQuestionDao()
        val recyclerView = findViewById<RecyclerView>(R.id.a_history_rcv)
        val layoutManager = LinearLayoutManager(this)
        val adapter = QuestionReviewAdapter(questionArray)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val backB = a_history_image_button_back.setOnClickListener {
            this.finish()
        }
        setQuestionList(questionDao.getAll() as ArrayList<Question>)
    }

    private fun setQuestionList(questionList: ArrayList<Question>) {
        this.questionArray.clear()
        this.questionArray.addAll(questionList)
        adapter.notifyDataSetChanged()
    }
}
