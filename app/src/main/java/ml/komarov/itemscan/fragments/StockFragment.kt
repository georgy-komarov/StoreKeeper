package ml.komarov.itemscan.fragments

import androidx.navigation.findNavController

class StockFragment : MainFragment() {
    override fun navigateToAction() {
        val action = StockFragmentDirections.actionNavStockToNavStockAction(productId)
        view?.findNavController()?.navigate(action)
    }
}