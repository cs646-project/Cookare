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


import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.R
import com.example.cookare.ui.theme.*
import com.example.cookare.viewModels.StockViewModel


@Composable
fun QuantitySelector(
    count: Int,
    decreaseItemCount: () -> Unit,
    increaseItemCount: () -> Unit,
    modifier: Modifier = Modifier,
//    stockViewModel: StockViewModel,
//    key: String
) {
    Row(modifier = modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(R.string.quantity),
                style = MaterialTheme.typography.caption,
                fontSize = 20.sp,
                color = Color.White,
//                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(end = 25.dp)
                    .align(Alignment.CenterVertically)
            )
        }
//
        FloatingActionButton(
            backgroundColor = green100,
            onClick =

                decreaseItemCount
//                stockViewModel.updateStock(key, count)
//                Log.d("Decrease", "ShowResult: $count")
            ,
            modifier = Modifier.height(20.dp).width(20.dp) .align(Alignment.CenterVertically)
        ) {
            Icon(imageVector = Icons.Default.Remove, contentDescription = null, tint = Color.Black)
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
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.widthIn(min = 50.dp)
            )
        }
//
        FloatingActionButton(
            backgroundColor = green100,
            onClick =
                increaseItemCount
//                Log.d("Increase", "ShowResult: $count")
                      ,
            modifier = Modifier.height(20.dp).width(20.dp) .align(Alignment.CenterVertically)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.Black)
        }
    }
}

//@Preview("default")
//
//@Composable
//fun QuantitySelectorPreview() {
//
//
//            QuantitySelector(1, {}, {})
//        }


