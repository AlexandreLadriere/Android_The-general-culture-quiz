package alexandre.ladriere.thegeneralculturequiz.questions

import alexandre.ladriere.thegeneralculturequiz.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    private lateinit var questionArray: ArrayList<Question>
    private var currentPosition: Int = 0

    private lateinit var categoryTv: TextView
    private lateinit var questionTv: TextView
    private lateinit var questionNbTv: TextView
    private lateinit var proposition1B: Button
    private lateinit var proposition2B: Button
    private lateinit var proposition3B: Button
    private lateinit var proposition4B: Button
    private lateinit var nextB: Button
    private lateinit var correctB: Button

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
            currentPosition += 1
            updateQuestionActivity()
            resetPropositionBackground()
            enablingButtonClick()
        }
    }

    private fun clickListenerAction(button: Button) {
        questionArray[currentPosition].correct =
            checkResponse(button, questionArray[currentPosition])
        if(questionDao.findByQuestion(questionArray[currentPosition].question) == null) {
            questionDao.insert(questionArray[currentPosition])
        }
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
        intent.putExtra(QUESTIONS_ARRAY, questionArray)
        this.startActivity(intent)
        this.finish()
    }
}
