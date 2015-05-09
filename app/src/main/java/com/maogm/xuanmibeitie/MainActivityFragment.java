package com.maogm.xuanmibeitie;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ImageView wordImage;
    private TextView wordText;
    private ZitieSetting xuanmi;
    private AlertDialog jumpDialog;
    private String[] words;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wordImage = (ImageView) view.findViewById(R.id.imageView);
        wordText = (TextView) view.findViewById(R.id.word);
    }

    @Override
    public void onStart() {
        super.onStart();

        readZiTieSetting();
        displaySlice(0);
    }

    private void displaySlice(int index) {
        if (xuanmi == null) {
            return;
        }
        if (index < 0 || index >= xuanmi.images.size()) {
            return;
        }

        ZitieSetting.ZitieImageItem item = xuanmi.images.get(index);
        String imagePath = xuanmi.imageDir + File.separator + item.image;
        setImageFromPath(imagePath);
        wordText.setText(item.word);
    }

    private void setImageFromPath(String path) {
        Activity mainActivity = getActivity();
        if (mainActivity == null || wordImage == null) {
            return;
        }

        AssetManager am = getActivity().getAssets();
        try {
            Bitmap bmp = BitmapFactory.decodeStream(am.open(path));
//            int newWidth = wordImage.getWidth();
//            int newHeight = newWidth * bmp.getHeight() / bmp.getWidth();
            wordImage.setImageBitmap(bmp);
        } catch (IOException e) {
            notifySettingFileNotFound();
            e.printStackTrace();
        }
    }

    private void readZiTieSetting() {
        Activity mainActivity = getActivity();
        if (mainActivity == null) {
            return;
        }

        AssetManager am = getActivity().getAssets();
        String settingPath = "xuanmibeitie.json";
        try {
            InputStream settingStream = am.open(settingPath);
            Gson gson = new Gson();
            xuanmi = gson.fromJson(new InputStreamReader(settingStream), ZitieSetting.class);
            List<String> wordsList = new ArrayList<String>();
            for (int i = 0; i < xuanmi.images.size(); ++i) {
                wordsList.add(xuanmi.images.get(i).word);
            }
            words = new String[wordsList.size()];
            words = wordsList.toArray(words);
        } catch (IOException e) {
            notifySettingFileNotFound();
            e.printStackTrace();
        }
    }

    private void notifySettingFileNotFound() {
        Activity mainActivity = getActivity();
        if (mainActivity == null) {
            return;
        }
        Toast.makeText(mainActivity, R.string.missing_setting_file, Toast.LENGTH_SHORT).show();
    }

    public void showJumpDialog() {
        if (getActivity() == null) {
            return;
        }

        if (jumpDialog == null) {
            AlertDialog.Builder builder = new  AlertDialog.Builder(getActivity());
            jumpDialog = builder.setTitle(R.string.action_jump)
                    .setItems(words, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            jumpDialog.dismiss();
                            displaySlice(which);
                        }
                    }).create();
        }

        jumpDialog.show();
    }

}
