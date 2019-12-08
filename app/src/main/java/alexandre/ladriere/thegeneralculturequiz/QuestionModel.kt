package alexandre.ladriere.thegeneralculturequiz

object QuestionModel {
    data class Response(val response_code: Int, val results: Results)
    data class Results(val questions: Array<Question>)
    data class Question(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: IncorrectAnswers
    )

    data class IncorrectAnswers(val p0: String, val p1: String, val p2: String)
}