package builder.the.barry.bnbwc
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import builder.the.barry.bnbwc.databinding.FragmentMainBinding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val navController = findNavController()


        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
            var currentMenu by rememberSaveable { mutableStateOf(R.id.homeFragment) }

                when (currentMenu) {
                    R.id.homeFragment -> HomeScreen( navController = navController )
                    R.id.myTicketFragment -> MyTicketScreen()
                    R.id.accountFragment -> AccountScreen()
                    else -> HomeScreen( navController = navController )
                }

                binding.bottomNavigation.setOnNavigationItemSelectedListener { menu ->
                    currentMenu = menu.itemId
                    true
                }

            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.bottomNavigation.selectedItemId != R.id.homeFragment) {
                binding.bottomNavigation.selectedItemId = R.id.homeFragment
                Log.i("barry-dev","nope")
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }

        return binding.root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
