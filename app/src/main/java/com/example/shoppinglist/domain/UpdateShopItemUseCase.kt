package com.example.shoppinglist.domain

class UpdateShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun updateShopList(shopItem: ShopItem){
        shopListRepository.updateShopList(shopItem)
    }
}