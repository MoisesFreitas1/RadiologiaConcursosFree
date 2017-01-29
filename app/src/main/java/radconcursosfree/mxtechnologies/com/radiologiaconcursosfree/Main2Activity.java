package radconcursosfree.mxtechnologies.com.radiologiaconcursosfree;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager FM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        UnityAds.init(this, "1074695", new UnityAdsListener());

        ImageView iv_share = (ImageView) findViewById(R.id.iv_share);
        iv_share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar");
                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=radconcursosfree.mxtechnologies.com.radiologiaconcursosfree");
                share.setType("text/plain");
                startActivity(share);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FM = getFragmentManager();
        FM.beginTransaction().replace(R.id.content_principal, new rad10()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        UnityAds.changeActivity(this);
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rad10) {
            FM.beginTransaction().replace(R.id.content_principal, new rad10()).commit();
        } else if (id == R.id.nav_rad20) {
            FM.beginTransaction().replace(R.id.content_principal, new rad20()).commit();
        } else if (id == R.id.nav_rad30) {
            FM.beginTransaction().replace(R.id.content_principal, new rad30()).commit();
        } else if (id == R.id.nav_upgrade) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=radiologia.mxtecnlogies.com.radiologia");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_face) {
            Uri uri = Uri.parse("https://m.facebook.com/Prepara-X-Cursos-961279907257312/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_email) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","preparaxcursos@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato [RADIOLOGIA CONCURSOS]");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Olá,\n");
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
        }else if (id == R.id.nav_site) {
            Uri uri = Uri.parse("http://www.preparaxcursos.com/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_cde) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_794bb96897c348ce967d162b07fd922a.pdf?dn=codigodeetica.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_d5211) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_48e7d3e5d6244ee0991ebd18ae7cbd69.pdf?dn=D5211.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_d92790) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_21f905a0a4654b848d28f4b0db2f7c7d.pdf?dn=D92790.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_l10508) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_4980e5295202492484ca8d54d3900a36.pdf?dn=L10508.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_l1243) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_678a2a52e6f3441dad2b4caaaf9847e6.pdf?dn=L123450.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_l7394) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_1325f2df5d37484986fd296a6f2c1f35.pdf?dn=L739485.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_nr32) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_c179eab623ae484aa29b264487192c2c.pdf?dn=NR32.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if (id == R.id.nav_p453) {
            Uri uri = Uri.parse("https://static.wixstatic.com/ugd/f0d361_64e1fc0eb08346fd81e327504c94304c.pdf?dn=P453.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
    }
}