package alexandre.ladriere.thegeneralculturequiz

import alexandre.ladriere.thegeneralculturequiz.questions.Question
import alexandre.ladriere.thegeneralculturequiz.questionsreview.QuestionReviewActivity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    private var questionArray: ArrayList<Question> = ArrayList()
    private lateinit var msgTV: TextView
    private lateinit var scoreTV: TextView
    private lateinit var questionReviewB: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        questionArray = intent.getSerializableExtra(QUESTIONS_ARRAY) as ArrayList<Question>
        msgTV = a_score_text_view_msg
        scoreTV = a_score_text_view_score
        questionReviewB = a_score_button_question_review
        val homeB = a_score_button_home
        val restartB = a_score_button_replay
        displayScore()
        questionReviewB.setOnClickListener {
            val intent = Intent(this, QuestionReviewActivity::class.java)
            intent.putExtra(QUESTIONS_ARRAY, questionArray)
            this.startActivity(intent)
        }
        homeB.setOnClickListener {
            this.finish()
        }
        restartB.setOnClickListener {
        }
    }

    private fun calculateScore(): Int {
        var score: Int = 0
        for (question in questionArray) {
            if (question.correct) {
                score += 1
            }
        }
        return score
    }

    private fun displayScore() {
        val score = calculateScore()
        scoreTV.text = "$score/${questionArray.size}"
        if (score < questionArray.size / 2) {
            scoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorError))
            if (score <= questionArray.size / 4) {
                msgTV.text = resources.getString(R.string.score_msg1)
            } else {
                msgTV.text = resources.getString(R.string.score_msg2)
            }
        } else {
            scoreTV.setTextColor(ContextCompat.getColor(this, R.color.colorCorrect))
            if (score <= questionArray.size / 2 + questionArray.size / 4) {
                msgTV.text = resources.getString(R.string.score_msg3)
            } else {
                msgTV.text = resources.getString(R.string.score_msg4)
            }
        }
    }
}
