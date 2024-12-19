package builder.the.barry.bnbwc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class FluidAdapter<T, VB : ViewDataBinding>(
    private val layoutResId: Int,
    private val onBind: (binding: VB, item: T) -> Unit,
    private val onClick: ((item: T) -> Unit)? = null
) : RecyclerView.Adapter<FluidAdapter.DataBindingViewHolder<T, VB>>() {

    private val items = mutableListOf<T>()

    fun submitList(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T, VB> {
        val binding = DataBindingUtil.inflate<VB>(
            LayoutInflater.from(parent.context),
            layoutResId,
            parent,
            false
        )
        return DataBindingViewHolder(binding, onBind, onClick)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T, VB>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class DataBindingViewHolder<T, VB : ViewDataBinding>(
        private val binding: VB,
        private val onBind: (binding: VB, item: T) -> Unit,
        private val onClick: ((item: T) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T) {
            onBind(binding, item)
            binding.root.setOnClickListener {
                onClick?.invoke(item)
            }
            binding.executePendingBindings()
        }
    }
}

//class FluidAdapter<T>(
//    private val layoutResId: Int,
//    private val onBind: (view: View, item: T) -> Unit,
//    private val onClick: ((item: T) -> Unit)? = null
//) : RecyclerView.Adapter<FluidAdapter.DynamicViewHolder<T>>() {
//
//    private val items = mutableListOf<T>()
//
//    fun submitList(newItems: List<T>) {
//        items.clear()
//        items.addAll(newItems)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicViewHolder<T> {
//        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
//        return DynamicViewHolder(view, onBind, onClick)
//    }
//
//    override fun onBindViewHolder(holder: DynamicViewHolder<T>, position: Int) {
//        holder.bind(items[position])
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    class DynamicViewHolder<T>(
//        private val view: View,
//        private val onBind: (view: View, item: T) -> Unit,
//        private val onClick: ((item: T) -> Unit)?
//    ) : RecyclerView.ViewHolder(view) {
//
//        fun bind(item: T) {
//            onBind(view, item)
//            view.setOnClickListener {
//                onClick?.invoke(item)
//            }
//        }
//    }
//}
