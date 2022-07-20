/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cookare.ui.components


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.R
import com.example.cookare.ui.theme.BackgroundWhite
import com.example.cookare.ui.theme.green700


@Composable
fun QuantitySelector(
    count: Int,
    decreaseItemCount: () -> Unit,
    increaseItemCount: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(R.string.quantity),
                style = MaterialTheme.typography.caption,
                fontSize = 20.sp,
                color = BackgroundWhite,
                modifier = Modifier
                    .padding(end = 25.dp)
                    .align(Alignment.CenterVertically)
            )
        }
//
        FloatingActionButton(
            onClick = decreaseItemCount,
            modifier = Modifier.height(20.dp).width(20.dp) .align(Alignment.CenterVertically)
        ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = null, tint = BackgroundWhite)
        }
        Crossfade(
            targetState = count,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "$it",
                style = MaterialTheme.typography.subtitle2,
                fontSize = 25.sp,
                color = BackgroundWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier.widthIn(min = 30.dp)
            )
        }
//
        FloatingActionButton(onClick = increaseItemCount,
            modifier = Modifier.height(20.dp).width(20.dp) .align(Alignment.CenterVertically)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = BackgroundWhite)
        }
    }
}

@Preview("default")

@Composable
fun QuantitySelectorPreview() {


            QuantitySelector(1, {}, {})
        }


