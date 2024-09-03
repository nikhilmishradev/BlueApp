package com.androidtechguru.blueapp.presentation.ui.componentsimport androidx.compose.foundation.layout.Columnimport androidx.compose.foundation.layout.Spacerimport androidx.compose.foundation.layout.fillMaxWidthimport androidx.compose.foundation.layout.heightimport androidx.compose.foundation.layout.paddingimport androidx.compose.material3.Buttonimport androidx.compose.material3.MaterialThemeimport androidx.compose.material3.Textimport androidx.compose.runtime.Composableimport androidx.compose.runtime.collectAsStateimport androidx.compose.runtime.getValueimport androidx.compose.ui.Modifierimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.res.stringResourceimport androidx.compose.ui.text.SpanStyleimport androidx.compose.ui.text.buildAnnotatedStringimport androidx.compose.ui.text.font.FontWeightimport androidx.compose.ui.text.style.TextDecorationimport androidx.compose.ui.text.withStyleimport androidx.lifecycle.viewmodel.compose.viewModelimport com.androidtechguru.blueapp.Rimport com.androidtechguru.blueapp.presentation.ui.theme.Dimensimport com.androidtechguru.blueapp.presentation.ui.viewmodel.MainViewModel@Composablefun StatisticsReport(viewModel: MainViewModel = viewModel()) {    val statistics by viewModel.statistics.collectAsState()        Column(        modifier = Modifier            .fillMaxWidth()            .padding(Dimens.paddingMedium_16dp)    ) {        Text(stringResource(R.string.items_statistics_report),            fontSize = Dimens.textSizeExtraLarge,            style = MaterialTheme.typography.headlineMedium)        Spacer(modifier = Modifier.height(Dimens.padding_20dp))        Text(stringResource(R.string.total_items, statistics.totalItems),            fontSize = Dimens.textSizeMedium_16sp,            textDecoration = TextDecoration.Underline,            fontWeight = FontWeight.Bold,            style = MaterialTheme.typography.headlineMedium)        Spacer(modifier = Modifier.height(Dimens.padding_20dp))                Text(stringResource(R.string.top_3_characters),            style = MaterialTheme.typography.headlineSmall,            fontSize = Dimens.textSizeMedium_16sp)        Spacer(modifier = Modifier.height(Dimens.padding_12dp))                statistics.topCharacters.forEachIndexed { index, (char, count) ->            val annotatedString = buildAnnotatedString {                withStyle(style =                SpanStyle(                    fontWeight = FontWeight.ExtraBold,                    color = when (index) {                        0 -> Color.Magenta                        1 -> Color.Blue                        else -> Color.Red                    })) {                    append("$char = ")                }                withStyle(style = SpanStyle(color = Color.Black,                    fontWeight = FontWeight.SemiBold)) {                    append("$count")                }            }            Text(annotatedString)            Spacer(modifier = Modifier.height(Dimens.paddingExtraSmall_5dp))        }                Spacer(modifier = Modifier.height(Dimens.paddingMedium_16dp))        Button(onClick = { viewModel.closeBottomSheet() }) {            Text(stringResource(R.string.close))        }        Spacer(modifier = Modifier.height(Dimens.paddingExtraLarge))    }}