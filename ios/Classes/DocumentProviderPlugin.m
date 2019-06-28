#import "DocumentProviderPlugin.h"
#import <document_provider/document_provider-Swift.h>

@implementation DocumentProviderPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftDocumentProviderPlugin registerWithRegistrar:registrar];
}
@end
