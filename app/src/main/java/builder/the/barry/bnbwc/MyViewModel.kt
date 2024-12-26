package builder.the.barry.bnbwc

import android.util.Log
import androidx.databinding.ViewDataBinding
import builder.the.barry.bnbwc.databinding.EpoxyViewHolderHeaderBinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import builder.the.barry.bnbwc.databinding.AItemLayoutBinding
import builder.the.barry.bnbwc.databinding.AnotherItemLayoutBinding
import builder.the.barry.bnbwc.databinding.BItemLayoutBinding

class MyViewModel : ViewModel() {

    private val _items = MutableLiveData<List<MyModel>>()
    val items: LiveData<List<MyModel>> get() = _items

    private val _selectedItem = MutableLiveData<MyModel?>()
    val selectedItem: LiveData<MyModel?> get() = _selectedItem

    private var currentLayoutResId: Int = R.layout.another_item_layout
    val adapter = MutableLiveData<FluidAdapter<MyModel, ViewDataBinding>>()

    /*val adapter = FluidAdapter<MyModel, AnotherItemLayoutBinding>(
        layoutResId = R.layout.another_item_layout,
        onBind = { binding, item ->
            binding.model = item
        },
        onClick = { item -> handleItemClick(item) }
    )*/
    private fun createAdapter(layoutResId: Int): FluidAdapter<MyModel, ViewDataBinding> {
        return FluidAdapter(
            layoutResId = layoutResId,
            onBind = { binding, item ->
                when (binding) {
                    is AnotherItemLayoutBinding -> binding.model = item
                    is AItemLayoutBinding -> binding.model = item
                    is BItemLayoutBinding -> binding.model = item
                }
            },
            onClick = { item -> handleItemClick(item) }
        )
    }


    init {
        _items.value = listOf(
            MyModel("Rp 10.000", "+15 hari","+1 poin"),
            MyModel("Rp 20.000", "+30 hari","+2 poin"),
            MyModel("Rp 30.000", "+45 hari","+3 poin"),
            MyModel("Rp 40.000", "+60 hari","+4 poin"),
            MyModel("Rp 50.000", "+75 hari","+5 poin"),
            MyModel("Rp 60.000", "+90 hari","+6 poin"),
        )
        updateAdapterLayout(R.layout.another_item_layout)
    }
    fun updateAdapterLayout(layoutResId: Int) {
        adapter.value = createAdapter(layoutResId)
    }
    private fun handleItemClick(clickedItem: MyModel) {
        val updatedList = _items.value?.map { item ->
            if (item == clickedItem) {
                item.copy(isSelected = true)
            } else {
                item.copy(isSelected = false)
            }
        } ?: return

        _items.value = updatedList
        _selectedItem.value = clickedItem
    }


    fun getSelectedItemTitle(): String? {
        return _selectedItem.value?.line1
    }

}
