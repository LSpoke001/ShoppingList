package com.example.shoppinglist.presentation.add_shop_item

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.main.ShopItem
import com.google.android.material.textfield.TextInputLayout

class AddShopItemActivity : AppCompatActivity() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var btnSave: Button

    private lateinit var viewModel: AddShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    companion object{
        private const val EXTRA_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_id"
        private const val MODE_UNKNOWN = ""


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

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_MODE)){
            throw RuntimeException("Param screen mode is empty")
        }
        val mode = intent.getStringExtra(EXTRA_MODE)
        if(mode != MODE_ADD && mode != MODE_EDIT){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT){
            if(!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Shop item is is empty")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews(){
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        etName = findViewById(R.id.et_name)
        etCount = findViewById(R.id.et_count)
        btnSave = findViewById(R.id.btn_save)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shop_item)

        parseIntent()
        viewModel = ViewModelProvider(this)[AddShopItemViewModel::class.java]
        initViews()
        addTextChangeListener()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.errorInputCount.observe(this){
            val message = if(it){
                getString(R.string.error_input_count)
            }else{
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(this){
            val message = if(it){
                getString(R.string.error_input_name)
            }else{
                null
            }
            tilName.error = message
        }
        viewModel.closeAddActivity.observe(this){
            finish()
        }
    }

    private fun addTextChangeListener() {
        etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }
    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        btnSave.setOnClickListener {
            viewModel.editShopItem(etName.text?.toString(),etCount.text?.toString())
        }
    }
    private fun launchAddMode(){
        btnSave.setOnClickListener {
            viewModel.addShopItem(etName.text?.toString(),etCount.text?.toString())
        }
    }
}