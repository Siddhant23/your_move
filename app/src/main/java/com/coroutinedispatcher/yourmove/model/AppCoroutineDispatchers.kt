package com.coroutinedispatcher.yourmove.model

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
    val ioDispatchers: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher
)