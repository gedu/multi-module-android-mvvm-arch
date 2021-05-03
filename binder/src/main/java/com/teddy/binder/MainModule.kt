package com.teddy.binder

import com.teddy.business.di.businessModule
import com.teddy.datasource.di.networkModule
import kotlinx.coroutines.FlowPreview

@FlowPreview
val appMainModules = listOf(networkModule, businessModule)