package ml.komarov.storekeeper.fragments.selectors

import androidx.navigation.findNavController

class PricesSelectorFragment : BaseSelectorFragment() {
    override fun navigateToAction() {
        val action = PricesSelectorFragmentDirections.actionNavPricesToNavPricesAction(productKey, barcodeData)
        view?.findNavController()?.navigate(action)
    }
}