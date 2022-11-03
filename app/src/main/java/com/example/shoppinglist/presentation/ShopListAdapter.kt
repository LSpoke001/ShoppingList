package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object{
        const val ENABLED_VIEW = 1
        const val DISABLED_VIEW = 0
        const val MAX_POOL_SIZE = 10
    }

    private var count = 0
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        Log.d("ADAPTER", "onCreateViewHolder:  ${++count}")
        val layout = when(viewType){
            ENABLED_VIEW -> R.layout.item_shop_enabled
            DISABLED_VIEW -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        viewHolder.tvName.text = shopItem.name
        viewHolder.tvCount.text = shopItem.count.toString()

        viewHolder.view.setOnLongClickListener {
            true
        }
/*        if (shopItem.enabled) {
            viewHolder.tvName.setTextColor(ContextCompat.getColor(viewHolder.view.context,
                android.R.color.system_accent3_800
            ))
            viewHolder.tvCount.setTextColor(ContextCompat.getColor(viewHolder.view.context,
                android.R.color.system_accent3_800
            ))
        }*/
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if(item.enabled){
             ENABLED_VIEW
        }else{
             DISABLED_VIEW
        }
    }
/*    override fun onViewRecycled(viewHolder: ShopItemViewHolder) {
        super.onViewRecycled(viewHolder)
        viewHolder.tvName.setTextColor(ContextCompat.getColor( viewHolder.view.context,
            android.R.color.system_accent2_800
        ))
        viewHolder.tvCount.setTextColor(ContextCompat.getColor(viewHolder.view.context,
            android.R.color.system_accent2_800
        ))
    }*/

    override fun getItemCount(): Int {
        return shopList.size
    }



    class ShopItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }
}
