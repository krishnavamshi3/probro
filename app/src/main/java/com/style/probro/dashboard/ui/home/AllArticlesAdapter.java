package com.style.probro.dashboard.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.style.probro.R;
import com.style.probro.models.PBArticle;

public class AllArticlesAdapter extends FirestoreRecyclerAdapter<PBArticle, AllArticlesAdapter.ViewHolder> {
    private Context mContext;

    private IAllArticleAdapterEventListener iAllArticleAdapterEventListener;
    public AllArticlesAdapter(@NonNull FirestoreRecyclerOptions<PBArticle> options, IAllArticleAdapterEventListener listener) {
        super(options);
        this.iAllArticleAdapterEventListener = listener;
        mContext = ((Fragment)listener).getContext();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PBArticle model) {
        if(position == 0)
        this.iAllArticleAdapterEventListener.onDataPresenting();
        Glide.with(mContext)

                .load(model.getThumbimageurl())
                //.placeholder(R.drawable.ic_probro_icon_grey)
                //.apply(new RequestOptions().override(160, 200))
                .into(holder.articleThumbIV);
        holder.articleTitle.setText(model.getName());
        holder.articlePayPrice.setText(String.format(mContext.getString(R.string.price_text), model.getPayprice()));
        holder.articleMaxPrice.setText(String.format(mContext.getString(R.string.price_text), model.getMrpprice()));

        holder.rootView.setOnClickListener(v -> iAllArticleAdapterEventListener.onClickPBArticle(model));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_rv_list_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView articleThumbIV;
        private TextView articleTitle, articlePayPrice, articleMaxPrice;
        private View rootView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            articleMaxPrice = itemView.findViewById(R.id.article_mrp_price_tv);
            articlePayPrice = itemView.findViewById(R.id.article_pay_price_tv);
            articleTitle = itemView.findViewById(R.id.article_name_tv);
            articleThumbIV = itemView.findViewById(R.id.article_thumb_iv);
        }
    }
}
