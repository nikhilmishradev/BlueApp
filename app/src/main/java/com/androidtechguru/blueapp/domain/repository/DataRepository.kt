package com.androidtechguru.blueapp.domain.repositoryimport com.androidtechguru.blueapp.domain.model.Imageimport com.androidtechguru.blueapp.domain.model.Itemimport kotlinx.coroutines.flow.Flowinterface DataRepository {    suspend fun getItems(): Flow<List<Item>>    suspend fun getImages(): Flow<List<Image>>}