package com.sarang.torang.compose.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

typealias GalleryComposeType = @Composable () -> Unit

val LocalGallery : ProvidableCompositionLocal<GalleryComposeType>
        = compositionLocalOf<GalleryComposeType> {
    @Composable {

    }
}
