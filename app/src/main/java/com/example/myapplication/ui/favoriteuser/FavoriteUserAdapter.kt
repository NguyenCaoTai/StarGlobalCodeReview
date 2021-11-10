package com.example.myapplication.ui.favoriteuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.R

class FavoriteUserAdapter(val dataset: MutableList<FavoriteUser>) :
    RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_view_favorite_user,
                parent,
                false
            ) as ViewDataBinding
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount() = dataset.size

    fun submitList(list: List<FavoriteUser>){
        dataset.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(value: FavoriteUser) {
            dataBinding.setVariable(BR.itemModel, value)
            dataBinding.executePendingBindings()
        }
    }
}
