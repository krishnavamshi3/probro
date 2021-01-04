package com.style.probro.dashboard.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.style.probro.BuildConfig;
import com.style.probro.app_utils.AppConstants;
import com.style.probro.app_utils.AppRoute;
import com.style.probro.R;
import com.style.probro.firebase_api.FirebaseConstants;
import com.style.probro.models.MyCartItem;
import com.style.probro.models.PBArticle;

import java.util.List;

public class HomeFragment extends Fragment implements IAllArticleAdapterEventListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Query mQuery;
    private FirestoreRecyclerAdapter adapter;

    private ProgressBar mProgressBar;
    private FrameLayout mCartItemNoBadge;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        setupDrawerLayout(root);

        RecyclerView rv = root.findViewById(R.id.all_article_rv);
        setupRecyclerView(rv);

        mProgressBar = root.findViewById(R.id.home_all_article_loading_pb);
        mCartItemNoBadge =  root.findViewById(R.id.cart_layout);
        mCartItemNoBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppRoute.myCart(getActivity());
            }
        });
        mCartItemNoBadge.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppRoute.myCart(getActivity());
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            View headerView = navigationView.getHeaderView(0);
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
            if (account == null) {
                headerView.findViewById(R.id.header_login_layout).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.user_info_header_layout).setVisibility(View.GONE);
                Button button = headerView.findViewById(R.id.nav_header_login_button);
                button.setOnClickListener(v -> {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    AppRoute.firebaseUIActivity(getActivity());
                });
            } else {
                headerView.findViewById(R.id.user_info_header_layout).setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.header_login_layout).setVisibility(View.GONE);
                TextView userNameTV = headerView.findViewById(R.id.user_name);
                TextView userID = headerView.findViewById(R.id.user_id);
                userNameTV.setText(account.getDisplayName());
                userID.setText(account.getEmail());
            }
        }

        if(adapter != null) adapter.startListening();

        homeViewModel.getMyCartItemList().observe(getViewLifecycleOwner(), new Observer<List<MyCartItem>>() {
            @Override
            public void onChanged(List<MyCartItem> cartItems) {
                if(cartItems == null || cartItems.isEmpty()) {
                    mCartItemNoBadge.findViewById(R.id.cart_badge).setVisibility(View.GONE);
                } else {
                    mCartItemNoBadge.findViewById(R.id.cart_badge).setVisibility(View.VISIBLE);
                    TextView _badgeTV = (TextView) mCartItemNoBadge.findViewById(R.id.cart_badge);
                    _badgeTV.setText(String.valueOf(cartItems.size()));
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null)adapter.stopListening();
    }


    private void setupRecyclerView(RecyclerView recyclerView) {


        String collectionName = FirebaseConstants.ARTICLES_COLLECTION_NAME;
        mQuery = FirebaseFirestore.getInstance()
                .collection(collectionName)
                .whereEqualTo("availability", true)
                .limit(100);


        FirestoreRecyclerOptions<PBArticle> options = new FirestoreRecyclerOptions.Builder<PBArticle>()
                .setQuery(mQuery, PBArticle.class)
                .build();

        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AllArticlesAdapter(options, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupDrawerLayout(View root) {
        ImageView imageView = root.findViewById(R.id.home_menu);
        drawerLayout = root.findViewById(R.id.drawer_layout);
        navigationView = root.findViewById(R.id.navi_view);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
                imageView.setImageResource(R.drawable.ic_outline_backspace_black);
            }

            @Override
            public void onDrawerClosed(View view) {
                imageView.setImageResource(R.drawable.ic_menu_black);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


        imageView.setOnClickListener(v -> {
            if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            Activity activity=getActivity();
            if(activity == null) return true;
            int id = menuItem.getItemId();
            if(id == R.id.nav_share) {
                onShare();
            } else if (id == R.id.my_orders) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
                if(account != null && account.getEmail() != null) {
                    AppRoute.myOrdersList(getActivity());
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onClickPBArticle(PBArticle model) {
        Log.i(TAG, "onClickPBArticle : " + model.getName());
        AppRoute.productDescriptionActivity(getActivity(), model);
    }

    @Override
    public void onDataPresenting() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void onShare() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, AppConstants.SHARE_INTENT_EXTRA_TEXT);
            String shareMessage= AppConstants.share_message_text;
            shareMessage = shareMessage + AppConstants.appstore_text + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            shareIntent.setPackage("com.whatsapp");
            getActivity().startActivityForResult(Intent.createChooser(shareIntent, "choose one"), 123);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}