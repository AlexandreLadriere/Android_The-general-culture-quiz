package alexandre.ladriere.thegeneralculturequiz

import alexandre.ladriere.thegeneralculturequiz.questions.AppDatabase
import alexandre.ladriere.thegeneralculturequiz.questions.Question
import alexandre.ladriere.thegeneralculturequiz.questionsreview.QuestionReviewAdapter
import alexandre.ladriere.thegeneralculturequiz.utils.SpinnerItem
import alexandre.ladriere.thegeneralculturequiz.utils.SpinnerItemArrayAdapter
import alexandre.ladriere.thegeneralculturequiz.utils.ViewAnimation
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
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
    private var isRotate = false
    private lateinit var categorySpinner: Spinner

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
        searchView.setOnClickListener {
            val imm = baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            searchView.isFocusable = true
            searchView.isIconified = false
            searchView.clearFocus()
            searchView.requestFocusFromTouch()
        }
        categorySpinner = a_history_category_spinner
        initCategorySpinner()
        val fab = a_history_fab
        val fabSort = a_history_fab_sort
        val fabFav = a_history_fab_fav
        val fabCorrect = a_history_fab_correct
        val fabFalse = a_history_fab_false
        val fabClear = a_history_fab_clear
        ViewAnimation().init(fabSort)
        ViewAnimation().init(fabFav)
        ViewAnimation().init(fabCorrect)
        ViewAnimation().init(fabFalse)
        ViewAnimation().init(fabClear)
        fab.setOnClickListener { view ->
            isRotate = ViewAnimation().rotateFab(view, !isRotate)
            if(isRotate){
                ViewAnimation().showIn(fabSort)
                ViewAnimation().showIn(fabFav)
                ViewAnimation().showIn(fabCorrect)
                ViewAnimation().showIn(fabFalse)
                ViewAnimation().showIn(fabClear)
            }else{
                ViewAnimation().showOut(fabSort)
                ViewAnimation().showOut(fabFav)
                ViewAnimation().showOut(fabCorrect)
                ViewAnimation().showOut(fabFalse)
                ViewAnimation().showOut(fabClear)
            }
        }
        fabSort.setOnClickListener { view ->
            categorySpinner.performClick()
        }
        fabFav.setOnClickListener { view ->
            Toast.makeText(this, "Fav", Toast.LENGTH_SHORT).show()
        }
        fabCorrect.setOnClickListener { view ->
            adapter.filterCorrect(true)
        }
        fabFalse.setOnClickListener { view ->
            adapter.filterCorrect(false)
        }
        fabClear.setOnClickListener { view ->
            adapter.filter("")
        }

        categorySpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                val category =
                    categorySpinner.adapter.getItem(position) as SpinnerItem
                if(category.code == "") {
                    adapter.filter(category.code)
                }
                else {
                    Toast.makeText(baseContext, category.title, Toast.LENGTH_SHORT).show()
                    adapter.filterCategory(category.title)
                }
            }
        }

    }

    private fun setQuestionList(questionList: ArrayList<Question>) {
        this.questionArray.clear()
        this.questionArray.addAll(questionList)
        adapter.notifyDataSetChanged()
    }



    private fun initCategorySpinner() {
        categorySpinner.adapter =
            SpinnerItemArrayAdapter(
                this, listOf(
                    SpinnerItem(
                        "",
                        "Any Category"
                    ),
                    SpinnerItem(
                        "9",
                        "General Knowledge"
                    ),
                    SpinnerItem(
                        "10",
                        "Entertainment: Books"
                    ),
                    SpinnerItem(
                        "11",
                        "Entertainment: Film"
                    ),
                    SpinnerItem(
                        "12",
                        "Entertainment: Music"
                    ),
                    SpinnerItem(
                        "13",
                        "Entertainment: Musicals & Theatres"
                    ),
                    SpinnerItem(
                        "14",
                        "Entertainment: Television"
                    ),
                    SpinnerItem(
                        "15",
                        "Entertainment: Video Games"
                    ),
                    SpinnerItem(
                        "16",
                        "Entertainment: Board Games"
                    ),
                    SpinnerItem(
                        "17",
                        "Science & Nature"
                    ),
                    SpinnerItem(
                        "18",
                        "Science: Computers"
                    ),
                    SpinnerItem(
                        "19",
                        "Science: Mathematics"
                    ),
                    SpinnerItem(
                        "20",
                        "Mythology"
                    ),
                    SpinnerItem(
                        "21",
                        "Sports"
                    ),
                    SpinnerItem(
                        "22",
                        "Geography"
                    ),
                    SpinnerItem(
                        "23",
                        "History"
                    ),
                    SpinnerItem(
                        "24",
                        "Politics"
                    ),
                    SpinnerItem(
                        "25",
                        "Art"
                    ),
                    SpinnerItem(
                        "26",
                        "Celebrities"
                    ),
                    SpinnerItem(
                        "27",
                        "Animals"
                    ),
                    SpinnerItem(
                        "28",
                        "Vehicles"
                    ),
                    SpinnerItem(
                        "29",
                        "Entertainment: Comics"
                    ),
                    SpinnerItem(
                        "30",
                        "Science: Gadgets"
                    ),
                    SpinnerItem(
                        "31",
                        "Entertainment: Japanese Anime & Manga"
                    ),
                    SpinnerItem(
                        "32",
                        "Entertainment: Cartoon & Animations"
                    )
                )
            )
    }
}
