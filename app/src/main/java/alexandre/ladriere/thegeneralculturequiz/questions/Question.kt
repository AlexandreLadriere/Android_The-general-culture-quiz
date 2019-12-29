package alexandre.ladriere.thegeneralculturequiz.questions

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Custom object to represent a Question according to the OpenTriviaDB API response
 */
@Entity
data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    @PrimaryKey val question: String,
    val correctAnswer: String,
    val proposition1: String,
    val proposition2: String,
    val proposition3: String,
    var correct: Boolean = false,
    var favorite: Boolean = false
) : Serializable