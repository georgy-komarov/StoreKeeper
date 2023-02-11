package ml.komarov.storekeeper.fragments.selectors

import androidx.navigation.findNavController

class PrintSelectorFragment : BaseSelectorFragment() {
    override fun navigateToAction() {
        val action = PrintSelectorFragmentDirections.actionNavPrintToNavPrintAction(productKey, barcodeData)
        view?.findNavController()?.navigate(action)
    }
}