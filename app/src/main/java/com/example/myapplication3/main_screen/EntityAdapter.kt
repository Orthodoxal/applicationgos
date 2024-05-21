package com.example.myapplication3.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.databinding.EntityItemBinding
import com.example.myapplication3.db.EntityEntity

class EntityEntityDiffUtilCallback(
    private val oldEventListDataModel: List<EntityEntity>,
    private val newEventListDataModel: List<EntityEntity>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldEventListDataModel.size

    override fun getNewListSize(): Int = newEventListDataModel.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldEventListDataModel[oldItemPosition]
        val newEvent = newEventListDataModel[newItemPosition]
        return oldEvent.id == newEvent.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEvent = oldEventListDataModel[oldItemPosition]
        val newEvent = newEventListDataModel[newItemPosition]
        return oldEvent == newEvent
    }

}

class EntityAdapter(
    private val onItemClick: (EntityEntity) -> Unit,
) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {
    var entitiesList: List<EntityEntity> = emptyList()
        set(value) {
            val diffUtilCallback = EntityEntityDiffUtilCallback(field, value)
            val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffUtilResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EntityItemBinding.inflate(inflater, parent, false)
        return EntityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        with(holder.binding) {
            itemName.text = entitiesList[position].name
            itemPrice.text = entitiesList[position].price.toString()
            // FIXME (10: Добавляем логику инициализации для элемента на экране)
            confirmCheckbox.isChecked = entitiesList[position].contractConfirmed
            root.setOnClickListener {
                onItemClick(entitiesList[position])
            }
        }
    }

    override fun getItemCount() = entitiesList.size

    class EntityViewHolder(val binding: EntityItemBinding) : RecyclerView.ViewHolder(binding.root)
}
