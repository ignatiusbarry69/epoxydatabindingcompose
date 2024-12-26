package builder.the.barry.bnbwc

import android.util.Log
import builder.the.barry.bnbwc.databinding.EpoxyViewHolderHeaderBinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import builder.the.barry.bnbwc.databinding.AnotherItemLayoutBinding

class MyViewModel : ViewModel() {

    private val _items = MutableLiveData<List<MyModel>>()
    val items: LiveData<List<MyModel>> get() = _items

    private val _selectedItem = MutableLiveData<MyModel?>()
    val selectedItem: LiveData<MyModel?> get() = _selectedItem

    val adapter = FluidAdapter<MyModel, AnotherItemLayoutBinding>(
        layoutResId = R.layout.another_item_layout,
        onBind = { binding, item ->
            binding.model = item
        },
        onClick = { item -> handleItemClick(item) }
    )

    init {
        _items.value = listOf(
            MyModel("Rp 10.000", "+15 hari","+1 poin"),
            MyModel("Rp 20.000", "+30 hari","+2 poin"),
            MyModel("Rp 30.000", "+45 hari","+3 poin"),
            MyModel("Rp 40.000", "+60 hari","+4 poin"),
            MyModel("Rp 50.000", "+75 hari","+5 poin"),
            MyModel("Rp 60.000", "+90 hari","+6 poin"),
        )
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

    fun onButtonClicked() {
        selectedItem.value?.let {
            Log.d("MyViewModel", "Button clicked with selected item: ${it.line1}")
        } ?: run {
            Log.d("MyViewModel", "No item selected!")
        }
    }

}
