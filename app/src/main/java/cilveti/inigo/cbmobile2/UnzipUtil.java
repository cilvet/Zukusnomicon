package cilveti.inigo.cbmobile2;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by inigocilvetisesma on 23/11/17.
 */

public class UnzipUtil
{
    private String zipFile;
    private String location;
    private Context context;

    public UnzipUtil(String zipFile, String location, Context context)
    {
        this.zipFile = zipFile;
        this.location = location;
        this.context = context;

        dirChecker("");
    }

    public void unzip()
    {
        try
        {
            InputStream fin = context.getAssets().open(zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null)
            {

                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory())
                {
                    dirChecker(ze.getName());
                }
                else
                {
                    FileOutputStream fout = new FileOutputStream(location + ze.getName());

                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = zin.read(buffer)) != -1)
                    {
                        fout.write(buffer, 0, len);
                    }
                    fout.close();

                    zin.closeEntry();

                }

            }
            zin.close();
        }

        catch(Exception e)
        {
            Log.e("Decompress", "unzip", e);
        }

    }

    private void dirChecker(String dir)
    {
        File f = new File(location + dir);
        if(!f.isDirectory())
        {
            f.mkdirs();
        }
    }
}