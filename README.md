# Jetpack Compose Bottom Navigation Bar with Dual Pane Support

This project demonstrates how to create a bottom navigation bar in Jetpack Compose with dual pane support for various screen sizes, including landscape, tablets and desktops. Additionally, it includes a modern drawer for navigation.

# Demo Video

| Small Device | Medium | Tablet |
| ------------- | ------------- | ------------- |
| ![smallphone](https://github.com/waqas028/Bottom_Nav_Bar_Compose/assets/96041723/9830d1ee-9157-415f-a5a0-af2e51bafe21) | ![medium phone](https://github.com/waqas028/Bottom_Nav_Bar_Compose/assets/96041723/6d6bc8a7-0e99-4109-bfbd-532504706e79) | ![tablets](https://github.com/waqas028/Bottom_Nav_Bar_Compose/assets/96041723/6efe8b28-4237-4fa8-b9e6-6e6d2269cf1c) |

## Features

- Bottom navigation bar with multiple destinations.
- Navigation rail
- ModalNavigationDrawer
- Dual pane layout for landscape, tablets and desktops, providing a master-detail interface.
- Modern drawer for navigation.
- Responsive design for different screen sizes.

## Getting Started

### 1. Bottom Navigation Bar

- Create a composable function for the bottom navigation bar.
- Use the `BottomNavigation` and `BottomNavigationItem` composable functions to define navigation items.

### 2. Dual Pane Layout

- Determine the screen size and layout accordingly.
- For tablets and desktops, use a dual pane layout with master-detail interface.
- For smaller screens, use a single pane layout.

### 3. Modern Drawer

- Implement a modern drawer using Jetpack Compose.
- Add navigation items to the drawer.
- Handle navigation when items are selected.

### 4. Navigation Rail
- Navigation rails let people switch between UI views on mid-sized devices
- Use navigation rails in medium, expanded, large, and extra-large window sizes
- Can contain 3-7 destinations plus an optional FAB
- Always put the rail in the same place, even on different screens of an app

### 5. Responsive Design

- Use ConstraintLayout, Modifier.weight, and other layout modifiers to create a responsive design.
- Test the layout on various screen sizes to ensure responsiveness.

## Usage

You can integrate the provided components into your existing Jetpack Compose project or use them as a reference to create your own bottom navigation bar with dual pane support.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, feel free to open an issue or create a pull request.
