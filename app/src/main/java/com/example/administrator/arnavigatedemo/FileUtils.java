package com.example.administrator.arnavigatedemo;

import android.content.Context;

import com.example.administrator.arnavigatedemo.model.BeaconInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by stone on 2017/5/17.
 */

public class FileUtils {
    public static void persistUserInfo(List<BeaconInfo> beaconInfoList, Context context) {
        File file = new File(context.getExternalCacheDir().getPath()+"/list.txt");
        try
        {
            file.createNewFile();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(beaconInfoList);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
