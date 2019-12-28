package alexandre.ladriere.thegeneralculturequiz.questions

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDAO {
    @Query("SELECT * FROM question")
    fun getAll(): List<Question>

    @Insert
    fun insert(vararg question: Question)

    @Delete
    fun delete(question: Question)

    @Query("SELECT * FROM question WHERE category LIKE :queryCategory")
    fun findByCategory(queryCategory: String): List<Question>

    @Query("SELECT * FROM question WHERE question LIKE :queryQuestion")
    fun findByQuestion(queryQuestion: String): Question

    @Query("UPDATE question SET favorite = :queryFav WHERE question = :queryQuestion")
    fun updateQuestionFav(queryQuestion: String, queryFav: Boolean)
}