package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShowList :LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llShowList = findViewById(R.id.ll_show_items)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java] //присваиваем значение

        //подписываемся на объект shopList
        viewModel.shopList.observe(this){
            showView(it)
        }
        viewModel.getShopList()
    }

    private fun showView(shopList : List<ShopItem>){
        llShowList.removeAllViews()
        for(shopItem in shopList){
            val layoutId = if(shopItem.enabled){
                R.layout.item_shop_enabled
            }else{
                R.layout.item_shop_disabled
            }

            val view = LayoutInflater.from(this).inflate(layoutId, llShowList, false)
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvCount = view.findViewById<TextView>(R.id.tv_count)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }
            llShowList.addView(view)
        }
    }
}