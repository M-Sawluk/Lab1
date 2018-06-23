package com.lab2.michau.lab2.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lab2.michau.lab2.R;
import com.lab2.michau.lab2.model.Grade;

import java.util.List;

public class GradesAdapter extends ArrayAdapter<Grade> {
    private List<Grade> grades;

    public GradesAdapter(@NonNull Activity activity, List<Grade> grades) {
        super(activity, R.layout.grade, grades);
        this.grades = grades;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.grade, null);

        RadioGroup btnGrp = (RadioGroup) convertView.findViewById(R.id.btnGrp);
        ((RadioButton) convertView.findViewById(R.id.b2)).setChecked(true);
        btnGrp.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton button = (RadioButton) group.findViewById(checkedId);
                        grades.get(position).setValue(Integer.parseInt(button.getText().toString()));
                    }
                }
        );
        TextView gradeTitle = (TextView) convertView.findViewById(R.id.gradeTitle);
        gradeTitle.setText(grades.get(position).getName());

        return convertView;
    }
}
