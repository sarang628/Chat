package com.sarang.torang.compose.chatroom

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.sarang.torang.R
import com.sarang.torang.compose.chat.ChatImageLoaderData
import com.sarang.torang.compose.chat.LocalChatImageLoader
import com.sarang.torang.data.ChatUser

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRoomItem(
    modifier: Modifier = Modifier,
    uiState: ChatRoomUiState,
    onClick: (Int) -> Unit = {},
    onLongClick: (Int) -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .combinedClickable(
                onClick = { onClick.invoke(uiState.id) },
                onLongClick = { onLongClick.invoke(uiState.id) }
            )
        ,
        constraintSet = ConstraintSet {
            val image = createRefFor("image")
            val camera = createRefFor("camera")
            val nickName = createRefFor("nickName")
            val seenTime = createRefFor("seenTime")
            constrain(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start,)
            }

            constrain(camera) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }

            constrain(nickName) {
                top.linkTo(image.top)
                bottom.linkTo(seenTime.top)
                start.linkTo(image.end, 12.dp)
                end.linkTo(camera.start)
                width = Dimension.fillToConstraints
            }
            constrain(seenTime) {
                top.linkTo(nickName.bottom)
                bottom.linkTo(image.bottom)
                start.linkTo(image.end, 12.dp)
            }
        }
    ) {
        if (!uiState.isMultiple) {
            LocalChatImageLoader.current.invoke(
                ChatImageLoaderData(
                modifier = Modifier
                    .layoutId("image")
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0x11000000)),
                url = uiState.profileUrl,
                iconSize = 30.dp,
                progressSize = 30.dp,
                contentScale = ContentScale.Crop
            ))
        } else {
            Box(modifier = Modifier.layoutId("image")) {
                LocalChatImageLoader.current.invoke(
                    ChatImageLoaderData(
                    modifier = Modifier
                        .layoutId("image")
                        .size(50.dp)
                        .padding(end = 8.dp, bottom = 8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEEEEE)),
                    url = uiState.profileUrl,
                    iconSize = 30.dp,
                    progressSize = 30.dp,
                    contentScale = ContentScale.Crop)
                )
                LocalChatImageLoader.current.invoke(
                    ChatImageLoaderData(
                    modifier = Modifier
                        .layoutId("image")
                        .size(50.dp)
                        .padding(start = 8.dp, top = 5.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFEEEEEE)),
                    url = uiState.list[1].profileUrl,
                    iconSize = 30.dp,
                    progressSize = 30.dp,
                    contentScale = ContentScale.Crop)
                )
            }
        }

        Text(
            text = uiState.nickName,
            modifier = Modifier.layoutId("nickName"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = uiState.seenTimeTxt, modifier = Modifier.layoutId("seenTime"), fontSize = 13.sp)
        IconButton(modifier = Modifier.layoutId("camera"),
            onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = R.drawable.camera),
                tint = Color.Gray,
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatRoomItemPreview() {
    ChatRoomItem(/*Preview*/
        uiState = ChatRoomUiState(
            0,
            "2025-10-10 10:10:00",
            listOf(
                ChatUser(nickName = "amy", profileUrl = "1", id = "id"),
                ChatUser(nickName = "jhone", profileUrl = "1", id = "id"),
                ChatUser(nickName = "frank", profileUrl = "1", id = "id")
            )
        ),
        onClick = {}
    )
}