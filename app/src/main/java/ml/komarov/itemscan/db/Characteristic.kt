package ml.komarov.itemscan.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Characteristic(
    @PrimaryKey
    @ColumnInfo(name = "ref_key")
    val refKey: String,

    @ColumnInfo(name = "name")
    val name: String,
)