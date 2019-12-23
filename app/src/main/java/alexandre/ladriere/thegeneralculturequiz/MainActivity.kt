package alexandre.ladriere.thegeneralculturequiz

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var categorySpinner: Spinner
    private lateinit var difficultySpinner: Spinner
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        categorySpinner = category_spinner
        difficultySpinner = difficulty_spinner
        seekBar = a_main_seek_bar_question_count
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                a_main_text_view_quesion_count.text = "$progress Questions"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        initCategorySpinner()
        initDifficultySpinner()
        val startButton = a_main_button_start.setOnClickListener { view: View? ->
            questionsArray.clear()
            val category = categorySpinner.adapter.getItem(categorySpinner.selectedItemPosition) as SpinnerItem
            val difficulty = difficultySpinner.adapter.getItem(difficultySpinner.selectedItemPosition) as SpinnerItem
            getQuestionsFromAPI(seekBar.progress.toString(), category.code, difficulty.code)
        }
        val successButton = a_main_image_button_success.setOnClickListener {
            Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
        val infoButton = a_main_image_button_info.setOnClickListener {
            Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
        }
        val historyButton = a_main_image_button_history.setOnClickListener {
            Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show()
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
        when (result.response_code) {
            0 -> {
                for (i in 0 until result.results.size) {
                    val tmpQuestion = Question(
                        category = result.results[i].category,
                        type = result.results[i].type,
                        difficulty = result.results[i].difficulty,
                        question = removeSpecialCharFromString(result.results[i].question),
                        correctAnswer = removeSpecialCharFromString(result.results[i].correct_answer),
                        proposition1 = removeSpecialCharFromString(result.results[i].incorrect_answers[0]),
                        proposition2 = removeSpecialCharFromString(result.results[i].incorrect_answers[1]),
                        proposition3 = removeSpecialCharFromString(result.results[i].incorrect_answers[2])
                    )
                    questionsArray.add(tmpQuestion)
                }
                startQuestionActivity()
            }
            1 -> {
                Toast.makeText(
                    this,
                    "${resources.getString(R.string.no_results)}: ${resources.getString(R.string.no_results_msg)}",
                    Toast.LENGTH_LONG
                ).show()
            }
            2 -> {
                Toast.makeText(
                    this,
                    "${resources.getString(R.string.invalid_parameter)}: ${resources.getString(R.string.invalid_parameter_msg)}",
                    Toast.LENGTH_LONG
                ).show()
            }
            3 -> {
                Toast.makeText(
                    this,
                    "${resources.getString(R.string.token_not_found)}: ${resources.getString(R.string.token_not_found_msg)}",
                    Toast.LENGTH_LONG
                ).show()
            }
            4 -> {
                Toast.makeText(
                    this,
                    "${resources.getString(R.string.token_empty)}: ${resources.getString(R.string.token_empty_msg)}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun startQuestionActivity() {
        val intent = Intent(this, QuestionActivity::class.java)
        intent.putExtra(QUESTIONS_ARRAY, questionsArray)
        this.startActivity(intent)
        //this.finish()
    }

    private fun initDifficultySpinner() {
        difficultySpinner.adapter =
            SpinnerItemArrayAdapter(this, listOf(
                SpinnerItem("", "Any Difficulty"),
                SpinnerItem("easy", "Easy"),
                SpinnerItem("medium", "Medium"),
                SpinnerItem("hard", "Hard")
            ))
    }

    private fun initCategorySpinner() {
        categorySpinner.adapter =
            SpinnerItemArrayAdapter(this, listOf(
                SpinnerItem("", "Any Category"),
                SpinnerItem("9", "General Knowledge"),
                SpinnerItem("10", "Entertainment: Books"),
                SpinnerItem("11", "Entertainment: Film"),
                SpinnerItem("12", "Entertainment: Music"),
                SpinnerItem("13", "Entertainment: Musicals & Theatres"),
                SpinnerItem("14", "Entertainment: Television"),
                SpinnerItem("15", "Entertainment: Video Games"),
                SpinnerItem("16", "Entertainment: Board Games"),
                SpinnerItem("17", "Science & Nature"),
                SpinnerItem("18", "Science: Computers"),
                SpinnerItem("19", "Science: Mathematics"),
                SpinnerItem("20", "Mythology"),
                SpinnerItem("21", "Sports"),
                SpinnerItem("22", "Geography"),
                SpinnerItem("23", "History"),
                SpinnerItem("24", "Politics"),
                SpinnerItem("25", "Art"),
                SpinnerItem("26", "Celebrities"),
                SpinnerItem("27", "Animals"),
                SpinnerItem("28", "Vehicles"),
                SpinnerItem("29", "Entertainment: Comics"),
                SpinnerItem("30", "Science: Gadgets"),
                SpinnerItem("31", "Entertainment: Japanese Anime & Manga"),
                SpinnerItem("32", "Entertainment: Cartoon & Animations")
                ))
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
