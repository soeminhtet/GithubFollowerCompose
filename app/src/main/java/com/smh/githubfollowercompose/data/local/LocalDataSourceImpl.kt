package com.smh.githubfollowercompose.data.local

import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val favouritesDao: FavouritesDao
) : LocalDataSource {
    override fun getAllFavourites(): Flow<List<FavouriteEntity>> {
        return favouritesDao.getAll()
    }

    override suspend fun insertFavourite(favourite: FavouriteEntity) {
        return favouritesDao.insert(favourite)
    }

    override suspend fun deleteFavourite(favourite: FavouriteEntity) {
        return favouritesDao.delete(favourite)
    }

    override fun checkExist(id: Int): Flow<Int> {
        return favouritesDao.checkExist(id)
    }
}