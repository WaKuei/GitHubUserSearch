package com.wakuei.githubusersearch

import com.wakuei.githubusersearch.repository.UserRepository
import com.wakuei.githubusersearch.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    viewModel { UserViewModel(get()) }
}

val repoModule = module {
    single { UserRepository() }
}