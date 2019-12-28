package alexandre.ladriere.thegeneralculturequiz

import alexandre.ladriere.thegeneralculturequiz.questions.AppDatabase
import alexandre.ladriere.thegeneralculturequiz.questions.Question
import alexandre.ladriere.thegeneralculturequiz.questionsreview.QuestionReviewAdapter
import alexandre.ladriere.thegeneralculturequiz.utils.ViewAnimation
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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
            Toast.makeText(this, "Sort", Toast.LENGTH_SHORT).show()
        }
        fabFav.setOnClickListener { view ->
            Toast.makeText(this, "Fav", Toast.LENGTH_SHORT).show()
        }
        fabCorrect.setOnClickListener { view ->
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
        }
        fabFalse.setOnClickListener { view ->
            Toast.makeText(this, "False", Toast.LENGTH_SHORT).show()
        }
        fabClear.setOnClickListener { view ->
            Toast.makeText(this, "Clear", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setQuestionList(questionList: ArrayList<Question>) {
        this.questionArray.clear()
        this.questionArray.addAll(questionList)
        adapter.notifyDataSetChanged()
    }
}
