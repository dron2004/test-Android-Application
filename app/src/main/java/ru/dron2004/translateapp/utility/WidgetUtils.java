package ru.dron2004.translateapp.utility;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

public class WidgetUtils {
    public static void fitText(TextView textView, String text, float maxTextSizePx, float maxWidthPx) {
        textView.setEllipsize(null);
//        Log.d("happy","We adapt text Size;");
        int size = (int)textView.getTextSize();
        while (true) {
            Rect bounds = new Rect();
            Paint textPaint = textView.getPaint();
            textPaint.getTextBounds(text, 0, text.length(), bounds);
//            Log.d("happy","Bounds:"+bounds.width());
//            Log.d("happy","MaxSize:"+maxWidthPx);
//            Log.d("happy","TextSize:"+size);
//            Log.d("happy","MaxTextSize:"+maxTextSizePx);
//            Log.d("happy","LineCount:"+textView.getLineCount());
            if(bounds.width() > maxWidthPx){
                break;
            }
            if (size >= maxTextSizePx) {
                textView.setEllipsize(TextUtils.TruncateAt.END);
                break;
            }
            if (textView.getLineCount() > 1) {
                break;
            }
            size += 5;
//            Log.d("happy","Text Size:"+size);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }
}
