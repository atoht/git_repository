#ListObjectOperation
![image](http://images.cnitblog.com/i/607542/201403/141558148063393.gif)

将字典与存储在磁盘上的NSData绑定,操作字典的同时也在修改存储在磁盘上的NSData.使用简单,用来替换系统的NSUserDefaults.

 * easy to store dictionary to file system
 * you can use it to replace NSUserDefaults

You can use like this:
```javascript
// store a dictionary to file system
[ListObjectOperation storeDictionary:@{@"name": @"YouXianMing"}
                              toPath:simpleFilePath(@"/Library/Preferences/data")];

// recover dictionary from file system and manage it
[ListObjectOperation syncDictionaryWithDataPath:simpleFilePath(@"/Library/Preferences/data")
                                           save:YES
                                     dictionary:^(NSMutableDictionary *list) {
                                         [list setObject:@"Y.X." forKey:@"name"];
                                     }];

// check the result
[ListObjectOperation syncDictionaryWithDataPath:simpleFilePath(@"/Library/Preferences/data")
                                           save:NO
                                     dictionary:^(NSMutableDictionary *list) {
                                         NSLog(@"%@", list);
                                     }];
```
Just use it for fun :).
