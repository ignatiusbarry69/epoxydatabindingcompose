package builder.the.barry.bnbwc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import builder.the.barry.bnbwc.databinding.FragmentMainBinding
import builder.the.barry.bnbwc.databinding.FragmentSecondBinding
import com.airbnb.epoxy.carousel
class SecondFragment : Fragment() {
    private val viewModel: MyViewModel by viewModels()
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_second, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe selectedItem LiveData in ViewModel
//        viewModel.selectedItem.observe(viewLifecycleOwner) { selectedItem ->
//            val message = selectedItem?.line1 ?: "No item selected"
//            // Handle UI-related tasks (like Toast) here
//            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//        }

        // Handle button click
        binding.btn.setOnClickListener {
            val selectedTitle = viewModel.getSelectedItemTitle()
            if (selectedTitle != null) {
                Toast.makeText(requireContext(), "Selected: $selectedTitle", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No item selected", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Ensure to clear binding in onDestroyView to avoid memory leaks
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

    }
}