package com.wakuei.githubusersearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wakuei.githubusersearch.repository.UserRepository
import com.wakuei.githubusersearch.model.UserModel
import timber.log.Timber

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val mList = MutableLiveData<List<UserModel>>()
    val mIsLoading = MutableLiveData<Boolean>()
    val mErrorMessage = MutableLiveData<String>()
    private var mHasMore = true
    private var mPage = 1
    private val mUserRepositoryCallback = OnUserRepositoryCallback()
    private var mDataList = ArrayList<UserModel>()

    fun searchUsers(keyword: String?) {
        Timber.d("search user with keyword:$keyword")
        mPage = 1
        mHasMore = true
        mDataList = ArrayList()
        userRepository.loadUserData(keyword, mPage, mUserRepositoryCallback)
    }

    fun searchMoreData(keyword: String?) {
        Timber.d("search more user with keyword:$keyword , hasMore:$mHasMore")
        if (mHasMore) {
            mPage += 1
            userRepository.loadUserData(keyword, mPage, mUserRepositoryCallback)
        }
    }

    inner class OnUserRepositoryCallback : UserRepository.OnUserApiCallback {
        override fun startLoading() {
            mIsLoading.postValue(true)
        }

        override fun finishLoading() {
            mIsLoading.postValue(false)
        }

        override fun updateData(data: ArrayList<UserModel>) {
            if (data.size == 0)
                mHasMore = false
            mDataList.addAll(data)
            mList.postValue(mDataList)
        }

        override fun errorMessage(errorMsg: String) {
            mErrorMessage.postValue(errorMsg)
        }
    }
}