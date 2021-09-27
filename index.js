import { NativeModules } from 'react-native';
  
const { MusicMetadata } = NativeModules;
  
const MusicMetadataWrapper = {
   getMetadata: uris => MusicMetadata.getMetadata(uris),
};
  
export default MusicMetadataWrapper;