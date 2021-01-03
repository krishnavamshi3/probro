package com.style.probro.cart;

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
import com.bumptech.glide.request.RequestOptions;
import com.style.probro.R;
import com.style.probro.models.MyCartItem;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder>{

    private Context mContext;
    private List<MyCartItem> myCartItemList;
    private ICartItemEventListener iCartItemEventListener;

    MyCartAdapter(Context context, List<MyCartItem> cartItems, ICartItemEventListener listener) {
        this.mContext = context;
        this.myCartItemList = cartItems;
        this.iCartItemEventListener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
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
        holder.mArticleQuantityText.setText(String.valueOf(myCartItem.getQuantity()));
        holder.mSaveCart.setOnClickListener(v -> iCartItemEventListener.onSaveCartItem(myCartItem));

        holder.mRemoveCart.setOnClickListener(v -> iCartItemEventListener.onRemoveCartItem(myCartItem));

        holder.onQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = myCartItem.getQuantity();
                qty+=1;
                myCartItem.setQuantity(qty);
                iCartItemEventListener.onCartUpdate(myCartItem);
            }
        });

        holder.onQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = myCartItem.getQuantity();
                if(qty == 1) return;
                qty-=1;
                if(qty <1) qty = 1;
                myCartItem.setQuantity(qty);
                iCartItemEventListener.onCartUpdate(myCartItem);
            }
        });
        if (myCartItem.getPbArticle().isAvailability()) {
            holder.mSoldOut.setVisibility(View.GONE);
            holder.onQuantityPlus.setClickable(true);
            holder.onQuantityMinus.setClickable(true);
            holder.mQtyLayout.setVisibility(View.VISIBLE);
        } else {
            holder.mSoldOut.setVisibility(View.VISIBLE);
            holder.onQuantityPlus.setClickable(false);
            holder.onQuantityMinus.setClickable(false);
            holder.mQtyLayout.setVisibility(View.GONE);
        }
        // Add selected size and color.


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
        private TextView mArticleExtraText, mRemoveCart, mSaveCart;
        private TextView mArticlePayPrice;
        private TextView mArticleMRPPrice, mArticleDiscountText, mArticleQuantityText;

        private ImageButton onQuantityPlus, onQuantityMinus;
        private ImageView mSoldOut;
        private RelativeLayout mQtyLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mArticleThumbImage = itemView.findViewById(R.id.ct_imgae);
            mArticleName = itemView.findViewById(R.id.ct_article_name);
            mArticleExtraText = itemView.findViewById(R.id.ct_article_extra_info);
            mArticlePayPrice = itemView.findViewById(R.id.cart_pay_price_tv);
            mArticleMRPPrice = itemView.findViewById(R.id.cart_mrp_price_tv);
            mArticleDiscountText = itemView.findViewById(R.id.cart_discount_tv);
            mArticleQuantityText = itemView.findViewById(R.id.cart_quantity_tv);
            mRemoveCart = itemView.findViewById(R.id.cart_remove);
            mSaveCart = itemView.findViewById(R.id.cart_save_for_later);
            onQuantityMinus = itemView.findViewById(R.id.cart_btn_qty_minus);
            onQuantityPlus = itemView.findViewById(R.id.cart_btn_qty_plus);
            mSoldOut = itemView.findViewById(R.id.soldout_iv);
            mQtyLayout = itemView.findViewById(R.id.qty_layout);
        }
    }
}
