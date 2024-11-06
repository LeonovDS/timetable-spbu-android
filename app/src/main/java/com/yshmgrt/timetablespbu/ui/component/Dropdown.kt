package com.yshmgrt.timetablespbu.ui.component

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@ExperimentalMaterial3Api
@Composable
fun <T> Dropdown(
    items: List<T>,
    selected: MutableState<T?>,
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit = {},
    itemName: T.() -> String = { toString() },
) {
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(items) {
        selected.value = null
        expanded = false
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selected.value?.itemName() ?: "",
            onValueChange = { },
            label = label,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true),
            enabled = items.isNotEmpty()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(item.itemName()) },
                    onClick = {
                        selected.value = item
                        expanded = false
                    }
                )
            }
        }
    }
}