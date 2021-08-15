package com.muhammad89a.amiiboapp.ui.customViews.viewHolder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.muhammad89a.amiiboapp.data.model.AmiiboDTO
import com.muhammad89a.amiiboapp.databinding.AmiibosItemListBinding
import com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid.AmiibosGridFragmentDirections
import com.muhammad89a.amiiboapp.ui.fragments.AmiibosGrid.adapter.AmiiboListAdapter
import com.muhammad89a.amiiboapp.utils.showImageByGlide
import com.muhammad89a.amiiboapp.utils.showOrNot

class AmiiboItemViewHolder(private val binding: AmiibosItemListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(amiiboItem: AmiiboDTO, isEnabled: Boolean = true) {
        binding.run {
            if(amiiboItem.purchased){
                Log.d("AmiiboItemViewHolder","purchased")
            }
            nameListItem.text = amiiboItem.name?:""
            gameSeriesListItem.text = amiiboItem.gameSeries?:""
            amiiboSeriesListItem.text = amiiboItem.amiiboSeries
            cartListItem.showOrNot(amiiboItem.purchased)
            imageListItem?.let {
                imageListItem.showImageByGlide(amiiboItem.image?:"")
            }
            if(isEnabled){
                setOnClickListener(amiiboItem)
            }

        }
    }

    private fun setOnClickListener(amiibo: AmiiboDTO) {
        binding.root.setOnClickListener{view ->
            navigateToDetail(amiibo, view)
        }
    }


    private fun navigateToDetail(amiiboDTO: AmiiboDTO, view: View) {
        val directions = AmiibosGridFragmentDirections.actionAmiibosGridFragmentToAmiibosDetailsFragment(amiiboDTO)
        view.findNavController().navigate(directions)
    }

    companion object {
        fun from(parent: ViewGroup): AmiiboItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AmiibosItemListBinding.inflate(layoutInflater, parent, false)
            return AmiiboItemViewHolder(binding)
        }
    }
}