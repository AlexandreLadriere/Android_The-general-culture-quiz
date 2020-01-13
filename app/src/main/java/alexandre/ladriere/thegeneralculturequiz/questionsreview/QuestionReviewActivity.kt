package alexandre.ladriere.thegeneralculturequiz.questionsreview

import alexandre.ladriere.thegeneralculturequiz.R
import alexandre.ladriere.thegeneralculturequiz.questions.AppDatabase
import alexandre.ladriere.thegeneralculturequiz.questions.Question
import alexandre.ladriere.thegeneralculturequiz.utils.QUESTIONS_ARRAY
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_question_review.*

class QuestionReviewActivity : AppCompatActivity() {

    private var questionArray: ArrayList<Question> = ArrayList()
    private var adapter =
        QuestionReviewAdapter(
            questionArray,
            ::favQuestion
        )
    private val questionDao = AppDatabase.getAppDatabase(
        this
    ).getQuestionDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_review)
        questionArray = intent.getSerializableExtra(QUESTIONS_ARRAY) as ArrayList<Question>
        val recyclerView = findViewById<RecyclerView>(R.id.a_question_review_rcv)
        val layoutManager = LinearLayoutManager(this)
        adapter =
            QuestionReviewAdapter(
                questionArray,
                ::favQuestion
            )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val backB = a_question_review_image_button_back.setOnClickListener {
            this.finish()
        }
    }

    private fun favQuestion(position: Int) {
        val tmpQuestion = this.questionArray[position]
        tmpQuestion.favorite = !this.questionArray[position].favorite
        questionDao.updateQuestionFav(tmpQuestion.question, tmpQuestion.favorite)
        questionArray[position] = tmpQuestion
        adapter.notifyItemChanged(position)
    }


    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
}
