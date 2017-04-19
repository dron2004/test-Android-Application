package ru.dron2004.translateapp.ui.view_adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.dron2004.translateapp.R;

public class TipsAdapter extends ArrayAdapter<SpannableString>{
    private List<SpannableString> tips;

    public TipsAdapter(@NonNull Context context, @LayoutRes int resource, List<SpannableString> list) {
        super(context, resource);
        tips = list;
    }

    @Override
    public int getCount() {
        return tips.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        SpannableString tip = tips.get(position);

        View row = convertView;
        Holder holder;

        if(row == null)
        {
            row = inflater.inflate(R.layout.tip_element, parent, false);
            holder = new Holder();
            row.setTag(holder);
            holder.tipText = (TextView) row.findViewById(R.id.tipText);
        }

        holder = (Holder) row.getTag();
        holder.tipText.setText(tips.get(position), TextView.BufferType.SPANNABLE);
        return row;
    }

    public String getSelectedTip(int intex){
        return tips.get(intex).toString();
    }

    static class Holder {
        TextView tipText;
    }
}
