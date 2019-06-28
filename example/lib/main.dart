import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:document_provider/document_provider.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {

    try {
      _checkDirectory();
    } catch (error){
      print(error);
    }
  }

  void _checkDirectory() async{
    // get all the directories
    List<dynamic> file = await DocumentProvider.getAllDirectories(
        isTrim: true);

    //get the path of SD card Directory
    String externalDirectory = await DocumentProvider.getExternalDirectoryPath(isTrim: true);

    //get the path of Phone storage directory
    String internalDirectory = await DocumentProvider.getInternalDirectoryPath(isTrim: true);

  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container()
      ),
    );
  }
}
