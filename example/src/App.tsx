import { useState } from 'react';
import { Button, View } from 'react-native';
import DrawOverOtherApps from 'react-native-draw-over-other-apps';

export default function App() {
  const [toggleOverlay, setToggleOverLay] = useState(true);

  const toggler = () => setToggleOverLay(!toggleOverlay);
  return (
    <View style={{ height: 400, padding: 20 }}>
      <Button onPress={toggler} title="Show Overlay" />
      <DrawOverOtherApps show={true} />
    </View>
  );
}
