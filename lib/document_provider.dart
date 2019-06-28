import 'dart:async';

import 'package:flutter/services.dart';

class DocumentProvider {
  static const MethodChannel _channel =
      const MethodChannel('document_provider');

  static Future<List<dynamic>>  getAllDirectories({bool isTrim = false}) async {
    List<dynamic> filesAsString =
    await _channel.invokeMethod('getAllDirectories', <String, dynamic>{'trim': isTrim});
    return filesAsString;
  }

  static Future<String> getExternalDirectoryPath({bool isTrim = false}) async {
    String file = await _channel.invokeMethod(
        'getExternalDirectory', <String, dynamic>{'trim': isTrim});
    return file;
  }

  static Future<String> getInternalDirectoryPath({bool isTrim = false}) async {
    String file = await _channel.invokeMethod(
        'getInternalDirectory', <String, dynamic>{'trim': isTrim});
    return file;
  }
}
