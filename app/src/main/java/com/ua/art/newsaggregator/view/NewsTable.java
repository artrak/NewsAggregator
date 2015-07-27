package com.ua.art.newsaggregator.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.ua.art.newsaggregator.model.News;

public class NewsTable {

    private Context mContext;
    private LinearLayout buttons_layout;
    private static final int COLUMNS = 3;
    private int counter = 0;

    public NewsTable(Context mContext, LinearLayout linearLayout) {
        this.mContext = mContext;
        buttons_layout = linearLayout;
    }

    public void createTable() {
        buttons_layout.removeAllViewsInLayout();
        setParamsForLayout(buttons_layout);

        ScrollView scrollView = new ScrollView(mContext);
        buttons_layout.addView(scrollView);

        TableLayout tableLayout = new TableLayout(mContext);
        //tableLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        tableLayout.setWeightSum(4);

        double newsCount = (double) News.values().length;
        for (int i = 0; i < Math.ceil(newsCount / COLUMNS); i++) {
            tableLayout.addView(prepareRow());
        }
        scrollView.addView(tableLayout);
    }

    private void setParamsForLayout(LinearLayout linearLayout) {
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private View prepareRow() {
        TableRow row = new TableRow(mContext);
        row.setGravity(Gravity.CENTER_HORIZONTAL);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        for (int j = 0; j < COLUMNS; j++, counter++) {
            if (counter < News.values().length) {
                //row.setPadding(0, 0, 10, 0);
                row.addView(prepareButton(News.values()[counter]));
            }
        }
        return row;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private View prepareButton(final News news) {
        final Button button = new Button(mContext);
        button.setText(mContext.getString(news.getRuResource()));
        int width = (mContext.getResources().getDisplayMetrics().widthPixels / COLUMNS)-20;
        button.setWidth(width);
        button.setHeight(width);

        button.setBackground(mContext.getResources().getDrawable(news.getImgResourceClicked()));
        //button.setPadding(10, 10, 10, 10);

        LinearLayout.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(2, 6, 2, 2);

        //layoutParams.span = 1;
        //layoutParams.setMargins(3, 3, 3, 3);
        layoutParams.width = TableRow.LayoutParams.WRAP_CONTENT;
        layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = 9;

        button.setLayoutParams(layoutParams);
        button.setTag(news.getRuResource());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news.isClicked()) {
                    news.setClicked(false);
                } else {
                    news.setClicked(true);
                }
                button.setBackground(mContext.getResources().getDrawable(getImgResourceOnClick(news)));
            }
        });
        return button;
    }

    public int getImgResourceOnClick(News news) {
        return news.isClicked() ? news.getImgResourceClicked() : news.getImgResourceUnclicked();
    }
}
