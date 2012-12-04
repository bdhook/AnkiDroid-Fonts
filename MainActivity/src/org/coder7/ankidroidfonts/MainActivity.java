/*
 *  AnkiDroid Fonts is a simple GPL font installer for AnkiDroid
 *  Copyright (C) 2012 Bradley Hook

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/.
 * */
package org.coder7.ankidroidfonts;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void installFonts(View view) {

    	AssetManager assetManager = getAssets();
    	String filename = "FreeSerif.ttf";
    	InputStream in = null;
    	OutputStream out = null;
    	try {
    		in = assetManager.open(filename);
    		String target_path=Environment.getExternalStorageDirectory().getAbsolutePath() + "/AnkiDroid/fonts/" + filename;
    		String target_path_test=Environment.getExternalStorageDirectory().getAbsolutePath() + "/AnkiDroid/";

    		Context context = getApplicationContext();
    		int duration = Toast.LENGTH_SHORT;

    		CharSequence text="Attempting to copy " + filename + " to " + target_path;
    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();

    		File test_path = new File(target_path_test);
    		if (test_path.isDirectory()!=true) {
    			throw new RuntimeException("It appears AnkiDroid is not installed!");
    		}
    		File test_path2 = new File(target_path_test + "fonts");
    		if (test_path2.isDirectory()!=true) {
    			//Attempt to create the directory
    			test_path2.mkdir();
    		}
    		out = new FileOutputStream(target_path);
    		copyFile(in, out);
    		in.close();
    		in = null;
    		out.flush();
    		out.close();
    		out = null;
    		text="File seems to have copied successfully.";
    		toast = Toast.makeText(context, text, duration);
    		toast.show();

    	} catch(Exception e) {
    		Context context = getApplicationContext();
    		int duration = Toast.LENGTH_SHORT;

    		CharSequence text="Copy failed. "+e.getMessage();
    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();

    		Log.e("tag", e.getMessage());
    	}       

    }
    
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
}
