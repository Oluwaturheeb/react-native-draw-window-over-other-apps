import { AppRegistry } from 'react-native';
import App from './src/App';
import OverlayContent from './src/OverlayContent';
import { name as appName } from './app.json';

AppRegistry.registerComponent('OverlayContent', () => OverlayContent);
AppRegistry.registerComponent(appName, () => App);
