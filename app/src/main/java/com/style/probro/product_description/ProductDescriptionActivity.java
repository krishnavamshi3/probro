package com.style.probro.product_description;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.style.probro.AppDatabase;
import com.style.probro.R;
import com.style.probro.app_utils.AppConstants;
import com.style.probro.app_utils.AppUtils;
import com.style.probro.room.MyCartDao;
import com.style.probro.models.MyCartItem;
import com.style.probro.models.PBArticle;

import java.lang.ref.WeakReference;
import java.util.List;

public class ProductDescriptionActivity extends AppCompatActivity {
    ImageView mProductImage1, mProductImage2,mProductImage3, mProductImage4,mProductImage5, mProductImage6;
    PBArticle mPBArticle;
    Spinner sizeSpinner, colorSpinner;

    String selectedSize = "";
    String selectedColor = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        AppUtils.hideActionBar(getSupportActionBar());

        mPBArticle = getIntent().getParcelableExtra(AppConstants.PB_ARTICLE_INTENT_KEY);

        if(mPBArticle == null) finish();

        setProductLookUp();
        setProductImages();

        if(mPBArticle.getSize() != null && !mPBArticle.getSize().isEmpty()) {
            sizeSpinner = findViewById(R.id.pd_size_spinner);

            ArrayAdapter<String> sizeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.pd_spinner_item,mPBArticle.getSize()) {

                public View getView(int position, View convertView, ViewGroup parent) {

                    View v = super.getView(position, convertView, parent);

                    ((TextView) v).setTextSize(16);

                    return v;

                }

                public View getDropDownView(int position, View convertView,ViewGroup parent) {

                    View v = super.getDropDownView(position, convertView,parent);

                    ((TextView) v).setGravity(Gravity.CENTER);

                    return v;

                }

            };
            sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedSize = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // set simple layout resource file
            // for each item of spinner
            sizeSpinnerAdapter.setDropDownViewResource(
                    android.R.layout
                            .simple_spinner_dropdown_item);

            // Set the ArrayAdapter (ad) data on the
            // Spinner which binds data to spinner
            sizeSpinner.setAdapter(sizeSpinnerAdapter);
        } else {
            findViewById(R.id.pd_size_root_layout).setVisibility(View.GONE);
        }

        if(mPBArticle.getColor() != null && !mPBArticle.getColor().isEmpty()) {
            colorSpinner = findViewById(R.id.pd_color_spinner);
//            ArrayAdapter colorSpinnerAdapter
//                    = new ArrayAdapter(
//                    this,
//                    android.R.layout.simple_spinner_item,
//                    mPBArticle.getColor());
            ArrayAdapter<String> colorSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.pd_spinner_item, mPBArticle.getColor()) {

                public View getView(int position, View convertView, ViewGroup parent) {

                    View v = super.getView(position, convertView, parent);

                    ((TextView) v).setTextSize(16);

                    return v;

                }

                public View getDropDownView(int position, View convertView,ViewGroup parent) {

                    View v = super.getDropDownView(position, convertView,parent);

                    ((TextView) v).setGravity(Gravity.CENTER);

                    return v;

                }

            };

            colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedColor = parent.getItemAtPosition(position).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            // set simple layout resource file
            // for each item of spinner
            colorSpinnerAdapter.setDropDownViewResource(
                    android.R.layout
                            .simple_spinner_dropdown_item);

            // Set the ArrayAdapter (ad) data on the
            // Spinner which binds data to spinner
            colorSpinner.setAdapter(colorSpinnerAdapter);
        } else {
            findViewById(R.id.pd_color_root_layout).setVisibility(View.GONE);
        }

    }

    private void setProductImages() {
        mProductImage1 = findViewById(R.id.product_iv_1);
        mProductImage2 = findViewById(R.id.product_iv_2);
        mProductImage3 = findViewById(R.id.product_iv_3);
        mProductImage4 = findViewById(R.id.product_iv_4);
        mProductImage5 = findViewById(R.id.product_iv_5);
        mProductImage6 = findViewById(R.id.product_iv_6);
        ImageView[] imageViewList = new ImageView[] {mProductImage1, mProductImage2,mProductImage3, mProductImage4,mProductImage5, mProductImage6};
        for(ImageView iv : imageViewList) {
            iv.setVisibility(View.GONE);
        }

        for(int i =0;i< mPBArticle.getImages().size() && i < imageViewList.length; i++) {
            imageViewList[i].setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mPBArticle.getImages().get(i).getUrl())
                    //.override(200, 200)
                    .placeholder(R.drawable.ic_probro_icon_grey)
                    .into(imageViewList[i]);

        }
    }

    private void setProductLookUp() {
        int discountPercentage = ((mPBArticle.getMrpprice() - mPBArticle.getPayprice()) * 100)/mPBArticle.getMrpprice();
        TextView articleNameTV = findViewById(R.id.pd_name_tv);
        TextView articlePayPriceTV = findViewById(R.id.pd_payprice_tv);
        TextView articleMaxPriceTV = findViewById(R.id.pd_mrpprice_tv);
        TextView articleDiscountTV = findViewById(R.id.pd_discount_tv);
        TextView articleProductDescriptionTV = findViewById(R.id.pd_product_description_tv);


        articleNameTV.setText(mPBArticle.getName());
        articlePayPriceTV.setText(String.format(getString(R.string.price_text), mPBArticle.getPayprice()));
        articleMaxPriceTV.setText(String.format(getString(R.string.price_text), mPBArticle.getMrpprice()));
        articleDiscountTV.setText(String.format(getString(R.string.discount_text), discountPercentage));

        articleProductDescriptionTV.setText(mPBArticle.getDescription());


    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }

    public void onAddArticleToBag(View view) {
        if(findViewById(R.id.pd_size_root_layout).getVisibility() == View.VISIBLE  && selectedColor.isEmpty()) {
            Toast.makeText(this, "Please select a size!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(findViewById(R.id.pd_color_root_layout).getVisibility() == View.VISIBLE  && selectedSize.isEmpty()) {
            Toast.makeText(this, "Please select a color!", Toast.LENGTH_SHORT).show();
            return;
        }

        MyCartItem myCartItem = new MyCartItem();
        myCartItem.setQuantity(1);
        myCartItem.setSelectedColor(selectedColor);
        myCartItem.setSelectedSize(selectedSize);
        myCartItem.setPbArticle(mPBArticle);
        myCartItem.setCartItemID(mPBArticle.getId() + selectedColor + selectedSize);

        new InsertAsyncTask(this, myCartItem, new IPostExecute() {
            @Override
            public void onPostExecute() {
                Toast.makeText(ProductDescriptionActivity.this, "Successfully added to cart!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).execute();

    }

    interface IPostExecute {
        void onPostExecute();
    }

    private static class InsertAsyncTask extends AsyncTask<Void, Void, Void> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;
        private MyCartItem myCartItem;
        private IPostExecute iPostExecute;

        public InsertAsyncTask(Activity activity, MyCartItem myCartItem, IPostExecute iPostExecute) {
            weakActivity = new WeakReference<>(activity);
            this.myCartItem = myCartItem;
            this.iPostExecute = iPostExecute;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MyCartDao agentDao = AppDatabase.getDb().myCartDao();
            try {
                agentDao.insertCartItem(this.myCartItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(this.iPostExecute != null) iPostExecute.onPostExecute();
        }
    }
}


