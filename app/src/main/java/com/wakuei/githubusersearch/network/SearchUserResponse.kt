package com.wakuei.githubusersearch.network

import com.wakuei.githubusersearch.model.UserModel

data class SearchUserResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: ArrayList<UserModel>?
)
