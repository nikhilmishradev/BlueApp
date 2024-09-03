@file:OptIn(ExperimentalMaterial3Api::class)package com.androidtechguru.blueapp.presentation.ui.componentsimport androidx.compose.foundation.layout.Columnimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.paddingimport androidx.compose.foundation.layout.wrapContentHeightimport androidx.compose.material3.ExperimentalMaterial3Apiimport androidx.compose.material3.MaterialThemeimport androidx.compose.material3.ModalBottomSheetimport androidx.compose.material3.Textimport androidx.compose.material3.rememberModalBottomSheetStateimport androidx.compose.runtime.Composableimport androidx.compose.ui.Modifierimport androidx.compose.ui.res.stringResourceimport androidx.compose.ui.text.style.TextAlignimport com.androidtechguru.blueapp.Rimport com.androidtechguru.blueapp.presentation.ui.theme.Dimensimport com.androidtechguru.blueapp.presentation.ui.viewmodel.MainViewModel@Composablefun BottomSheetDialog(viewModel: MainViewModel) {    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)        ModalBottomSheet(        onDismissRequest = {            viewModel.closeBottomSheet()        },        sheetState = sheetState,        shape = MaterialTheme.shapes.large    ) {        Column(modifier = Modifier            .padding(Dimens.paddingMedium_16dp)            .fillMaxWidth()            .wrapContentHeight()) {            Text(stringResource(R.string.statistics),                textAlign = TextAlign.Center,                modifier = Modifier.fillMaxWidth())            StatisticsReport(viewModel)        }    }}