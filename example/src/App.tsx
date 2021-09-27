import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import MusicMetadata from 'react-native-music-metadata';

export default function App() {
  const [results, setResults] = React.useState<Array<object> | []>();

  React.useEffect(() => {
    MusicMetadata.getMetadata(['/path/to/your/track.mp3']).then(setResults);
  }, []);

  return (
    <View style={styles.container}>
      {
        results.forEach((result: object) => {
          <Text>Result: {result.title}</Text>
        })
      }

    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
