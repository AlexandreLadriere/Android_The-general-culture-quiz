package alexandre.ladriere.thegeneralculturequiz

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

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
    var correct: Boolean = false
) : Serializable