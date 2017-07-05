package jiacc.com.installapktest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jiacc on 2017/7/4.
 */

public class InstallUtils {
    private static int BUFFERSIZE=1024;
    private static boolean copyInstallPkg(Context context,File dstFile){
        boolean isSucess=false;
        try {
            String cupInfo=getCupInfo();
            String sourceFile="";
            if("armeabi-v7a/NEON (hard-float)".equals(cupInfo)||"armeabi-v7a/NEON".equals(cupInfo)){
                sourceFile="OpenCV_3.2.0_Manager_3.20_armeabi.apk";
            }else if("armeabi-v7a".equals(cupInfo)||"armeabi-v7a (hard-float)".equals(cupInfo)){
                sourceFile="OpenCV_3.2.0_Manager_3.20_armeabi-v7a.apk";
            }else if("mips".equals(cupInfo)){
                sourceFile="OpenCV_3.2.0_Manager_3.20_mips.apk";
            }else if("mips64".equals(cupInfo)){
                sourceFile="OpenCV_3.2.0_Manager_3.20_mips64.apk";
            }else if("x86".equals(cupInfo)){
                sourceFile="OpenCV_3.2.0_Manager_3.20_x86.apk";
            }else if("arm64-v8a".equals(cupInfo)){
                sourceFile="OpenCV_3.2.0_Manager_3.20_x86_64.apk";
            }
            InputStream is=context.getAssets().open(sourceFile);
            BufferedInputStream bis=new BufferedInputStream(is,2*BUFFERSIZE);
            FileOutputStream outputStream=new FileOutputStream(dstFile);
            BufferedOutputStream bos=new BufferedOutputStream(outputStream);
            int bisLength=bis.available();
            int readLength=0;
            byte[] byteArray=new byte[BUFFERSIZE];
            int temp;
            while((temp=bis.read(byteArray))!=-1) {
                bos.write(byteArray,0,temp);
            }
            bos.flush();
            bos.close();
            bis.close();
            isSucess=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return isSucess;
    }
    public static void install(Context context){
        try {
            File dstFile = new File(Environment.getExternalStorageDirectory()+"/aaa.apk");
            if (!copyInstallPkg(context, dstFile)) return;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(dstFile),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public static native String getCupInfo();
    static{
        System.loadLibrary("myjni");
    }
}
