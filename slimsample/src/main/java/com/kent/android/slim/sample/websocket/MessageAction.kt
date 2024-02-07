package com.kent.android.slim.sample.websocket

/**
 * Created by Kent Sung on 2024/2/7.
 */
data class MessageModel(
    val type: String?,
    val aiVLiverAction: MessageAction
)

data class MessageAction(
    val command: String?,
    val subtitle: String?,
    val emotion: String?,
    val expression: String?,
    val wav: String?,
    val interrupt: Boolean
)

