import { useEffect } from 'react';
import { NativeModules } from 'react-native';

const { DrawOverOtherAppsView } = NativeModules;

interface Props {
  show?: boolean;
  message?: string;
}

export const hideOverlay = () => DrawOverOtherAppsView.hideOverlay();

// this will break your app dont try it use the show props instead
export const showOverlay = (componentName: string, props?: object) =>
  DrawOverOtherAppsView.showOverlay(componentName, props);

export const openApp = () => DrawOverOtherAppsView.openApp();

export default function DrawOverOtherApps({ show = true, message }: Props) {
  useEffect(() => {
    if (show) {
      DrawOverOtherAppsView.showOverlay('OverlayContent', { message });
    } else {
      DrawOverOtherAppsView.hideOverlay();
    }
    return () => {
      DrawOverOtherAppsView.hideOverlay();
    };
  }, [show, message]);

  return null;
}
