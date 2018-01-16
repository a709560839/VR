package com.daydvr.store.view.person;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;

/**
 * @author a79560839
 */
public class SelectGenderActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton radioButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);

        radioGroup = findViewById(R.id.radioGroup);
        radioButton = findViewById(R.id.man);
        radioButton1 = findViewById(R.id.woman);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.man) {
                    radioButton1.setChecked(false);
                } else if (checkedId == R.id.woman) {
                    radioButton.setChecked(false);
                }
            }
        });
    }
}
