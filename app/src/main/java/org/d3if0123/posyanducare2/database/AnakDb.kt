package org.d3if0123.posyanducare2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0123.posyanducare2.model.Anak

@Database(entities = [Anak::class], version = 1, exportSchema = false)
abstract class AnakDb : RoomDatabase() {

    abstract val dao : AnakDao

    companion object {
        @Volatile
        private var INSTANCE: AnakDb? = null

        fun getInstance(context: Context): AnakDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnakDb::class.java,
                        "anak.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}