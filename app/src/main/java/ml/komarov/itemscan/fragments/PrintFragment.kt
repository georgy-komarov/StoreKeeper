package ml.komarov.itemscan.fragments

import androidx.navigation.findNavController

class PrintFragment : MainFragment() {
    override fun navigateToAction() {
        val action = PrintFragmentDirections.actionNavPrintToNavPrintAction(productId)
        view?.findNavController()?.navigate(action)
    }
}