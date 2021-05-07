package com.android.roteiroentremares.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.roteiroentremares.R;
import com.android.roteiroentremares.data.model.Artefacto;
import com.android.roteiroentremares.data.model.EspecieAvencas;
import com.android.roteiroentremares.ui.dashboard.viewmodel.dashboard.DashboardViewModel;
import com.android.roteiroentremares.ui.onboarding.MainActivity;
import com.android.roteiroentremares.util.PermissionsUtils;
import com.android.roteiroentremares.util.TypefaceSpan;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

@AndroidEntryPoint
public class UserDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EasyPermissions.PermissionCallbacks {

    private DashboardViewModel dashboardViewModel;

    private MaterialToolbar toolbar;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initToolbar();

        askForPermissions();
    }

    private void initToolbar() {
        // Init Toolbar (extended version of an ActionBar)
        toolbar = findViewById(R.id.toolbar);

        SpannableString s = new SpannableString(getResources().getString(R.string.app_name));
        s.setSpan(new TypefaceSpan(this, "poppins_medium.ttf", R.font.poppins_medium), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        toolbar.setTitle(s);

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_artefactos:
                Intent intent = new Intent(this, ArtefactosActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.nav_guiaDeCampo:
                Intent intent2 = new Intent(this, GuiaDeCampoActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.nav_pessoal:
                Intent intent3 = new Intent(this, PessoalActivity.class);
                startActivityForResult(intent3, 3);
                break;
            case R.id.nav_change_zone:
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
                materialAlertDialogBuilder.setTitle("Atenção!");

                if (dashboardViewModel.getAvencasOrRiaFormosa() == 0) {
                    materialAlertDialogBuilder.setMessage("Tens a certeza que queres mudar o ponto de interesse para a Ria Formosa?");
                    materialAlertDialogBuilder.setPositiveButton("Mudar de zona", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dashboardViewModel.setChangeToAvencasOrRiaFormosa(1);

                            restartApp(getApplicationContext());
                        }
                    });
                } else if (dashboardViewModel.getAvencasOrRiaFormosa() == 1) {
                    materialAlertDialogBuilder.setMessage("Tens a certeza que queres mudar o ponto de interesse para a Área Marinha Protegida das Avencas?");
                    materialAlertDialogBuilder.setPositiveButton("Mudar de zona", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dashboardViewModel.setChangeToAvencasOrRiaFormosa(0);

                            restartApp(getApplicationContext());
                        }
                    });
                }

                materialAlertDialogBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dismiss
                    }
                });
                AlertDialog alertDialog = materialAlertDialogBuilder.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorError, null));

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void restartApp(Context c) {
        Intent i = new Intent(UserDashboardActivity.this, MainActivity.class);
        // set the new task and clear flags
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    @AfterPermissionGranted(PermissionsUtils.PERMISSIONS_REQUEST_CODE)
    private void askForPermissions() {
        if (EasyPermissions.hasPermissions(this, PermissionsUtils.getPermissionList())) {
            // Toast.makeText(this, "Already has permissions needed", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "A aplicação necessita da sua permissão para aceder a todas as funcionalidades",
                    PermissionsUtils.PERMISSIONS_REQUEST_CODE, PermissionsUtils.getPermissionList());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        } else {
            askForPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            askForPermissions();
        }
    }
}