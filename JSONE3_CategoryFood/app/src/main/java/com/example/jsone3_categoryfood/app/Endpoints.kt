package com.example.jsone3_categoryfood.app


object Endpoints {
    const val URL_REGISTER = "auth/register"
    const val URL_LOGIN = "auth/login"
    const val URL_CATEGORY = "category"
    const val URL_SUB_CATEGORY = "subcategory"

    fun getRegisterUrl(): String{
        return "${Config.BASE_URL + URL_REGISTER}"
    }

    fun getLoginUrl(): String{
        return "${Config.BASE_URL + URL_LOGIN}"
    }

    fun getCategoryUrl():String{
        return "${Config.BASE_URL + URL_CATEGORY}"
    }

    fun getSubCategoryByCatId(catId: Int): String{
        return "${Config.BASE_URL + URL_SUB_CATEGORY}/{$catId}"
    }

}