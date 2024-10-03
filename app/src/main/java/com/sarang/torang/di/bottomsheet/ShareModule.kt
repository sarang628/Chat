package com.sarang.torangbottomsheet.di.bottomsheet

import com.sarang.torang.api.ApiProfile
import com.sarang.torang.data.dao.LoggedInUserDao
import com.sarang.torang.session.SessionService
import com.sarang.torang.data.User
import com.sarang.torang.usecase.GetFollowerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ShareModule
{
    @Provides
    fun providesGetFollowerUseCase(apiProfile: ApiProfile, loggedInUserDao: LoggedInUserDao, sessionService: SessionService): GetFollowerUseCase
    {
        return object : GetFollowerUseCase
        {
            override suspend fun invoke(): List<com.sarang.torang.data.bottomsheet.User>
            {
                val token = sessionService.getToken() ?: throw Exception("로그인을 해주세요.")

                /*return apiProfile.getMyFollowing(token).map {
                    User(userId = it.followerId, userName = it.userName, picture = it.profilePicUrl)
                }*/

                return listOf()
            }
        }
    }

}