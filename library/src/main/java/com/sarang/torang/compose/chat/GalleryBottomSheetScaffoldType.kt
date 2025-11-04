package com.sarang.torang.compose.chat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

typealias GalleryBottomSheetScaffoldType = @Composable (
    GalleryBottomSheetScaffoldData
) -> Unit

data class GalleryBottomSheetScaffoldData(
    val show            : Boolean                               = false,
    val onHidden        : () -> Unit                            = {},
    val onSend          : (List<String>) -> Unit                = {},
    val sheetContents   : @Composable () -> Unit                = {},
    val content         : @Composable (PaddingValues) -> Unit   = {},
)

val LocalGalleryBottomSheetScaffold : ProvidableCompositionLocal<GalleryBottomSheetScaffoldType>
= compositionLocalOf<GalleryBottomSheetScaffoldType> {
    @Composable{
        it.content.invoke(PaddingValues(0.dp))
    }
}