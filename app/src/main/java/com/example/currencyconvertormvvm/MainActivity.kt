package com.example.currencyconvertormvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconvertormvvm.ui.presentation.CurrencyConvertorMVVMTheme
import com.example.currencyconvertormvvm.ui.presentation.main_screen.MainScreen
import com.example.currencyconvertormvvm.ui.presentation.main_screen.Viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConvertorMVVMTheme {
                val viewModel = hiltViewModel<MainScreenViewModel>()
                Surface {
                    MainScreen(
                    state = viewModel.state,
                    onEvent = viewModel::onEvent,

                    ) }






            }
        }
    }
}

