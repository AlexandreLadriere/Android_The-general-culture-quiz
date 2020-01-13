package alexandre.ladriere.thegeneralculturequiz

import alexandre.ladriere.thegeneralculturequiz.questions.Question
import alexandre.ladriere.thegeneralculturequiz.questionsreview.QuestionReviewActivity
import alexandre.ladriere.thegeneralculturequiz.utils.QUESTIONS_ARRAY
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_score.*

/**
 * Custom activity to display player's score at the end of a game
 */
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
            replay()
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

    private fun replay() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
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
