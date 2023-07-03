package applab.veiligthuis.ui.meldingenlijst

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.ui.common.CustomSwitchThumb

import applab.veiligthuis.ui.theme.filter_blue
import applab.veiligthuis.ui.theme.filter_grey
import applab.veiligthuis.ui.common.ListDialogSpinner
import applab.veiligthuis.ui.theme.veilig_thuis_blauw

@Composable
fun FilterButtonsBar(
    toggleInkomendSelected: (Boolean) -> Unit,
    expandedFilter: Boolean,
    onClickExpandFilter: () -> Unit,
    selectedLocatie: String?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(11.dp, 40.dp, 11.dp, 25.dp)
        ) {
            //meldingFilterButtons(inkomendSelected = isInkomendSelected, onClick = toggleInkomendSelected)
            CustomSwitchThumb(
                leftText = "Inkomend", rightText = "Afgesloten",
                trackColor = filter_grey, thumbColor = veilig_thuis_blauw,
                textColor = Color.White,
                trackWidth = 200.dp, trackHeight = 30.dp,
                thumbWidth = 100.dp,
                onSwipe = toggleInkomendSelected,
            )
            Button(
                onClick = onClickExpandFilter,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                modifier = modifier.height(30.dp)
            ) {
                Text(
                    text = "Filter",
                    fontSize = 10.sp,
                    color = Color.White
                )
            }
        }
        if (expandedFilter) {
            Column(
                modifier = Modifier
                    .padding(11.dp, 0.dp)
            ) {
                ListDialogSpinner(
                    textValueDefault = "Selecteer een locatie",
                    textValueState = selectedLocatie,
                    label = "Locatie",
                    items = listOf(
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam",
                        "Amsterdam",
                        "Groningen",
                        "Eindhoven",
                        "Rotterdam"
                    ),
                    itemSelectedOnClick = { },
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                        modifier = modifier.height(30.dp)
                    ) {
                        Text(
                            text = "Reset filter",
                            fontSize = 10.sp,
                            color = Color.White,
                        )
                    }
                    Box {
                        var expanded by remember { mutableStateOf(false) }
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "More",
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Sorting by date descending"
                                )
                                Text(text = "Sort Datum Desc")
                            }
                            DropdownMenuItem(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Sorting by date ascending"
                                )
                                Text(text = "Sort Datum Asc")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewExpandedBar() {
    FilterButtonsBar({ }, true, { }, null)
}

@Preview(showBackground = true)
@Composable
fun PreviewUnexpandedBar() {
    FilterButtonsBar({ }, false, { }, null)
}