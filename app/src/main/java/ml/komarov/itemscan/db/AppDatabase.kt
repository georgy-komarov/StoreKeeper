package ml.komarov.itemscan.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Barcode::class, Characteristic::class, Product::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun characteristicDao(): CharacteristicDao
    abstract fun barcodeDao(): BarcodeDao
}
