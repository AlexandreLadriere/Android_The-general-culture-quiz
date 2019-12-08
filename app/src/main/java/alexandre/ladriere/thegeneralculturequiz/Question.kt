package alexandre.ladriere.thegeneralculturequiz

data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val proposition1: String,
    val proposition2: String,
    val proposition3: String,
    val correct: Boolean = false
) {
}