package com.mksmcqapplicationtest;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TooltipWindow {

    private static final int MSG_DISMISS_TOOLTIP = 100;
    private Context ctx;
    private PopupWindow tipWindow;
    private View contentView;
    private LayoutInflater inflater;
    TextView txtMessageForToolTip;
    ImageView imgForToolTip;

    public TooltipWindow(Context ctx) {
        this.ctx = ctx;
        tipWindow = new PopupWindow(ctx);

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.tooltip_layout, null);
        txtMessageForToolTip = (TextView) contentView.findViewById(R.id.tooltip_text);
        imgForToolTip = (ImageView) contentView.findViewById(R.id.imgForToolTip);

    }

    void showToolTip(View anchor, String Message) {
        try {
            txtMessageForToolTip.setText(Message);
            tipWindow.setHeight(LayoutParams.WRAP_CONTENT);
            tipWindow.setWidth(LayoutParams.WRAP_CONTENT);

            tipWindow.setOutsideTouchable(true);
            tipWindow.setTouchable(true);
            tipWindow.setFocusable(true);
            tipWindow.setBackgroundDrawable(new BitmapDrawable());

            tipWindow.setContentView(contentView);

            int screen_pos[] = new int[2];

            anchor.getLocationOnScreen(screen_pos);


            Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
                    + anchor.getWidth(), screen_pos[1] + anchor.getHeight());


            contentView.measure(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            int contentViewHeight = contentView.getMeasuredHeight();
            int contentViewWidth = contentView.getMeasuredWidth();
            int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
            int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);

            tipWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, position_x, position_y);


            handler.sendEmptyMessageDelayed(MSG_DISMISS_TOOLTIP, 4000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isTooltipShown() {
        if (tipWindow != null && tipWindow.isShowing())
            return true;
        return false;
    }

    void dismissTooltip() {
        if (tipWindow != null && tipWindow.isShowing())
            tipWindow.dismiss();
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_TOOLTIP:
                    if (tipWindow != null && tipWindow.isShowing())
                        tipWindow.dismiss();
                    break;
            }
        }

        ;
    };

}
