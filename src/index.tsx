import { NativeModules } from 'react-native';

type MusicMetadataType = {
  multiply(a: number, b: number): Promise<number>;
};

const { MusicMetadata } = NativeModules;

export default MusicMetadata as MusicMetadataType;
