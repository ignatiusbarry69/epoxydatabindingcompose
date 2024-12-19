package builder.the.barry.bnbwc

import android.util.Log
import builder.the.barry.bnbwc.databinding.EpoxyViewHolderHeaderBinding

// File: MyViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import builder.the.barry.bnbwc.databinding.AnotherItemLayoutBinding

// MyViewModel.kt
class MyViewModel : ViewModel() {

    private val _items = MutableLiveData<List<MyModel>>()
    val items: LiveData<List<MyModel>> get() = _items

    // Track the selected item
    private val _selectedItem = MutableLiveData<MyModel?>()
    val selectedItem: LiveData<MyModel?> get() = _selectedItem

    val adapter = FluidAdapter<MyModel, AnotherItemLayoutBinding>(
        layoutResId = R.layout.another_item_layout,
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
            MyModel("Title 3"),
            MyModel("Title 4"),
            MyModel("Title 5"),
            MyModel("Title 6"),
            MyModel("Title 7"),
        )
    }

    // Update selected item
    private fun handleItemClick(clickedItem: MyModel) {
        val updatedList = _items.value?.map { item ->
            // Set all items' isSelected to false, except the clicked one
            if (item == clickedItem) {
                item.copy(isSelected = true) // Always set the clicked item as selected
            } else {
                item.copy(isSelected = false)
            }
        } ?: return

        _items.value = updatedList

        // Update the selected item explicitly
        _selectedItem.value = clickedItem
    }


    // Method to get the title of the selected item
    fun getSelectedItemTitle(): String? {
        return _selectedItem.value?.line1
    }

    fun onButtonClicked() {
        selectedItem.value?.let {
            Log.d("MyViewModel", "Button clicked with selected item: ${it.line1}")
            // Handle the button click logic here
        } ?: run {
            Log.d("MyViewModel", "No item selected!")
        }
    }

}
