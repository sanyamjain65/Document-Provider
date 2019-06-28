import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:document_provider/document_provider.dart';

void main() {
  const MethodChannel channel = MethodChannel('document_provider');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await DocumentProvider.platformVersion, '42');
  });
}
