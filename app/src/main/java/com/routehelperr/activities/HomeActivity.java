package com.routehelperr.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.karan.churi.PermissionManager.PermissionManager;
import com.routehelperr.R;
import com.routehelperr.adapter.NavMenuAdapter;
import com.routehelperr.fragments.HomeServicesFragment;
import com.routehelperr.fragments.MainHomeFragment;
import com.routehelperr.interfaces.NavRecyclerClickListner;
import com.routehelperr.model.InfoDataModel;
import com.routehelperr.model.NavItemModel;
import com.routehelperr.model.ServicesModel;
import com.routehelperr.model.User;
import com.routehelperr.networking.ApiInterface;
import com.routehelperr.networking.NetworkAvailable;
import com.routehelperr.networking.ApiClient;
import com.routehelperr.utils.ContactUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ArrayList<NavItemModel> navList;
    NavMenuAdapter nav_adapter;
    @BindView(R.id.nav_menu_recyclerV_id)
    RecyclerView recyclerView;
    @BindView(R.id.close_drawer_imageV)
    ImageView close_drawer_imageV;
    @BindView(R.id.home_main_layout_id)
    FrameLayout frameLayout;

    public static TextView toolBarTitle;

    FragmentTransaction ft;
    DrawerLayout drawer;
    NetworkAvailable networkAvailable;
    private List<ServicesModel.ServiceDataModel> serviceList;
    private List<InfoDataModel.Info> infoList;

    public static Toolbar toolbar;
    public static String selected_lang;

    private final String TAG = this.getClass().getSimpleName();
    public static User userModel;
    private ContactUtil contactUtil;
    private Typeface custom_font;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SetupToolBar();

        if (getIntent().getExtras() != null) {
            userModel = getIntent().getExtras().getParcelable("user_data");
            selected_lang = getIntent().getExtras().getString("lang");
        }
        permissionManager = new PermissionManager() {
        };
        permissionManager.checkAndRequestPermissions(this);


        networkAvailable = new NetworkAvailable(this);
        contactUtil = new ContactUtil(HomeActivity.this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        custom_font = Typeface.createFromAsset(this.getAssets(), "fonts/DIN Next LT Arabic Medium.ttf");
        infoList = new ArrayList<>();
        serviceList = new ArrayList<>();

        // Get Setting Info ...
        if (networkAvailable.isNetworkAvailable()) {
            getServicessInfo();
            getInfoData();
        } else {
            Toast.makeText(this, getString(R.string.error_connection), Snackbar.LENGTH_LONG).show();
        }

        prepareNavListItems();
        buildMenuRecycler();

        // Choose Default Fragment For Home Page....
        MainHomeFragment mainFragment = new MainHomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("setting_data", settingInfoModel.getMessage());
//        mainFragment.setArguments(bundle);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame_id, mainFragment).commit();
    }

    private void getInfoData() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<InfoDataModel> call = apiInterface.getInfo();
        call.enqueue(new Callback<InfoDataModel>() {
            @Override
            public void onResponse(Call<InfoDataModel> call, Response<InfoDataModel> response) {
                InfoDataModel infoDataModel = response.body();
                infoList = infoDataModel.getMessage().getInfo();
            }

            @Override
            public void onFailure(Call<InfoDataModel> call, Throwable t) {

            }
        });
    }

    private void buildMenuRecycler() {
        recyclerView = findViewById(R.id.nav_menu_recyclerV_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        nav_adapter = new NavMenuAdapter(HomeActivity.this, navList);
        recyclerView.setAdapter(nav_adapter);

        nav_adapter.setOnItemClickListener(new NavRecyclerClickListner() {
            @Override
            public void OnItemClick(int position) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                Intent intent = null;
                switch (position) {
                    case 0:
                        recreate();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (userModel != null && !userModel.getUsername().equals("")) {
                            intent = new Intent(HomeActivity.this, MyNotificatios.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, getString(R.string.login_first), Toast.LENGTH_SHORT).show();
                            intent = new Intent(HomeActivity.this, LogIn.class);
                            startActivity(intent);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (userModel != null && !userModel.getUsername().equals("")) {
                            intent = new Intent(HomeActivity.this, Chatters.class);
                            intent.putExtra("user_id", userModel.getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, getString(R.string.login_first), Toast.LENGTH_SHORT).show();
                            intent = new Intent(HomeActivity.this, LogIn.class);
                            startActivity(intent);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (userModel != null && !userModel.getUsername().equals("")) {
                            intent = new Intent(HomeActivity.this, MyAccount.class);
                            intent.putExtra("user_data", userModel);
                            startActivity(intent);
                        } else {
                            Toast.makeText(HomeActivity.this, getString(R.string.login_first), Toast.LENGTH_SHORT).show();
                            intent = new Intent(HomeActivity.this, LogIn.class);
                            startActivity(intent);
                        }
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        openWhatsApp();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
//                    case 5:
//                        intent = new Intent(HomeActivity.this, Register.class);
//                        startActivity(intent);
//                        drawer.closeDrawer(GravityCompat.START);
//                        break;
                    case 5:
                        toolBarTitle.setText(getString(R.string.our_services));
                        Fragment fragment = new HomeServicesFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("our_services", (ArrayList<? extends Parcelable>) serviceList);
                        fragment.setArguments(bundle);
                        displaySelectedFragment(fragment);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        intent = new Intent(HomeActivity.this, SelectBaqqa.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        String about = infoList.get(0).getAbout();
                        Log.i(TAG, about);
                        intent = new Intent(HomeActivity.this, AboutUs.class);
                        intent.putExtra("about", about);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 8:
                        intent = new Intent(HomeActivity.this, HelpScreen.class);
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 9:
                        Intent intent1 = new Intent(HomeActivity.this, EmployerRegisteration.class);
                        startActivity(intent1);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 10:
                        int applicationNameId = getApplicationInfo().labelRes;
                        final String appPackageName = getPackageName();
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, getString(applicationNameId));
                        String text = "Install this cool application: ";
                        String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
                        i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                        startActivity(Intent.createChooser(i, getString(R.string.share_link)));
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 11:
                        intent = new Intent(HomeActivity.this, PrivacyPolicy.class);
                        intent.putExtra("conditions", infoList.get(0).getConditions());
                        startActivity(intent);
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 12:
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogCustom);
                        builder.setMessage(getString(R.string.outofApp))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //super.onBackPressed();
                                        dialogInterface.dismiss();
                                        //-------------------------------------------------------
                                        pref = getSharedPreferences(LogIn.MY_PREFS_NAME, Context.MODE_PRIVATE);
                                        editor = pref.edit();
                                        editor.clear();
                                        editor.apply();
                                        startActivity(new Intent(HomeActivity.this, LogIn.class));
                                        finish();
                                        // -------------------------------------------------------
                                    }
                                })
                                .setNegativeButton(getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                        drawer.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });

    }

    public void openWhatsApp() {
        PackageManager pm = getPackageManager();
        try {
            String toNumber = "920029550"; // Replace with mobile phone number without +Sign or leading zeros, but with country code.
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + toNumber));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(HomeActivity.this, "it may be you dont have whats app", Toast.LENGTH_LONG).show();

        }
    }

    private void prepareNavListItems() {
        navList = new ArrayList<>();
        navList.add(new NavItemModel(R.string.home, R.drawable.home));
        navList.add(new NavItemModel(R.string.notifications, R.drawable.notifications));
        navList.add(new NavItemModel(R.string.messages, R.drawable.email));
        navList.add(new NavItemModel(R.string.my_account, R.drawable.profile));
//        navList.add(new NavItemModel(R.string.sales, R.drawable.sale));
        navList.add(new NavItemModel(R.string.subscribe, R.drawable.subscribe));
        navList.add(new NavItemModel(R.string.services, R.drawable.services));
        navList.add(new NavItemModel(R.string.packages, R.drawable.packages));
        navList.add(new NavItemModel(R.string.about_us, R.drawable.about));
        navList.add(new NavItemModel(R.string.contact_Us, R.drawable.contact));
        navList.add(new NavItemModel(R.string.employer_register, R.drawable.profile));
        navList.add(new NavItemModel(R.string.share_app, R.drawable.email));
        navList.add(new NavItemModel(R.string.privacy_policy, R.drawable.terms));
        navList.add(new NavItemModel(R.string.logOut, R.drawable.terms));
    }

    private void SetupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolBarTitle = findViewById(R.id.home_toolBar_title);
        toolBarTitle.setTypeface(custom_font);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.callUs_icon_id) {
            callPhoneNumber();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.close_drawer_imageV)
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void getServicessInfo() {
        ApiInterface apiServiceInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ServicesModel> call = apiServiceInterface.getServicesInfo();
        call.enqueue(new Callback<ServicesModel>() {
            @Override
            public void onResponse(Call<ServicesModel> call, Response<ServicesModel> response) {
                ServicesModel servicesModel = response.body();
                if (servicesModel.getSuccess()) {
                    serviceList = servicesModel.getMessage().getServices();
                    Log.i(TAG, serviceList.size() + "");
                    Gson gson = new Gson();
                    String list_obj = gson.toJson(serviceList);
                    Log.i(TAG, list_obj);
                }
            }

            @Override
            public void onFailure(Call<ServicesModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    private void displaySelectedFragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame_id, fragment).commit();
    }

    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "920029550"));
                startActivity(callIntent);

            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "920029550"));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            } else {
                Log.e(TAG, "Permission not Granted");
            }
        }
    }

}
