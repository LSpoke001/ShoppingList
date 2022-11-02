package com.example.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun deleteShopItemById(id: Int)
    fun getShopItemById(id: Int): ShopItem
    fun getShopList() : List<ShopItem>
    fun updateShopList(shopItem: ShopItem)
}