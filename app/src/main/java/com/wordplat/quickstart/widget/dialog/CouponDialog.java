package com.wordplat.quickstart.widget.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wordplat.easydivider.RecyclerViewCornerRadius;
import com.wordplat.quickstart.R;
import com.wordplat.quickstart.adapter.BaseViewHolder;
import com.wordplat.quickstart.utils.AppUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by afon on 2017/2/7.
 */

public class CouponDialog extends BaseDialog {

    private RecyclerView Coupon_List;

    public CouponDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.setContentView(R.layout.dialog_new_coupon);
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setAttributes(lp);
            window.setGravity(Gravity.CENTER);
            this.setCancelable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        Coupon_List = (RecyclerView) findViewById(R.id.Coupon_List);
        CouponAdapter couponAdapter = new CouponAdapter(context);
        Coupon_List.setLayoutManager(new LinearLayoutManager(context));
        Coupon_List.setAdapter(couponAdapter);

        RecyclerViewCornerRadius radiusItemDecoration = new RecyclerViewCornerRadius(Coupon_List);
        radiusItemDecoration.setCornerRadius(0, 0, AppUtils.dpTopx(context, 6.5f), AppUtils.dpTopx(context, 6.5f));
        Coupon_List.addItemDecoration(radiusItemDecoration);
    }

    private static class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {

        private Context context;

        public CouponAdapter(Context context) {
            this.context = context;
        }

        @Override
        public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_coupon, parent, false);
            return new CouponViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(CouponViewHolder holder, int position) {
            holder.text.setText("position " + position);
            switch (position % 3) {
                case 0:
                    holder.text.setBackgroundColor(Color.parseColor("#aa0000"));
                    break;

                case 1:
                    holder.text.setBackgroundColor(Color.parseColor("#00aa00"));
                    break;

                case 2:
                    holder.text.setBackgroundColor(Color.parseColor("#0000aa"));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 200;
        }
    }

    private static class CouponViewHolder extends BaseViewHolder {

        @ViewInject(R.id.text) TextView text;

        public CouponViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static CouponDialog from(Context context) {
        return new CouponDialog(context);
    }
}
