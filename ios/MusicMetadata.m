#import "MusicMetadata.h"
#import <AVFoundation/AVFoundation.h>

@implementation MusicMetadata

RCT_EXPORT_MODULE()

// Example method
// See // https://reactnative.dev/docs/native-modules-ios
RCT_REMAP_METHOD(multiply,
                 multiplyWithA:(nonnull NSNumber*)a withB:(nonnull NSNumber*)b
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)
{
  NSNumber *result = @([a floatValue] * [b floatValue]);

  resolve(result);
}


RCT_EXPORT_METHOD(getMetadata:(NSArray *)uris resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    NSMutableArray *songArray = [NSMutableArray array];
    for (int i = 0; i < uris.count; i++) {
        NSDictionary *songDictionary = [self getData:uris[i]];
        [songArray addObject:songDictionary];
    }

    NSArray *result = [songArray copy];

    resolve(result);
}

-(NSDictionary *) getData:(NSString *)uri
{
    AVAsset *asset;
    NSDictionary *songDictionary = [NSMutableDictionary dictionary];
    NSURL *fileURL = [NSURL fileURLWithPath:uri];

    asset = [AVURLAsset URLAssetWithURL:fileURL options:nil];
    for (NSString *format in [asset availableMetadataFormats]) {
        for (AVMetadataItem *item in [asset metadataForFormat:format]) {
            if ([[item commonKey] isEqualToString:@"title"]) {
                [songDictionary setValue:[NSString stringWithString:(NSString *)[item value]] forKey:@"title"];
            }
            if ([[item commonKey] isEqualToString:@"artist"]) {
                [songDictionary setValue:[NSString stringWithString:(NSString *)[item value]] forKey:@"artist"];
            }
            if ([[item commonKey] isEqualToString:@"albumName"]) {
                [songDictionary setValue:[NSString stringWithString:(NSString *)[item value]] forKey:@"albumName"];
            }
            if ([[item commonKey] isEqualToString:@"albumArtist"]) {
                [songDictionary setValue:[NSString stringWithString:(NSString *)[item value]] forKey:@"albumArtist"];
            }
            if ([[item commonKey] isEqualToString:@"artwork"]) {
                NSString *img = [(NSData*)item.value base64EncodedStringWithOptions:0];
                [songDictionary setValue:img forKey:@"artwork"];
            }
        }
    }

    NSURL *assetURL = [NSURL fileURLWithPath:uri];
    AVAudioPlayer *audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:assetURL error:nil];
    NSNumber *duration = [NSNumber numberWithDouble:[audioPlayer duration]];
    [songDictionary setValue:duration forKey:@"duration"];

    [songDictionary setValue:uri forKey:@"uri"];

    return songDictionary;
}

@end
