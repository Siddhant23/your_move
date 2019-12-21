package com.coroutinedispatcher.yourmove.model

import kotlinx.coroutines.CoroutineDispatcher

class AppCoroutineDispatchers(
    val mainDispatcher: CoroutineDispatcher,
    val ioDispatcher: CoroutineDispatcher
)