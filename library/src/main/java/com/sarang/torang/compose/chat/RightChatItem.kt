package com.sarang.torang.compose.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.R

@Preview(showBackground = true)
@Composable
internal fun RightChatItem(
    message: String = "",
    isSending: Boolean = false,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp)
    ) {
        Row(
            Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.Bottom
        ) {
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            bottomStart = 24.dp,
                            topEnd = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    )
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
            if (isSending)
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.sending),
                    contentDescription = ""
                )
        }
    }
}