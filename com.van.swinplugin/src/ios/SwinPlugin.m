#import "SwinPlugin.h"
#import <Cordova/CDV.h>

@implementation SwinPlugin

- (void)switchApp:(CDVInvokedUrlCommand*)command
{
    //跳至另一个app
    NSString *mystr=[[NSString alloc] initWithFormat:@"<URL Schemes>://"];
    NSURL *myurl=[[NSURL alloc] initWithString:mystr];
    [[UIApplication sharedApplication] openURL:myurl];
}

@end