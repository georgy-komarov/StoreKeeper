package ml.komarov.itemscan.fragments.selectors

import androidx.navigation.findNavController

class PricesSelectorFragment : BaseSelectorFragment() {
    override fun navigateToAction() {
        val action = PricesSelectorFragmentDirections.actionNavPricesToNavPricesAction(productId)
        view?.findNavController()?.navigate(action)
    }
}