package radconcursosfree.mxtechnologies.com.radiologiaconcursosfree;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class BannerActivity extends Activity implements Runnable {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        Handler handler = new Handler();
        handler.postDelayed(this, 3000);

        ImageView banner1 = (ImageView) findViewById(R.id.banner1);
        banner1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=losfreitasapps.com.incidenciasradiologicas");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
    }

    public void run(){
        startActivity(new Intent(this, Main2Activity.class));
        finish();
    }
}