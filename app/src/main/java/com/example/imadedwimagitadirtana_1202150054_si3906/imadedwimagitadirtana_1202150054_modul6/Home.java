package com.example.imadedwimagitadirtana_1202150054_si3906.imadedwimagitadirtana_1202150054_modul6;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
//deklarasi
    private TabLayout tabMainLayout;
    private ViewPager viewPagerMain;
    FloatingActionButton fabPost;

    FirebaseUser user;
    String userEmail, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();
        userName = userEmail.substring(0, userEmail.indexOf("@"));
//inisialisasi
        viewPagerMain = findViewById(R.id.viewPagerMain);
        tabMainLayout = findViewById(R.id.tabMainLayout);
        fabPost = findViewById(R.id.fabPost);
//method jika tombol floating di tekan
        fabPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, PostActivity.class);
                startActivity(intent);
            }
        });

        setupViewPager(viewPagerMain);
        tabMainLayout.setupWithViewPager(viewPagerMain);
    }
// view pager untuk menampilkan nama pada tab yang ada di home
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecentFragment(), "Terbaru");
        adapter.addFragment(new MyPhotoFragment(), "Foto Saya");
        viewPager.setAdapter(adapter);
    }
// membuat option menu pada toolbar di layout home
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
// method jika option menu di pilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionLogout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
// method untuk melakukan sign out
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
//class view pager
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
