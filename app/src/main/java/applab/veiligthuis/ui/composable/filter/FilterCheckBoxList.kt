package applab.veiligthuis.ui.composable.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import applab.veiligthuis.ui.composable.CheckBoxItemState
import applab.veiligthuis.ui.composable.CheckBoxList
import applab.veiligthuis.ui.theme.AppTheme

@Composable
fun FilterCheckBoxList(
    headerText: String,
    filterItems: List<CheckBoxItemState>,
    onItemChecked: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 18.dp)) {
        Divider(modifier = Modifier.padding(bottom = 18.dp))
        Text(
            text = headerText,
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.h1
        )
        CheckBoxList(
            items = filterItems,
            onChecked = onItemChecked,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FilterListPreview() {
    AppTheme {
        FilterCheckBoxList(
            headerText = "Beroepsmatig",
            filterItems = listOf(
                CheckBoxItemState(1, false, "Onbehandeld"),
                CheckBoxItemState(2, false, "In behandeling")
            ),
            onItemChecked = { _, _ -> })
    }
}