package ml.komarov.itemscan.fragments.selectors

import androidx.navigation.findNavController

class StockSelectorFragment : BaseSelectorFragment() {
    override fun navigateToAction() {
        val action = StockSelectorFragmentDirections.actionNavStockToNavStockAction(productKey, barcodeData)
        view?.findNavController()?.navigate(action)
    }
}