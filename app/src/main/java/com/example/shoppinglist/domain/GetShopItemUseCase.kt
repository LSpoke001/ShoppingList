package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItemId(shopItemId: Int): ShopItem{
        return shopListRepository.getShopItem(shopItemId)
    }
}