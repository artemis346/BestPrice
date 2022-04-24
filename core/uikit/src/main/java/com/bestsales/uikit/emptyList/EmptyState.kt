package com.bestsales.uikit.emptyList

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bestsales.uikit.R


@Composable
fun EmptyState(content: String, @DrawableRes res: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = res),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )

        Text(
            text = content,
            fontSize = 16.sp,
            color = colorResource(R.color.grey),
        )
    }
}