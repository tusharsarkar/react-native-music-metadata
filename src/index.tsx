import { NativeModules } from 'react-native';

type MusicMetadataType = {
  getMetadata(uris: Array<String>): Promise<Array<object>>;
};

const { MusicMetadata } = NativeModules;

export default MusicMetadata as MusicMetadataType;
