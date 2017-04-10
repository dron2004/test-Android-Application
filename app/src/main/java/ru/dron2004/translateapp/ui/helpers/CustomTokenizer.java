package ru.dron2004.translateapp.ui.helpers;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

public class CustomTokenizer implements MultiAutoCompleteTextView.Tokenizer {
    private char delimer;
    private String delimerAfter = " ";

    public CustomTokenizer(char t){
        this.delimer = t;
    }
    @Override
    public int findTokenStart(CharSequence text, int cursor) {
        int i = cursor;
        while (i > 0 && text.charAt(i - 1) != delimer) {
            i--;
        }
        while (i < cursor && text.charAt(i) == delimer) {
            i++;
        }
        return i;
    }

    @Override
    public int findTokenEnd(CharSequence text, int cursor) {
        int i = cursor;
        int len = text.length();
        while (i < len) {
            if (text.charAt(i) == delimer) {
                return i;
            } else {
                i++;
            }
        }
        return len;
    }

    @Override
    public CharSequence terminateToken(CharSequence text) {
        int i = text.length();
        while (i > 0 && text.charAt(i - 1) == delimer) {
            i--;
        }
        if (i > 0 && text.charAt(i - 1) == delimer) {
            return text;
        } else {
            if (text instanceof Spanned) {
                SpannableString sp = new SpannableString(text + Character.toString(delimer));
                TextUtils.copySpansFrom((Spanned) text, 0, text.length(),Object.class, sp, 0);
                return sp;
            } else {
                return text + Character.toString(delimer);
            }
        }
    }
}
