package builder.the.barry.bnbwc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import builder.the.barry.bnbwc.databinding.FragmentSecondBinding

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

        binding.btn.setOnClickListener {
            val selectedTitle = viewModel.getSelectedItemTitle()
            if (selectedTitle != null) {
                Toast.makeText(requireContext(), "Selected: $selectedTitle", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No item selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnA.setOnClickListener {
            viewModel.updateAdapterLayout(R.layout.a_item_layout)
        }

        binding.btnB.setOnClickListener {
            viewModel.updateAdapterLayout(R.layout.b_item_layout)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {

    }
}