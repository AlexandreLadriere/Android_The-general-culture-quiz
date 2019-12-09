package alexandre.ladriere.thegeneralculturequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        //TODO check for null size
        //TODO Parsing function
    }

    private fun clickListenerAction(button: Button) {
        questionArray[currentPosition].correct =
            checkResponse(button, questionArray[currentPosition])
        currentPosition += 1
        updateQuestionActivity()
        resetPropositionBackground()
    }

    private fun checkResponse(button: Button, question: Question): Boolean {
        var retBool = false
        if (button.text == question.correctAnswer) {
            retBool = true
            button.setBackgroundResource(R.drawable.custom_rectangle_correct_cr20)
        } else {
            button.setBackgroundResource(R.drawable.custom_rectangle_error_cr20)
        }
        return retBool
    }

    private fun updateQuestionActivity() {
        if (currentPosition < questionArray.size && currentPosition >= 0) {
            questionNbTv.text = "${currentPosition+1}/10"
            categoryTv.text = questionArray[currentPosition].category
            questionTv.text = removeSpecialCharFromString(questionArray[currentPosition].question)
            when ((0 until 4).random()) {
                0 -> {
                    proposition1B.text = removeSpecialCharFromString(questionArray[currentPosition].correctAnswer)
                    proposition2B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition1)
                    proposition3B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition2)
                    proposition4B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition3)
                }
                1 -> {
                    proposition1B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition1)
                    proposition2B.text = removeSpecialCharFromString(questionArray[currentPosition].correctAnswer)
                    proposition3B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition2)
                    proposition4B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition3)
                }
                2 -> {
                    proposition1B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition1)
                    proposition2B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition2)
                    proposition3B.text = removeSpecialCharFromString(questionArray[currentPosition].correctAnswer)
                    proposition4B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition3)
                }
                3 -> {
                    proposition1B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition1)
                    proposition2B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition2)
                    proposition3B.text = removeSpecialCharFromString(questionArray[currentPosition].proposition3)
                    proposition4B.text = removeSpecialCharFromString(questionArray[currentPosition].correctAnswer)
                }
            }
        }
        else {
            this.finish()
        }
    }

    private fun resetPropositionBackground() {
        proposition1B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
        proposition2B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
        proposition3B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
        proposition4B.setBackgroundResource(R.drawable.custom_rectangle_text_second_cr20_empty)
    }
}
