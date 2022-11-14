package ml.komarov.itemscan.fragments.selectors

import androidx.navigation.findNavController

class PrintSelectorFragment : BaseSelectorFragment() {
    override fun navigateToAction() {
        val action = PrintSelectorFragmentDirections.actionNavPrintToNavPrintAction(productId)
        view?.findNavController()?.navigate(action)
    }
}