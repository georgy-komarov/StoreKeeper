package ml.komarov.itemscan.db

import androidx.room.*

@Dao
interface BarcodeDao {
    @Query("SELECT * FROM barcode")
    fun getAll(): List<Barcode>

    @Query("SELECT COUNT(*) FROM barcode")
    fun count(): Int

    @Query("SELECT * FROM barcode WHERE barcode = :barcode")
    fun findByBarcode(barcode: String): List<Barcode>

    @Query(
        "SELECT " +
                "barcode.*, " +
                "product.name AS product_name, product.ref_key AS product_ref_key, product.sku AS product_sku, " +
                "characteristic.name AS characteristic_name, characteristic.ref_key AS characteristic_ref_key " +
        "FROM barcode " +
        "LEFT OUTER JOIN product ON barcode.product_key = product.ref_key " +
        "LEFT OUTER JOIN characteristic ON barcode.characteristic_key = characteristic.ref_key " +
        "WHERE barcode.barcode = :barcode"
    )
    fun findByBarcodeWithInfo(barcode: String): List<BarcodeWithInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg barcodes: Barcode)

    @Delete
    fun delete(barcode: Barcode)
}
