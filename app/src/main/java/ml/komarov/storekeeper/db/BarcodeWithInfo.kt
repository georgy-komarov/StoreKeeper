package ml.komarov.storekeeper.db

import androidx.room.Embedded

data class BarcodeWithInfo(
    @Embedded
    val barcode: Barcode,

    @Embedded(prefix = "product_")
    val product: Product,

    @Embedded(prefix = "characteristic_")
    val characteristic: Characteristic,
)