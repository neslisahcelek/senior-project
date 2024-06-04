package com.example.theguide.presentation.dashboard.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theguide.presentation.dashboard.CheckboxState
import com.example.theguide.ui.theme.TheGuideTheme
import com.example.theguide.ui.theme.softOrange


@Composable
fun DistrictCheckbox(districts: SnapshotStateList<CheckboxState>) {
    districts.forEachIndexed { index, info ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 12.dp)
                .padding(start = 8.dp, end = 20.dp)
        ) {
            Checkbox(
                checked = info.isChecked,
                onCheckedChange = { isChecked ->
                    districts[index] = info.copy(isChecked = isChecked)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = softOrange,
                )
            )
            Text(text = info.text, color = Color.Black, modifier = Modifier.padding(start = 6.dp))
        }
    }
}

@Preview
@Composable
fun DistrictCheckboxPreview() {
    TheGuideTheme {
        Column {
            DistrictCheckbox(districts = remember {
                mutableStateListOf(
                    CheckboxState(
                        text = "Konyaaltı",
                        isChecked = true
                    ),
                    CheckboxState(
                        text = "Muratpaşa",
                        isChecked = false
                    ),
                    CheckboxState(
                        text = "Kepez",
                        isChecked = false
                    )
                )
            })
        }
    }
}