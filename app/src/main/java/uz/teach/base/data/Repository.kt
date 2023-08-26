package uz.teach.base.data

import javax.inject.Inject

class Repository @Inject constructor(
    localDataSource: LocalDataSource,
    remoteDataSource: RemoteDataSource,
) {
    val remote = remoteDataSource
    val local = localDataSource
}