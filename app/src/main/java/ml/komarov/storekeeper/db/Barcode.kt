package ml.komarov.storekeeper.db

import androidx.room.*

@Entity(
    primaryKeys = ["barcode", "product_key", "characteristic_key"],
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["ref_key"],
        childColumns = ["product_key"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Characteristic::class,
        parentColumns = ["ref_key"],
        childColumns = ["characteristic_key"],
        onDelete = ForeignKey.CASCADE
    )], indices = [Index("product_key"), Index("characteristic_key")]
)
data class Barcode(
    @ColumnInfo(name = "barcode")
    val barcode: String,

    @ColumnInfo(name = "product_key")
    val productKey: String,

    @ColumnInfo(name = "characteristic_key")
    val characteristicKey: String,
)
