package com.waqas028.composebottomnavigationbar.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.waqas028.composebottomnavigationbar.R
import com.waqas028.composebottomnavigationbar.model.BottomNavigationItem
import com.waqas028.composebottomnavigationbar.utils.NavigationContentPosition

@Composable
fun PermanentNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToTopLevelDestination: (BottomNavigationItem) -> Unit,
) {
    //Drawer which show on Tablet or large device on landscape mode
    PermanentDrawerSheet(
        modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Layout(
            modifier = Modifier.padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ExtendedFloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.compose),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BOTTOM_MENU_LIST.forEachIndexed { index, navBarDestination ->
                        NavigationDrawerItem(
                            selected = selectedDestination == navBarDestination.route,
                            label = {
                                Text(
                                    text = navBarDestination.title,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = navBarDestination.selectedIcon,
                                    contentDescription = ""
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = { navigateToTopLevelDestination(navBarDestination) }
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition)
        )
    }
}

fun navigationMeasurePolicy(
    navigationContentPosition: NavigationContentPosition,
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        lateinit var headerMeasurable: Measurable
        lateinit var contentMeasurable: Measurable
        measurables.forEach {
            when (it.layoutId) {
                LayoutType.HEADER -> headerMeasurable = it
                LayoutType.CONTENT -> contentMeasurable = it
                else -> error("Unknown layoutId encountered!")
            }
        }

        val headerPlaceable = headerMeasurable.measure(constraints)
        val contentPlaceable = contentMeasurable.measure(
            constraints.offset(vertical = -headerPlaceable.height)
        )
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place the header, this goes at the top
            headerPlaceable.placeRelative(0, 0)

            // Determine how much space is not taken up by the content
            val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

            val contentPlaceableY = when (navigationContentPosition) {
                // Figure out the place we want to place the content, with respect to the
                // parent (ignoring the header for now)
                NavigationContentPosition.TOP -> 0
                NavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
            }
                // And finally, make sure we don't overlap with the header.
                .coerceAtLeast(headerPlaceable.height)

            contentPlaceable.placeRelative(0, contentPlaceableY)
        }
    }
}


@Composable
fun NavigationRail(
    selectedDestination: String,
    navigateToTopLevelDestination: (BottomNavigationItem) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {

    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Column(
            modifier = Modifier.layoutId(LayoutType.HEADER),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            NavigationRailItem(
                selected = false,
                onClick = onDrawerClicked,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = ""
                    )
                }
            )
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(8.dp)) // NavigationRailHeaderPadding
            Spacer(Modifier.height(4.dp)) // NavigationRailVerticalPadding
        }

        Column(
            modifier = Modifier.layoutId(LayoutType.CONTENT),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BOTTOM_MENU_LIST.forEach { navBarDestination ->
                NavigationRailItem(
                    selected = selectedDestination == navBarDestination.route,
                    onClick = { navigateToTopLevelDestination(navBarDestination) },
                    icon = {
                        Icon(imageVector = navBarDestination.selectedIcon, contentDescription = "")
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (BottomNavigationItem) -> Unit
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        BOTTOM_MENU_LIST.forEach { navBarDestination ->
            NavigationBarItem(
                selected = selectedDestination == navBarDestination.route,
                onClick = { navigateToTopLevelDestination(navBarDestination) },
                icon = {
                    Icon(imageVector = navBarDestination.selectedIcon, contentDescription = "")
                }
            )
        }
    }
}

@Composable
fun ModalNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToTopLevelDestination: (BottomNavigationItem) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {

    ModalDrawerSheet(
        drawerContentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Layout(
            modifier = Modifier.padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ModalNavigationDrawerContent",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    }

                    ExtendedFloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.compose),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BOTTOM_MENU_LIST.forEachIndexed { index, navBarDestination ->
                        NavigationDrawerItem(
                            selected = selectedDestination == navBarDestination.route,
                            label = {
                                Text(
                                    text = navBarDestination.title,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = navBarDestination.selectedIcon,
                                    contentDescription = ""
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                            onClick = { navigateToTopLevelDestination(navBarDestination) }
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition)
        )
    }
}

enum class LayoutType {
    HEADER, CONTENT
}