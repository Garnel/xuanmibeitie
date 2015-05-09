package com.maogm.xuanmibeitie;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class AboutActivity extends ActionBarActivity {

    private TextView briefText;

    private ImageButton website;
    private ImageButton github;
    private ImageButton weibo;
    private View.OnClickListener iconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.icon_website:
                    openUrl("http://maogm.com");
                    break;
                case R.id.icon_github:
                    openUrl("https://github.com/Garnel/xuanmibeitie");
                    break;
                case R.id.icon_weibo:
                    openUrl("http://weibo.com/740562151");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        preprocessText();

        website = (ImageButton) findViewById(R.id.icon_website);
        github = (ImageButton) findViewById(R.id.icon_github);
        weibo = (ImageButton) findViewById(R.id.icon_weibo);

        website.setOnClickListener(iconClickListener);
        github.setOnClickListener(iconClickListener);
        weibo.setOnClickListener(iconClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            exit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void preprocessText() {
        briefText = (TextView) findViewById(R.id.brief);
        Spanned spanned = Html.fromHtml(getString(R.string.xuanmibeitie_brief));
        briefText.setMovementMethod(LinkMovementMethod.getInstance());
        briefText.setText(spanned);
    }

    private void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
