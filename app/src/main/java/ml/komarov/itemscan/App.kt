package ml.komarov.itemscan

import android.app.Application
import androidx.room.Room
import ml.komarov.itemscan.db.AppDatabase


class App : Application() {
    companion object {
        var instance: App? = null
    }

    private var _database: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        _database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    val database get() = _database!!
}