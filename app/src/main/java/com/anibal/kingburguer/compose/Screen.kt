package com.anibal.kingburguer.compose

enum class Screen(val route: String) {
    LOGIN("login"),
    SIGNUP("signup"),
    MAIN("main"),

    //Serão as rotas da tela principal
    HOME("home"),
    COUPON("coupon"),
    PROFILE("profile")
}