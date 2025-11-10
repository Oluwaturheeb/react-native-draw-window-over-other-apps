# `react-native-draw-over-other-apps`

A React Native module to display overlays (floating views) above other apps, similar to chat heads.

---

## 📦 Installation

```bash
npm install react-native-draw-over-other-apps
# or
yarn add react-native-draw-over-other-apps
```

> Make sure your app has **`SYSTEM_ALERT_WINDOW` permission** enabled for overlays to work on Android.

---

## 🔹 Importing the module

```javascript
import DrawOverOtherApps from 'react-native-draw-over-other-apps';
```

---

## 🔹 Usage

### Show overlay

```jsx
<DrawOverOtherApps show={true} />
```

This will start the overlay service and display your registered React Native component above other apps.

### Hide overlay

```javascript
DrawOverOtherApps.hideOverlay();
```

### Bring your app back to foreground

```javascript
DrawOverOtherApps.openApp();
```

---

## 🔹 Register your overlay component

<span style="color:red">The name **must be exactly `OverlayContent`**</span>:

```javascript
import { AppRegistry } from 'react-native';
import OverlayContent from './OverlayContent';

AppRegistry.registerComponent('OverlayContent', () => OverlayContent);
```

---

## 🔹 Example `OverlayContent`

```javascript
import React from 'react';
import { View, Text, Pressable } from 'react-native';

const OverlayContent = () => (
  <View style={{ padding: 10, backgroundColor: 'blue', flex: 1 }}>
    <View style={{ backgroundColor: 'black', borderRadius: 12, padding: 12 }}>
      <Text style={{ color: 'white', fontWeight: 'bold' }}>Overlay</Text>
      <Pressable onPress={() => DrawOverOtherApps.openApp()}>
        <Text style={{ color: 'red', fontWeight: 'bold' }}>Open App</Text>
      </Pressable>
    </View>
  </View>
);

export default OverlayContent;
```

---

## ⚡ Notes

- Make sure `show={true}` is passed to display the overlay.
- The overlay component is rendered by the **service**, so it will appear even when your app is in the background.
- Use `hideOverlay()` to remove it safely.
- Use `openApp()` to bring your app back from the overlay.
