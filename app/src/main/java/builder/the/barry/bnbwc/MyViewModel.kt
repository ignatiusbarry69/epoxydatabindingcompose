package builder.the.barry.bnbwc

import builder.the.barry.bnbwc.databinding.EpoxyViewHolderHeaderBinding

// File: MyViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
// MyViewModel.kt
class MyViewModel : ViewModel() {

    private val _items = MutableLiveData<List<MyModel>>()
    val items: LiveData<List<MyModel>> get() = _items

    // Track the selected item
    private val _selectedItem = MutableLiveData<MyModel?>()
    val selectedItem: LiveData<MyModel?> get() = _selectedItem

    val adapter = FluidAdapter<MyModel, EpoxyViewHolderHeaderBinding>(
        layoutResId = R.layout.epoxy_view_holder_header,
        onBind = { binding, item ->
            binding.title = item.line1
            binding.isSelected = item.isSelected
        },
        onClick = { item -> handleItemClick(item) }
    )

    init {
        // Sample data
        _items.value = listOf(
            MyModel("Title 1"),
            MyModel("Title 2"),
            MyModel("Title 3")
        )
    }

    // Update selected item
    private fun handleItemClick(clickedItem: MyModel) {
        val updatedList = _items.value?.map {
            if (it == clickedItem) it.copy(isSelected = !it.isSelected)
            else it.copy(isSelected = false)
        } ?: return

        _items.value = updatedList

        // Update the selected item
        _selectedItem.value = if (clickedItem.isSelected) clickedItem else null
    }

    // Method to get the title of the selected item
    fun getSelectedItemTitle(): String? {
        return _selectedItem.value?.line1
    }
}
