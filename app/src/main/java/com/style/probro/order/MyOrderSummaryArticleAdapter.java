package com.style.probro.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.style.probro.R;
import com.style.probro.cart.ICartItemEventListener;
import com.style.probro.models.MyCartItem;

import java.util.List;

public class MyOrderSummaryArticleAdapter extends RecyclerView.Adapter<MyOrderSummaryArticleAdapter.MyViewHolder>{

    private Context mContext;
    private List<MyCartItem> myCartItemList;

    MyOrderSummaryArticleAdapter(Context context, List<MyCartItem> cartItems) {
        this.mContext = context;
        this.myCartItemList = cartItems;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_item, parent, false);
        return  new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyCartItem myCartItem = this.myCartItemList.get(holder.getAdapterPosition());
        int discountPercentage = ((myCartItem.getPbArticle().getMrpprice() - myCartItem.getPbArticle().getPayprice()) * 100)/myCartItem.getPbArticle().getMrpprice();
        holder.mArticleName.setText(myCartItem.getPbArticle().getName());
        holder.mArticlePayPrice.setText(String.format(mContext.getString(R.string.price_text), myCartItem.getPbArticle().getPayprice() * myCartItem.getQuantity()));
        holder.mArticleMRPPrice.setText(String.format(mContext.getString(R.string.price_text), myCartItem.getPbArticle().getMrpprice() * myCartItem.getQuantity()));
        holder.mArticleDiscountText.setText(String.format(mContext.getString(R.string.discount_text), discountPercentage));
        Glide.with(mContext)
                .load(myCartItem.getPbArticle().getThumbimageurl())
                //.placeholder(R.drawable.ic_probro_icon_grey)
                //.apply(new RequestOptions().override(140, 100))
                .into(holder.mArticleThumbImage);

        // Add selected size and color.
        String selectedSize = myCartItem.getSelectedSize();
        String selectedColor = myCartItem.getSelectedColor();
        int selectedQuantity = myCartItem.getQuantity();

        String extraText = "";
        if(selectedSize != null && !selectedSize.isEmpty()) {
            extraText+= "size: "+selectedSize+" ";
        }
        if(selectedColor != null && !selectedColor.isEmpty()) {
            extraText+= "color: "+selectedColor;
        }
        extraText+= "Qty: "+selectedQuantity;
        holder.mArticleExtraText.setText(extraText);
    }

    @Override
    public int getItemCount() {
        return myCartItemList != null ? myCartItemList.size() : 0;
    }

    public void setCartItems(List<MyCartItem> myCartItemList) {
        this.myCartItemList.clear();
        this.myCartItemList.addAll(myCartItemList);
        notifyDataSetChanged();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mArticleThumbImage;
        private TextView mArticleName;
        private TextView mArticleExtraText;
        private TextView mArticlePayPrice;
        private TextView mArticleMRPPrice, mArticleDiscountText, mArticleQuantityText;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mArticleThumbImage = itemView.findViewById(R.id.ct_imgae);
            mArticleName = itemView.findViewById(R.id.ct_article_name);
            mArticleExtraText = itemView.findViewById(R.id.ct_article_extra_info);
            mArticlePayPrice = itemView.findViewById(R.id.cart_pay_price_tv);
            mArticleMRPPrice = itemView.findViewById(R.id.cart_mrp_price_tv);
            mArticleDiscountText = itemView.findViewById(R.id.cart_discount_tv);
            mArticleQuantityText = itemView.findViewById(R.id.cart_quantity_tv);
        }
    }
}
