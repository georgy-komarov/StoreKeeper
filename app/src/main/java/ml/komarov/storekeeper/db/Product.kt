package ml.komarov.storekeeper.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "ref_key")
    val refKey: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "sku")
    val sku: String,
)