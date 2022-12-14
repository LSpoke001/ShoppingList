package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: ListAdapter<ShopItem, ShopItemViewHolder>(
    ShopItemDiffCallback()
)
// 1 способ
// : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>()
{

// 1 способ
//    var shopList = listOf<ShopItem>()
//        set(value) {
//            val callback = ShopListDiffCallback(shopList, value)
//            val diffUtil = DiffUtil.calculateDiff(callback)
//            diffUtil.dispatchUpdatesTo(this)
//            field = value
//        }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layoutId = when(viewType){
            TYPE_ENABLED -> R.layout.item_shop_enabled
            TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown type $viewType")
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)

        holder.tvName.text = "${shopItem.name}"
        holder.tvCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.view.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
            true
        }
    }

//    override fun onViewRecycled(holder: ShopItemViewHolder) {
//        super.onViewRecycled(holder)
//        holder.tvName.text = ""
//        holder.tvCount.text = ""
//        holder.tvName.setTextColor(
//            ContextCompat.getColor(
//                holder.view.context, android.R.color.white
//            ))
//    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).enabled){
            TYPE_ENABLED
        }else{
            TYPE_DISABLED
        }
    }

//    interface OnShopItemLongClickListener{
//        fun onShopItemLongClick()
//    }
//    interface OnShopItemClickListener{
//        fun onShopItemClick()
//    }
    companion object{
        const val TYPE_ENABLED = 1
        const val TYPE_DISABLED = 2
        const val MAX_POOL_SIZE = 15
    }
}