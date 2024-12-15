package builder.the.barry.bnbwc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import builder.the.barry.bnbwc.databinding.FragmentMainBinding
import builder.the.barry.bnbwc.databinding.FragmentSecondBinding
import com.airbnb.epoxy.carousel

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.withModels {
            getUiModel().forEachIndexed { pos, model ->
                content{
                    id(pos)
                    title(model.content.name)
                }
            }
            val a = getUiModel().map {
                ContentBindingModel_()
                    .id(it.content.id)
                    .title(it.content.name)
            }
            carousel {
                id("haha")
                models(a)
            }
        }
    }

    companion object {

    }
}