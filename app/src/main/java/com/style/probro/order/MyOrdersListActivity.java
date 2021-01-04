package com.style.probro.order;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.style.probro.R;
import com.style.probro.app_utils.AppRoute;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.models.MyCartItem;
import com.style.probro.models.PBOrder;

import java.util.List;

public class MyOrdersListActivity extends AppCompatActivity {
    private Query mQuery;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_list);
        AppUtils.hideActionBar(getSupportActionBar());

        RecyclerView rv = (RecyclerView) findViewById(R.id.orders_recycler_view);
        setupRecyclerView(rv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null) adapter.startListening();
    }

    private void setupRecyclerView(RecyclerView rv) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null || account.getEmail() == null) {
            Toast.makeText(this, "Cant show all orders!", Toast.LENGTH_SHORT).show();
            return;
        }
        String userName = account.getEmail()
                .replace("@", "")
                .replace(".", "")
                .replace("gmail", "")
                .replace("com","");

        String collectionName = userName +"_orders";
        mQuery = FirebaseFirestore.getInstance()
                .collection(collectionName)
                .limit(100);

        FirestoreRecyclerOptions<PBOrder> options = new FirestoreRecyclerOptions.Builder<PBOrder>()
                .setQuery(mQuery, PBOrder.class)
                .build();


        adapter = new MyOrdersAdapter(this, options);
        rv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) adapter.stopListening();
    }


    public void onBackButtonClick(View view) {
        onBackPressed();
    }

    public static class MyOrdersAdapter extends FirestoreRecyclerAdapter<PBOrder, MyOrdersAdapter.ViewHolder> {

        MyOrdersListActivity myActivty;

        public MyOrdersAdapter(MyOrdersListActivity myOrdersActivity, FirestoreRecyclerOptions<PBOrder> options) {
            super(options);
            this.myActivty = myOrdersActivity;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order, parent, false);
            return new ViewHolder(view);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public final TextView mOrderItemTitleText;
            public final TextView mOrderExtraInfoText;
            public final TextView mOrderDateText;
            public final ConstraintLayout mCardView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = view.findViewById(R.id.order_image_view);
                mOrderItemTitleText = view.findViewById(R.id.order_item_title_tv);
                mOrderExtraInfoText = view.findViewById(R.id.order_extra_info);
                mOrderDateText = view.findViewById(R.id.order_status_date_tv);
                mCardView = view.findViewById(R.id.root_card_view);
            }
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {

        }

        @Override
        public void onDataChanged() {
            // Called each time there is a new query snapshot. You may want to use this method
            // to hide a loading spinner or check for the "no documents" state and update your UI.
            // ...
        }

        @Override
        public void onError(FirebaseFirestoreException e) {
            // Called when there is an error getting a query snapshot. You may want to update
            // your UI to display an error message to the user.
            // ...
            Toast.makeText(myActivty, "Error Connecting to the firebase : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull final PBOrder model) {

            List<MyCartItem> cartItems = model.getCartItems();
            if(cartItems.isEmpty()) return;
            String orderItemName = "";
            String extraInfo = "";
            // Add selected size and color.
            String selectedSize ="";
            String selectedColor ="";
            int selectedQuantity = 0;

            String extraText = "";
            if(cartItems.size() == 1) {
                orderItemName = cartItems.get(0).getPbArticle().getName();
                selectedSize = cartItems.get(0).getSelectedSize();
                selectedColor = cartItems.get(0).getSelectedColor();
                if(selectedSize!= null && !selectedSize.isEmpty()) {
                    extraText+= " size: "+selectedSize;
                }
                if(selectedColor != null && !selectedColor.isEmpty()) {
                    extraText+= " color: "+selectedColor;
                }
                extraText+= " Qty: "+cartItems.get(0).getQuantity();
            } else {
                orderItemName = cartItems.get(0).getPbArticle().getName();
                orderItemName += " "+String.valueOf(cartItems.size()-1);
                selectedQuantity = 0;
                for (MyCartItem myCartItem : cartItems) {
                    selectedQuantity+=myCartItem.getQuantity();
                }
                extraText+= "Qty: "+selectedQuantity;

            }

            Glide.with(myActivty)
                    .load(cartItems.get(0).getPbArticle().getThumbimageurl())
                    .into(holder.mImageView);
            holder.mOrderItemTitleText.setText(orderItemName);
            holder.mOrderExtraInfoText.setText(extraText);

            String orderDate = model.getOrderDate().toString();

            holder.mOrderDateText.setText(orderDate);

            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppRoute.myOrderDetail(myActivty, model);
                }
            });
        }

    }

}