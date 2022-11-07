package com.example.shoppinglist.presentation.add_shop_item

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.google.android.material.textfield.TextInputLayout

class AddShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    private lateinit var viewModel: AddShopItemViewModel

    companion object{
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_id"

        fun newIntentAddItem(context:Context): Intent {
            val intent = Intent(context, AddShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_ADD)
            return intent
        }
        fun newIntentEditItem(context: Context, id:Int):Intent{
            val intent = Intent(context, AddShopItemActivity::class.java)
            intent.putExtra(EXTRA_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, id)
            return intent
        }
    }
    private fun initViews(){
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        btnSave = findViewById(R.id.btn_save)
        viewModel = ViewModelProvider(this)[AddShopItemViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shop_item)
        initViews()
        val mode = intent.getStringExtra(EXTRA_MODE)
        val id = intent.getStringExtra(EXTRA_SHOP_ITEM_ID)
        Log.d("AddShopItemINFO", "${mode.toString()} ${id.toString()}")
    }
}