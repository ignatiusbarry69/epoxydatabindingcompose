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

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSecondBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_second, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.selectedItem.observe(viewLifecycleOwner) { selectedItem ->
//            val message = selectedItem?.line1 ?: "No item selected"
//            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//        }
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val items = listOf(
//            MyModel("Item 1"),
//            MyModel("Item 2"),
//            MyModel("Item 3")
//        )
//        val adapter = FluidAdapter(
//            layoutResId = R.layout.epoxy_view_holder_header,
//            onBind = { view, item: MyModel ->
//                item.line1
//            },
//            onClick = { item ->
//                Log.d("a","iueo")
//            }
//        )
//
//        binding.fluidRv.adapter = adapter
//        adapter.submitList(items)
//
//
////        binding.recyclerView.withModels {
////            getUiModel().forEachIndexed { pos, model ->
////                content{
////                    id(pos)
////                    title(model.content.name)
////                }
////            }
////            val a = getUiModel().map {
////                ContentBindingModel_()
////                    .id(it.content.id)
////                    .title(it.content.name)
////            }
////            carousel {
////                id("haha")
////                models(a)
////            }
////        }
//    }

    companion object {

    }
}