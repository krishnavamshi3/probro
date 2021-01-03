package com.style.probro.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.style.probro.AppDatabase;
import com.style.probro.models.PBOrder;
import com.style.probro.R;
import com.style.probro.app_utils.AppRoute;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.firebase_api.ArticleAPI;
import com.style.probro.models.MyCartItem;
import com.style.probro.room.MyCartDao;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity implements ICartItemEventListener {

    RecyclerView mRecyclerView;
    MyCartAdapter adapter;

    List<MyCartItem> mCarItemList = new ArrayList<>();

    private ArticleAPI mArticleApi;

    private TextView totalPriceTV;

    private int totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        AppUtils.hideActionBar(getSupportActionBar());

        totalPriceTV = findViewById(R.id.cart_total_price);
        mRecyclerView = findViewById(R.id.my_cart_rv);
        setupRecyclerView(mRecyclerView);
        mArticleApi = new ArticleAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sas();
    }

    void sas() {
        AppDatabase.getDb().myCartDao().getAll().observe(MyCartActivity.this, new Observer<List<MyCartItem>>() {
            @Override
            public void onChanged(List<MyCartItem> cartItemList) {
                reCalculatePrice(cartItemList);
                ((MyCartAdapter) adapter).setCartItems(cartItemList);
            }
        });
    }

    private void setupRecyclerView(RecyclerView rv) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        adapter = new MyCartAdapter(this, mCarItemList, this);
        rv.setAdapter(adapter);
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }

    public void onClickPlaceOrder(View view) {
        if(mCarItemList.isEmpty()) {
            Toast.makeText(this, "Add something to cart to place order!", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Sorry! We are not accepting any orders now!", Toast.LENGTH_SHORT).show();

        PBOrder pbOrder = new PBOrder();
        pbOrder.setCartItems(mCarItemList);
        pbOrder.setTotalPrice(reCalculatePrice(mCarItemList));

        AppRoute.orderSummary(this, pbOrder);

    }

    @Override
    public void onRemoveCartItem(MyCartItem myCartItem) {
        new DeleteAsyncTask(this, myCartItem, new IPostExecute() {
            @Override
            public void onPostExecute() {
                Toast.makeText(MyCartActivity.this, "Successfully removed from cart!", Toast.LENGTH_SHORT).show();
            }
        }).execute();

    }

    @Override
    public void onSaveCartItem(MyCartItem myCartItem) {

    }

    @Override
    public void onCartUpdate(MyCartItem myCartItem) {
        new UpdateAsyncTask(this, myCartItem, new IPostExecute() {
            @Override
            public void onPostExecute() {
                // Toast.makeText(MyCartActivity.this, "Successfully updated the  from cart!", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private int reCalculatePrice(List<MyCartItem> cartItems) {
        int price = 0;
        for(MyCartItem cartItem : cartItems) {
            if(!cartItem.getPbArticle().isAvailability()) continue;
            int qty = cartItem.getQuantity();
            int perPrice = cartItem.getPbArticle().getPayprice();
            int totalCartItemPrice = perPrice * qty;
            price += totalCartItemPrice;
        }
        totalPrice = price;
        totalPriceTV.setText(String.format(getString(R.string.price_text), totalPrice));
        return totalPrice;
    }

    interface IPostExecute {
        void onPostExecute();
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;
        private MyCartItem myCartItem;
        private IPostExecute iPostExecute;

        public DeleteAsyncTask(Activity activity, MyCartItem myCartItem, IPostExecute iPostExecute) {
            weakActivity = new WeakReference<>(activity);
            this.myCartItem = myCartItem;
            this.iPostExecute = iPostExecute;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MyCartDao agentDao = AppDatabase.getDb().myCartDao();
            agentDao.delete(this.myCartItem);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(this.iPostExecute != null) iPostExecute.onPostExecute();
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;
        private MyCartItem myCartItem;
        private IPostExecute iPostExecute;

        public UpdateAsyncTask(Activity activity, MyCartItem myCartItem, IPostExecute iPostExecute) {
            weakActivity = new WeakReference<>(activity);
            this.myCartItem = myCartItem;
            this.iPostExecute = iPostExecute;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MyCartDao agentDao = AppDatabase.getDb().myCartDao();
            agentDao.updateCartItem(this.myCartItem);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(this.iPostExecute != null) iPostExecute.onPostExecute();
        }
    }
}