package com.smh.githubfollowercompose.data.local

import com.smh.githubfollowercompose.domain.model.local.FavouriteEntity
import com.smh.githubfollowercompose.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource(
    private val favouritesDao: FavouritesDao
) : LocalDataSource {
//    private var database: GithubDatabase = FakeDatabase.database
//    private var favouritesDao = database.favouritesDao()

    override fun getAllFavourites(): Flow<List<FavouriteEntity>> {
        return favouritesDao.getAll()
    }

    override suspend fun insertFavourite(favourite: FavouriteEntity) {
        favouritesDao.insert(favourite)
    }

    override suspend fun deleteFavourite(favourite: FavouriteEntity) {
        return favouritesDao.delete(favourite)
    }

    override fun checkExist(id: Int): Flow<Int> {
        return favouritesDao.checkExist(id)
    }
}