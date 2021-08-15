package com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import com.muhammad89a.amiiboapp.ui.customViews.viewHolder.AmiiboItemViewHolder

class AmiiboListAdapter :
    PagingDataAdapter<AmiiboDTO, AmiiboItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmiiboItemViewHolder {
        return AmiiboItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AmiiboItemViewHolder, position: Int) {
        val amiiboItem = getItem(position) ?: return
        holder.bind(amiiboItem)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<AmiiboDTO> =
            object : DiffUtil.ItemCallback<AmiiboDTO>() {
                override fun areItemsTheSame(oldItem: AmiiboDTO, newItem: AmiiboDTO) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: AmiiboDTO, newItem: AmiiboDTO) =
                    oldItem.head == newItem.head && oldItem.tail == newItem.tail
            }
    }
}
