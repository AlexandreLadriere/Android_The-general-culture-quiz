package alexandre.ladriere.thegeneralculturequiz.questions

object QuestionModel {
    data class Response(val response_code: Int, val results: Array<Question>)
    data class Question(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: Array<String>
    )
}