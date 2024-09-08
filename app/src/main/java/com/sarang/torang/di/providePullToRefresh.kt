package com.sarang.torang.di

import androidx.compose.runtime.Composable
import com.sryang.library.pullrefresh.PullToRefreshLayout
import com.sryang.library.pullrefresh.PullToRefreshLayoutState
import com.sryang.library.pullrefresh.RefreshIndicatorState

fun providePullToRefresh(state: PullToRefreshLayoutState): @Composable ((isRefreshing: Boolean, onRefresh: (() -> Unit), contents: @Composable (() -> Unit)) -> Unit) =
    { isRefreshing, onRefresh, contents ->

        if (isRefreshing) {
            state.updateState(RefreshIndicatorState.Refreshing)
        } else {
            state.updateState(RefreshIndicatorState.Default)
        }

        PullToRefreshLayout(
            pullRefreshLayoutState = state, refreshThreshold = 80, onRefresh = onRefresh
        ) {
            contents.invoke()
        }
    }