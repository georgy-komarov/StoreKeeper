package ml.komarov.itemscan.db

import androidx.room.*

@Dao
interface CharacteristicDao {
    @Query("SELECT * FROM characteristic")
    fun all(): List<Characteristic>

    @Query("SELECT COUNT(*) FROM characteristic")
    fun count(): Int

    @Query("SELECT * FROM characteristic WHERE ref_key IN (:characteristicKeys)")
    fun loadAllByKeys(characteristicKeys: Array<String>): List<Characteristic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg characteristics: Characteristic)

    @Delete
    fun delete(characteristic: Characteristic)
}