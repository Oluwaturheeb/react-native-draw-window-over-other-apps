import { View, Text, Pressable, StyleSheet } from 'react-native';
import { hideOverlay, openApp } from 'react-native-draw-over-other-apps';

const OverlayContent = () => {
  const colors = {
    primary: 'red',
    white: 'white',
  };

  return (
    <View
      style={{
        padding: 10,
        flex: 1,
        backgroundColor: 'transparent',
        justifyContent: 'flex-end',
      }}
    >
      <View
        style={{
          backgroundColor: 'black',
          borderRadius: 20,
          padding: 12,
          zIndex: 100000,
          width: '100%',
        }}
      >
        <View style={[css.spacenter]}>
          <View>
            <Text>Flow over other apps</Text>
          </View>
          <Pressable onPress={() => console.log('hello')}>
            <Text style={{ color: colors.primary, fontWeight: 'bold' }}>
              Tee
            </Text>
          </Pressable>
          <Pressable onPress={() => hideOverlay()}>
            <Text style={{ color: colors.primary, fontWeight: 'bold' }}>
              Close
            </Text>
          </Pressable>
          <Pressable onPress={() => openApp()}>
            <Text style={{ color: colors.primary, fontWeight: 'bold' }}>
              Open
            </Text>
          </Pressable>
        </View>
        <View
          style={{
            marginVertical: 12,
            borderRadius: 10,
            height: 80,
            backgroundColor: colors.primary,
            padding: 16,
            flexDirection: 'row',
            gap: 10,
          }}
        >
          <View>
            <Text style={{ color: colors.white, fontWeight: 'bold' }}>Tee</Text>
            <View />
          </View>
        </View>
      </View>
    </View>
  );
};

const css = StyleSheet.create({
  spacenter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
});

export default OverlayContent;
