package com.bestsales.uikit.error

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bestsales.uikit.R

@Composable
fun ErrorView(errorMessage: String, performOnClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            modifier = Modifier
                .padding(0.dp, 40.dp),
            color = colorResource(R.color.black),
        )
        Button(onClick = performOnClick) {
            Text(text = stringResource(id = R.string.error_view_button))
        }
    }

}