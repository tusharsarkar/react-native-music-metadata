# react-native-music-metadata

React Native module for reading the music metadata on iOS and Android.

## Installation

```sh
npm install react-native-music-metadata --save

or

yarn add react-native-music-metadata

```

## Usage

```js
// import the module
import MusicMetadata from 'react-native-music-metadata';

// get the metadata for a list of files
MusicMetadata.getMetadata(['/path/to/your/track.mp3'])
  .then((tracks) => {
    tracks.forEach((track) => {
      console.log(`${track.title} by ${track.artist}`);
    });
  })
  .catch((err) => {
    console.error(err);
  });

```
## API

### `getMetadata(uris: string[]): Promise<MusicMetadataItem[]>`

Reads the music metadata for each uri.

The returned promise resolves with an array of objects with the following properties:

```
type MusicMetadataItem = {
  albumArtist: string;     // The artist of the album
  albumName: string;     // The name of the album
  artist: string;     // The artist of the track
  title: string;     // The title of the track
  uri: string;     // The path to the track
  duration: number;     // Length of the track in seconds
};
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT
