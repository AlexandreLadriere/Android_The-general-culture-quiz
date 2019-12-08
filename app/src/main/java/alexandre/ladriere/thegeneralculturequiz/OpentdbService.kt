package alexandre.ladriere.thegeneralculturequiz

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OpentdbService {
    @GET("api.php")
    fun getQuestions(@Query("amount") amount: String,
                      @Query("category") category: String,
                      @Query("difficulty") difficulty: String,
                      @Query("type") type: String):
            Observable<QuestionModel.Response>

    companion object {
        fun create(): OpentdbService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://opentdb.com/")
                .build()
            return retrofit.create(OpentdbService::class.java)
        }
    }
}