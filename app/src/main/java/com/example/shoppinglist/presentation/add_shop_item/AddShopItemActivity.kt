package com.example.shoppinglist.presentation.add_shop_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R

class AddShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: AddShopItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shop_item)
        viewModel.errorInputName.observe()
    }
}