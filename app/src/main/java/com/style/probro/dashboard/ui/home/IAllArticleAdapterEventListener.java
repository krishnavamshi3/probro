package com.style.probro.dashboard.ui.home;

import com.style.probro.models.PBArticle;

public interface IAllArticleAdapterEventListener {
    void onClickPBArticle(PBArticle model);

    void onDataPresenting();
}
