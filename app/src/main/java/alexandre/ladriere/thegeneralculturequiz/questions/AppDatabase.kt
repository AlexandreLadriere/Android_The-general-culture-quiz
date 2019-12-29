package alexandre.ladriere.thegeneralculturequiz.questions

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Class to represent the app DB
 */
@Database(entities = [Question::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Abstract function to get all the question from one request to the API used
     */
    abstract fun getQuestionDao(): QuestionDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        /**
         * Retrieve the app database
         */
        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "db"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as AppDatabase
        }
    }
}
