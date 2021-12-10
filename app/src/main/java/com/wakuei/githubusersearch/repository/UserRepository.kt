package com.wakuei.githubusersearch.repository

import android.text.TextUtils
import com.wakuei.githubusersearch.model.UserModel
import com.wakuei.githubusersearch.network.ApiExecutor
import com.wakuei.githubusersearch.network.UiCallback

class UserRepository {

    fun loadUserData(keyword: String?, page: Int, callback: OnUserApiCallback) {
        callback.startLoading()
        ApiExecutor.searchUsers(keyword, page, object : UiCallback.UsersCallback {
            override fun onSearchUserListSuccess(items: ArrayList<UserModel>?) {
                if (items != null) callback.updateData(items)
                callback.finishLoading()
            }

            override fun onError(errorMessage: String?) {
                if (!TextUtils.isEmpty(errorMessage)) callback.errorMessage(errorMessage!!)
                callback.finishLoading()
            }

        })
    }

    interface OnUserApiCallback {
        fun startLoading()
        fun finishLoading()
        fun updateData(data: ArrayList<UserModel>)
        fun errorMessage(errorMsg: String)
    }
}