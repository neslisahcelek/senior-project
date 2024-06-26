package com.example.theguide.presentation.topplaces

import Recommendation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theguide.R
import com.example.theguide.presentation.navigation.Route
import com.example.theguide.presentation.welcome.views.CategoryRow
import com.example.theguide.ui.component.PrimaryTopAppBar
import com.example.theguide.ui.theme.TheGuideTheme
import com.example.theguide.util.Util

@Composable
fun TopPlacesScreen(
    action: (TopPlacesAction) -> Unit = {},
    state: TopPlacesState = TopPlacesState(),
    navigate: (String) -> Unit = {},
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { PrimaryTopAppBar(
                title = stringResource(id = R.string.top_places_screen_title),
                onBackClick = { navigate.invoke(Route.DashboardScreen.route) }
            ) }
        ) { values ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.9f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Kafeler",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = Color.Black,
                                style = MaterialTheme.typography.titleLarge
                            )
                            CategoryRow(
                                action = action,
                                state = state
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TopPlacesScreenPreview() {
    TheGuideTheme {
        TopPlacesScreen(
            state = TopPlacesState(
                topPlaces = Util.getPlaceList()
            )
        )
    }
}