package cilveti.inigo.cbmobile2.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.couchbase.lite.Database;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class file_utils {
    public static void copyDatabase(String nombreDb, Context c, Database database) {
        File dir = c.getFilesDir();
        String fileparent = c.getFilesDir().getAbsolutePath();

        String filename = "/" + nombreDb + ".cblite2";
        if ((database.getName()!=null) ){
            filename = "/" + database.getName() + ".cblite2";
        }

        String objectiveSubDir = "";
        String today = DateTime.now().dayOfMonth().getAsShortText() + "_" + DateTime.now().monthOfYear().get() + "_" + DateTime.now().year().getAsShortText();

        //delete all files inside objective directory if it already exists
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + nombreDb + ".cblite2");
        f.delete();

        if (f.isDirectory()) {
            String[] children = f.list();
            if (children != null)
                for (int i = 0; i < children.length; i++) {
                    new File(f, children[i]).delete();
                }
        } else {
            if (f.mkdirs()) {
                Log.i("", "");
            }
        }
        boolean completed = false;
        try {
            File parent = new File(fileparent);
            File son = new File(fileparent + filename);
            copyFolder(son, f);
            completed = true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (completed)
            Toast.makeText(c, "copiado correctamente", Toast.LENGTH_SHORT).show();
        //copyFile(fileparent, filename, objective);
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null)
                source.close();
            if (destination != null)
                destination.close();
        }
    }

    public static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
            }
            //list all the directory contents
            String files[] = src.list();
            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile);
            }
        } else {
            copyFile(src, dest);
        }
    }
}
