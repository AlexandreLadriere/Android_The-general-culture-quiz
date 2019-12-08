package alexandre.ladriere.thegeneralculturequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

const val QUESTIONS_ARRAY = "QUESTIONS_ARRAY"

class MainActivity : AppCompatActivity() {

    private val opentdbServe by lazy {
        OpentdbService.create()
    }
    private var disposable: Disposable? = null
    private var questionsArray: ArrayList<Question> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton = a_main_button_start.setOnClickListener { view: View? ->
            getQuestionsFromAPI()
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun getQuestionsFromAPI(
        pAmount: String = "10",
        pCategory: String = "",
        pDifficulty: String = "",
        pType: String = "multiple"
    ) {
        disposable =
            opentdbServe.getQuestions(
                pAmount,
                pCategory,
                pDifficulty,
                pType
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> getQuestionsFromRequestResult(result) },
                    { error -> Toast.makeText(this, error.message, Toast.LENGTH_LONG).show() }
                )
    }

    private fun getQuestionsFromRequestResult(result: QuestionModel.Response) {
        for (i in 0 until result.results.size) {
            val tmpQuestion = Question(
                category = result.results[i].category,
                type = result.results[i].type,
                difficulty = result.results[i].difficulty,
                question = result.results[i].question,
                correctAnswer = result.results[i].correct_answer,
                proposition1 = result.results[i].incorrect_answers[0],
                proposition2 = result.results[i].incorrect_answers[1],
                proposition3 = result.results[i].incorrect_answers[2]
            )
            questionsArray.add(tmpQuestion)
        }
        startQuestionActivity()
    }

    private fun startQuestionActivity() {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra(QUESTIONS_ARRAY, questionsArray)
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
