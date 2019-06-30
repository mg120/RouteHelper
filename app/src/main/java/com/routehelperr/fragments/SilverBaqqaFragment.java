package com.routehelperr.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.routehelperr.R;
import com.routehelperr.activities.PaymentMethod;


public class SilverBaqqaFragment extends Fragment implements View.OnClickListener {

    TextView baqqa_desc_txtV, service_cost_txtV;
    Button selectBaqqa_btn;
    CheckBox checkBox;
    ConstraintLayout silverBaqa_layout;
    ProgressBar progressBar;

    String baqqa_desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            baqqa_desc = getArguments().getString("silver_desc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_silver_baqqa, container, false);
        baqqa_desc_txtV = view.findViewById(R.id.silverBaqqa_desc_txtV_id);
        selectBaqqa_btn = view.findViewById(R.id.select_silverBaqqa_btn_id);
        service_cost_txtV = view.findViewById(R.id.service_cost_txtV_id);
        checkBox = view.findViewById(R.id.silverBaqqa_checkBox_id);
        silverBaqa_layout = view.findViewById(R.id.silverBaqa_layout_id);
        progressBar = view.findViewById(R.id.silverBaqa_progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baqqa_desc_txtV.setText(Html.fromHtml(baqqa_desc, Html.FROM_HTML_MODE_COMPACT));
        } else {
            baqqa_desc_txtV.setText(Html.fromHtml(baqqa_desc));
        }

        progressBar.setVisibility(View.GONE);
        silverBaqa_layout.setVisibility(View.VISIBLE);
        selectBaqqa_btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                    if (isChecked) {

                                                        int cost = Integer.parseInt(service_cost_txtV.getText().toString());
                                                        cost = cost + 49;
                                                        service_cost_txtV.setText(String.valueOf(cost) + "ريال");

                                                    } else {
//                                                        int cost = Integer.parseInt(goldBaqqa_cost_txtV.getText().toString());
//                                                        cost = cost  49;
                                                        service_cost_txtV.setText(String.valueOf(250) + "ريال");
                                                    }
                                                }
                                            }
        );
    }

    @Override
    public void onClick(View v) {
        // Get the text from the EditText
        String cost_txt = service_cost_txtV.getText().toString();

        // Put the String to pass back into an Intent and close this activity
//        Intent intent = new Intent();
//        intent.putExtra("baqqa_cost", cost_txt);
//        getActivity().setResult(RESULT_OK, intent);
//        getActivity().finish();

        Intent intent = new Intent(getActivity(), PaymentMethod.class);
        intent.putExtra("cost", cost_txt);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
