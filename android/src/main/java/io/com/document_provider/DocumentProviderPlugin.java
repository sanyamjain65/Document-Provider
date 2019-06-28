package io.com.document_provider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** DocumentProviderPlugin */
public class DocumentProviderPlugin implements MethodCallHandler {
  /** Plugin registration. */
  Activity context;
  MethodChannel methodChannel;

  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "document_provider");
    channel.setMethodCallHandler(new DocumentProviderPlugin(registrar.activity(), channel));
  }

  public DocumentProviderPlugin(Activity activity, MethodChannel methodChannel) {
    this.context = activity;
    this.methodChannel = methodChannel;
    this.methodChannel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    Map<String, Object> request = (Map<String, Object>) call.arguments;
    boolean isTrim = (boolean) request.get("trim");
    switch (call.method) {
      case "getAllDirectories":
        getAllDirectories(context, result, isTrim);
        break;
      case "getExternalDirectory":
        getExternalDirectoryPath(context, result, isTrim);
        break;
      case "getInternalDirectory":
        getInternalDirectory(context, result, isTrim);
        break;
      default:
        result.notImplemented();
    }
  }

  @TargetApi(Build.VERSION_CODES.FROYO)
  private File[] getAllDirectoriesPath() {
    File[] files;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
      files = context.getExternalFilesDirs(null);
    else
      files = new File[]{context.getExternalFilesDir(null)};
    return files;
  }

  @TargetApi(Build.VERSION_CODES.FROYO)
  private void getAllDirectories(Context context, Result result, boolean isTrim) {
    File files[];
    ArrayList<String> fileAsString = new ArrayList<>();

    files = getAllDirectoriesPath();
    for (File file : files) {
      if (!isTrim)
        fileAsString.add(file.getAbsolutePath());
      else
        fileAsString.add(file.getAbsolutePath().substring(0, file.getAbsolutePath().indexOf("Android")));
    }

    if (fileAsString.size() >= 1) {
      result.success(fileAsString);
    } else {
      result.error("Directory Provider", "No directories found on this device", null);
    }
  }

  private void getExternalDirectoryPath(Context context, Result result, Boolean isTrimPath) {
    File[] files;
    files = getAllDirectoriesPath();
    File externalFile = getPreferredReadLocation(context, files);

    if (externalFile != null) {
      if (!isTrimPath) {
        result.success(externalFile.getAbsolutePath());
      } else {
        result.success(externalFile.getAbsolutePath().substring(0, externalFile.getAbsolutePath().indexOf("Android")));
      }
    } else {
      result.error("Directory Provider", "Error finding Sd-Card directory in your phone", null);
    }
  }

  @TargetApi(Build.VERSION_CODES.FROYO)
  private void getInternalDirectory(Context context, Result result, Boolean isTrimPath) {
    File internal = context.getExternalFilesDir(null);
    if (internal != null) {
      if (!isTrimPath)
        result.success(internal.getAbsolutePath());
      else
        result.success(internal.getAbsolutePath().substring(0, internal.getAbsolutePath().indexOf("Android")));
    } else {
      result.error("Directory Provider", "Error finding internal directory in your phone", null);
    }
  }

  @TargetApi(Build.VERSION_CODES.FROYO)
  private static File getPreferredReadLocation(Context context, File[] files) {

    File internal = context.getExternalFilesDir(null);

    for (File f : files) {
      if (f == null)
        return internal;
    }

    if (files.length == 2) {
      if (!files[0].getAbsolutePath().equals(internal.getAbsolutePath()))
        return files[0];
      else
        return files[1];
    } else
      return internal;
  }
}
