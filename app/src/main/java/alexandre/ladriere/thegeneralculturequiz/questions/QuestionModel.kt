package alexandre.ladriere.thegeneralculturequiz.questions

/**
 * Custom object to store responses of the API used
 */
object QuestionModel {
    /**
     * Custom class to retrieve the overall response
     */
    data class Response(val response_code: Int, val results: Array<Question>)

    /**
     * Custom class to retrieve all the information of one question
     */
    data class Question(
        val category: String,
        val type: String,
        val difficulty: String,
        val question: String,
        val correct_answer: String,
        val incorrect_answers: Array<String>
    )
}