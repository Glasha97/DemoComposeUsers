package com.example.demo

enum class NavigationRoute(val route:String) {
    USERS_PAGE("users"),
    USER_DETAILS("userPage/{userId}")
}