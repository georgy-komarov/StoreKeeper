package ml.komarov.storekeeper.db

import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun all(): List<Product>

    @Query("SELECT COUNT(*) FROM product")
    fun count(): Int

    @Query("SELECT * FROM product WHERE ref_key = :productKey LIMIT 1")
    fun findByKey(productKey: String): Product

    @Query("SELECT * FROM product WHERE ref_key IN (:productKeys)")
    fun loadAllByKeys(productKeys: Array<String>): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg products: Product)

    @Delete
    fun delete(product: Product)
}