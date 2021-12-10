package com.wakuei.githubusersearch.network

import com.wakuei.githubusersearch.model.UserModel

class UiCallback {
    interface UsersCallback {
        fun onSearchUserListSuccess(items: ArrayList<UserModel>?)
        fun onError(errorMessage: String?)
    }
}