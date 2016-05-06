package com.example.srikiransistla.homework5;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Srikiran Sistla on 2/28/2016.
 */
public class MyDialog extends DialogFragment implements View.OnClickListener{
    Button ok;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.dialog,null);
        ok=(Button) view.findViewById(R.id.dialog_ok);
        ok.setOnClickListener(this);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.dialog_ok){
            dismiss();
        }
    }
}
