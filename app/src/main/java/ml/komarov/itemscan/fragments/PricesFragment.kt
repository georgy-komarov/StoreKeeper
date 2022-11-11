package ml.komarov.itemscan.fragments

import androidx.navigation.findNavController

class PricesFragment : MainFragment() {
    override fun navigateToAction() {
        val action = PricesFragmentDirections.actionNavPricesToNavPricesAction(productId)
        view?.findNavController()?.navigate(action)
    }
}