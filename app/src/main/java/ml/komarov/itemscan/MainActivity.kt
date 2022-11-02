package ml.komarov.itemscan

import androidx.fragment.app.Fragment
import ml.komarov.itemscan.fragments.MainFragment


class MainActivity : SingleFragmentActivity() {
    override fun getFragment(): Fragment {
        return MainFragment.newInstance()
    }
}