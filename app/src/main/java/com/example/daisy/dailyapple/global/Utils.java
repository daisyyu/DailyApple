package com.example.daisy.dailyapple.global;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Daisy on 10/25/15.
 */
public class Utils {
    public static int REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 1;

    private Utils() {

    }

    public static void requestGlobalPermissionsUpfront(final Activity context) {
        requestPermission(context, new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE},
                "defualt reason");
    }

    private static void requestPermission(final Activity context, final String[]
            permissions, final String
                                                  explaination) {
        //TODO: loop through all permissions
        if (ContextCompat.checkSelfPermission(context,
                permissions[0])
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    permissions[0])) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
//                Thread t = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder
                        (context);
                builder.setTitle("Reason for permission request");
                builder.setMessage(explaination);
                builder.setPositiveButton("Acknowleaged", new
                        DialogInterface
                                .OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                ActivityCompat.requestPermissions(context,
                                        permissions,
                                        REQUEST_WRITE_EXTERNAL_STORAGE_CODE);
                            }

                        });
                AlertDialog dialog = builder.create();
                dialog.show();

//                });
//                t.start();
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(context,
                        permissions,
                        REQUEST_WRITE_EXTERNAL_STORAGE_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
