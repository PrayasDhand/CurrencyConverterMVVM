package com.example.currencyconvertormvvm.ui.presentation.main_screen


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconvertormvvm.R
import com.example.currencyconvertormvvm.domain.model.CurrencyRate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit,
) {
    val keys = listOf("1","2","3","4","5","6","7","8","9",".","0","C")
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) { // Use state.error as the key
        state.error?.let { error -> // Check if state.error is not null
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var shouldBottomSheetShow by remember {
        mutableStateOf(false)
    }
    var selectedCurrency by remember { mutableStateOf("") }

    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { shouldBottomSheetShow = false },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Select Currency",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                }
            },
            content = {
                BottomSheetContent(
                    currenciesList = state.currencyRates.values.toList(),
                    onItemClicked = { currencyCode ->
                        selectedCurrency = currencyCode
                        onEvent(MainScreenEvent.BottomSheetItemClicked(currencyCode))
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) shouldBottomSheetShow = false
                        }
                    }
                )
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Text(

            text = "Currency Convertor",
            modifier = Modifier.wrapContentSize(), // Changed to wrapContentSize()
            fontFamily = FontFamily.Cursive,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(16.dp)) // Added spacer

        Box(contentAlignment = Alignment.CenterStart) {

            Column {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        CurrencyRow(
                            modifier = Modifier.fillMaxWidth(),
                            currencyCode = state.fromCurrencyCode,
                            currencyName = state.currencyRates[state.fromCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {  shouldBottomSheetShow = true
                                onEvent(MainScreenEvent.FromCurrencySelect)}
                        )
                        Text(
                            text = state.fromCurrencyValue,
                            fontSize = 40.sp,
                            modifier = Modifier.clickable(interactionSource = remember {MutableInteractionSource() },
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.FromCurrencySelect) }),
                            color = if (state.selectionState == SelectionState.FROM){
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = state.toCurrencyValue,
                            fontSize = 40.sp,
                            modifier = Modifier.clickable(interactionSource = remember {MutableInteractionSource() },
                                indication = null,
                                onClick = { onEvent(MainScreenEvent.ToCurrencySelect) }),
                            color = if (state.selectionState == SelectionState.TO){
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                        CurrencyRow(
                            modifier = Modifier.fillMaxWidth(),
                            currencyCode = state.toCurrencyCode,
                            currencyName = state.currencyRates[state.toCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = { shouldBottomSheetShow = true
                            onEvent(MainScreenEvent.ToCurrencySelect) }
                        )

                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(CircleShape)
                    .clickable { onEvent(MainScreenEvent.SwapIconClicked) }
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {

                Icon(
                    painter = painterResource(R.drawable.sync),


                    contentDescription = "Icon",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(25.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )


            }
        }

        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 35.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(keys) { key ->
                KeyboardButton(
                    modifier = Modifier.aspectRatio(1f),
                    key = key,
                    backgroundColor = if (key == "C") {
                        MaterialTheme.colorScheme.primary
                    } else MaterialTheme.colorScheme.surfaceVariant,
                    onClick = {
                         onEvent(MainScreenEvent.NumberButtonClicked(key))
                    }
                )
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    currenciesList: List<CurrencyRate>,
    onItemClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BottomSheetDefaults.DragHandle()
        Text(
            text = "Select Currency",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(currenciesList) { currency ->
                Text(
                    text = "${currency.code} - ${currency.name}",
                    modifier = Modifier
                        .clickable { onItemClicked(currency.code) }
                        .padding(8.dp)
                )
            }
        }
    }
}


@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    currencyCode: String,
    currencyName: String,
    onDropDownIconClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = currencyCode,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onDropDownIconClicked) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Drop Down Icon",
                tint = Color.Gray
            )
        }

        Text(
            text = currencyName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(2f)
        )
    }
}

@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    key: String,
    backgroundColor: Color,
    onClick: (String) -> Unit,
) {
    val textColor = if (key == "C") {
        MaterialTheme.colorScheme.onPrimary
    } else {
        if (isSystemInDarkTheme()) {
            Color.White // White text in dark mode
        } else {
            Color.Black // Black text in light mode
        }
    }

    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable { onClick(key) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = key, fontSize = 32.sp, color = textColor)
    }
}










///rough code////









