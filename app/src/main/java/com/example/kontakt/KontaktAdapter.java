package com.example.kontakt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class KontaktAdapter extends ArrayAdapter<Kontakt>
{
    private Context context;
    private List<Kontakt> kontakt;

    public KontaktAdapter(Context context, List<Kontakt> list)
    {
        super(context, R.layout.kolona_layout, list);
        this.context = context;
        this.kontakt = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.kolona_layout, parent, false);

        TextView tvChar = convertView.findViewById(R.id.tvChar);
        TextView tvIme = convertView.findViewById(R.id.tvIme);
        TextView tvMail = convertView.findViewById(R.id.tvMail);

        tvChar.setText(kontakt.get(position).getIme().toUpperCase().charAt(0) + "");
        tvIme.setText(kontakt.get(position).getIme());
        tvMail.setText(kontakt.get(position).getEmail());

        return convertView;
    }
}
