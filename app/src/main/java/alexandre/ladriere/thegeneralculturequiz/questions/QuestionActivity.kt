package alexandre.ladriere.thegeneralculturequiz.questions

import alexandre.ladriere.thegeneralculturequiz.*
import alexandre.ladriere.thegeneralculturequiz.utils.QUESTIONS_ARRAY
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_question.*

/**
 * Custom activity to display all the questions answered during a game
 */
class QuestionActivity : AppCompatActivity() {

    private lateinit var questionArray: ArrayList<Question>
    private var currentPosition: Int = 0
    private var isFav: Boolean = false

    private lateinit var categoryTv: TextView
    private lateinit var questionTv: TextView
    private lateinit var questionNbTv: TextView
    private lateinit var proposition1B: Button
    private lateinit var proposition2B: Button
    private lateinit var proposition3B: Button
    private lateinit var proposition4B: Button
    private lateinit var nextB: Button
    private lateinit var correctB: Button
    private lateinit var favoriteB: ImageButton

    private val questionDao = AppDatabase.getAppDatabase(
        this
    ).getQuestionDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        questionArray = intent.getSerializableExtra(QUESTIONS_ARRAY) as ArrayList<Question>

        categoryTv = a_question_text_view_category
        questionTv = a_question_text_view_question
        questionNbTv = a_question_text_view_question_number
        proposition1B = a_question_button_p1
        proposition2B = a_question_button_p2
        proposition3B = a_question_button_p3
        proposition4B = a_question_button_p4
        nextB = a_question_button_next
        favoriteB = a_question_image_button_fav

        updateQuestionActivity()

        proposition1B.setOnClickListener {
            clickListenerAction(proposition1B)
        }
        proposition2B.setOnClickListener {
            clickListenerAction(proposition2B)
        }
        proposition3B.setOnClickListener {
            clickListenerAction(proposition3B)
        }
        proposition4B.setOnClickListener {
            clickListenerAction(proposition4B)
        }
        nextB.setOnClickListener {
            if (questionDao.findByQuestion(questionArray[currentPosition].question) == null) {
                questionDao.insert(questionArray[currentPosition])
            }
            currentPosition += 1
            updateQuestionActivity()
            resetPropositionBackground()
            enablingButtonClick()
        }
        favoriteB.setOnClickListener {
            if (!isFav) {
                questionArray[currentPosition].favorite = true
                favoriteB.setImageResource(R.drawable.ic_favorite_24px)
                isFav = true
            } else {
                questionArray[currentPosition].favorite = false
                favoriteB.setImageResource(R.drawable.ic_favorite_border_24px)
                isFav = false
            }
        }
    }

    private fun clickListenerAction(button: Button) {
        questionArray[currentPosition].correct =
            checkResponse(button, questionArray[currentPosition])
        disablingButtonClick()
        nextB.visibility = Button.VISIBLE
    }

    private fun disablingButtonClick() {
        proposition1B.isClickable = false
        proposition2B.isClickable = false
        proposition3B.isClickable = false
        proposition4B.isClickable = false
    }

    private fun enablingButtonClick() {
        proposition1B.isClickable = true
        proposition2B.isClickable = true
        proposition3B.isClickable = true
        proposition4B.isClickable = true
    }

    private fun checkResponse(button: Button, question: Question): Boolean {
        var retBool = false
        if (button.text == question.correctAnswer) {
            retBool = true
            button.setBackgroundResource(R.drawable.custom_rectangle_correct_cr20)
        } else {
            button.setBackgroundResource(R.drawable.custom_rectangle_error_cr20)
            correctB.setBackgroundResource(R.drawable.custom_rectangle_correct_cr20)
        }
        return retBool
    }

    private fun updateQuestionActivity() {
        nextB.visibility = Button.INVISIBLE
        favoriteB.setImageResource(R.drawable.ic_favorite_border_24px)
        isFav = false
        if (currentPosition < questionArray.size && currentPosition >= 0) {
            questionNbTv.text = "${currentPosition + 1}/${questionArray.size}"
            categoryTv.text = questionArray[currentPosition].category
            questionTv.text = questionArray[currentPosition].question
            when ((0 until 4).random()) {
                0 -> {
                    proposition1B.text = questionArray[currentPosition].correctAnswer
                    correctB = proposition1B
                    proposition2B.text = questionArray[currentPosition].proposition1
                    proposition3B.text = questionArray[currentPosition].proposition2
                    proposition4B.text = questionArray[currentPosition].proposition3
                }
                1 -> {
                    proposition1B.text = questionArray[currentPosition].proposition1
                    proposition2B.text = questionArray[currentPosition].correctAnswer
                    correctB = proposition2B
                    proposition3B.text = questionArray[currentPosition].proposition2
                    proposition4B.text = questionArray[currentPosition].proposition3
                }
                2 -> {
                    proposition1B.text = questionArray[currentPosition].proposition1
                    proposition2B.text = questionArray[currentPosition].proposition2
                    proposition3B.text = questionArray[currentPosition].correctAnswer
                    correctB = proposition3B
                    proposition4B.text = questionArray[currentPosition].proposition3
                }
                3 -> {
                    proposition1B.text = questionArray[currentPosition].proposition1
                    proposition2B.text = questionArray[currentPosition].proposition2
                    proposition3B.text = questionArray[currentPosition].proposition3
                    proposition4B.text = questionArray[currentPosition].correctAnswer
                    correctB = proposition4B
                }
            }
        } else {
            //this.finish()
            startQuestionReviewActivity()
        }
    }

    private fun resetPropositionBackground() {
        proposition1B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
        proposition2B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
        proposition3B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
        proposition4B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
    }

    private fun startQuestionReviewActivity() {
        val intent = Intent(this, ScoreActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
        intent.putExtra(QUESTIONS_ARRAY, questionArray)
        this.startActivity(intent)
        this.finish()
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
