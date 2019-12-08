package alexandre.ladriere.thegeneralculturequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    private lateinit var questionArray: ArrayList<Question>

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

        val categoryTv: TextView = a_question_text_view_category
        val questionTv: TextView = a_question_text_view_question
        val questionNbTv: TextView = a_question_text_view_question_number
        val proposition1B: Button = a_question_button_p1
        val proposition2B: Button = a_question_button_p2
        val proposition3B: Button = a_question_button_p3
        val proposition4B: Button = a_question_button_p4

        categoryTv.text = questionArray[0].category
        questionTv.text = questionArray[0].question
        proposition1B.text = questionArray[0].correctAnswer
        proposition2B.text = questionArray[0].proposition1
        proposition3B.text = questionArray[0].proposition2
        proposition4B.text = questionArray[0].proposition3
        questionNbTv.text = "0/10"

        //TODO check for null size
    }
}
