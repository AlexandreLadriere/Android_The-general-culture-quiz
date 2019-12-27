package alexandre.ladriere.thegeneralculturequiz

import alexandre.ladriere.thegeneralculturequiz.questions.AppDatabase
import alexandre.ladriere.thegeneralculturequiz.questions.Question
import alexandre.ladriere.thegeneralculturequiz.questionsreview.QuestionReviewAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.*


class HistoryActivity : AppCompatActivity() {

    private var questionArray: ArrayList<Question> = ArrayList()
    private val adapter =
        QuestionReviewAdapter(
            questionArray
        )
    private val questionDao = AppDatabase.getAppDatabase(this).getQuestionDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setQuestionList(questionDao.getAll().reversed() as ArrayList<Question>)
        val recyclerView = findViewById<RecyclerView>(R.id.a_history_rcv)
        val layoutManager = LinearLayoutManager(this)
        val adapter =
            QuestionReviewAdapter(
                questionArray
            )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val backB = a_history_image_button_back.setOnClickListener {
            this.finish()
        }
        val searchView = a_history_search_view
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText!!)
                return true
            }
        })
    }

    private fun setQuestionList(questionList: ArrayList<Question>) {
        this.questionArray.clear()
        this.questionArray.addAll(questionList)
        adapter.notifyDataSetChanged()
    }
}
