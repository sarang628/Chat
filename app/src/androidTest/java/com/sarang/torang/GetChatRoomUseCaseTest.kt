package com.sarang.torang

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.usecase.GetChatRoomUseCase
import com.sarang.torang.usecase.LoadChatRoomUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * runTest를 사용하면 코루틴 안에서 코드를 작성할 수 있다.
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class GetChatRoomUseCaseTest {
    @get:Rule var hiltRule = HiltAndroidRule(this)
    val tag : String = "__GetChatRoomUseCase"
    @Inject lateinit var getChatRoomUseCase: GetChatRoomUseCase
    @Inject lateinit var loadChatRoomUseCase: LoadChatRoomUseCase
    @Inject lateinit var loginRepository: LoginRepository
    @Before fun setUp() = runTest {
        hiltRule.inject()
        loginRepository.emailLogin("sry_ang@naver.com", "Torang!234")
    }

    @Test
    fun getChatRoomUseCaseTest() = runTest {
        loadChatRoomUseCase.invoke()
        val result = getChatRoomUseCase.invoke()

        val data = result.first()

        Log.d(tag, data.toString())
    }
}