package alexandre.ladriere.thegeneralculturequiz.questions

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Custom DAO
 */
@Dao
interface QuestionDAO {
    /**
     * get all the questions object from the database
     */
    @Query("SELECT * FROM question")
    fun getAll(): List<Question>

    /**
     * Insert the specified question object to the database
     */
    @Insert
    fun insert(vararg question: Question)

    /**
     * Delete the specified question object to the database
     */
    @Delete
    fun delete(question: Question)

    /**
     * Get all questions objects corresponding to the specified category
     */
    @Query("SELECT * FROM question WHERE category LIKE :queryCategory")
    fun findByCategory(queryCategory: String): List<Question>

    /**
     * Get all questions objects corresponding to the specified question
     */
    @Query("SELECT * FROM question WHERE question LIKE :queryQuestion")
    fun findByQuestion(queryQuestion: String): Question

    /**
     * Get all questions objects corresponding to the specified favorite status
     */
    @Query("UPDATE question SET favorite = :queryFav WHERE question = :queryQuestion")
    fun updateQuestionFav(queryQuestion: String, queryFav: Boolean)
}