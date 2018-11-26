package com.mksmcqapplicationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mksmcqapplicationtest.util.AppUtility;

public class InstructionActivity extends AppCompatActivity {

    TextView txtinstruction;
    Button btn_startExam, btn_skip;
    Bundle bundle;
    String Instruction;
    View parentLayout;

    String testID, testName, ClassName;
    int testDuration;
    String NegativeMarkingString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        parentLayout = findViewById(android.R.id.content);
        txtinstruction = (TextView) findViewById(R.id.txtinstruction);
        btn_startExam = (Button) findViewById(R.id.btn_startExam);
        btn_skip = (Button) findViewById(R.id.btn_skip);

        bundle = getIntent().getExtras();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            testID = bundle.getString(AppUtility.KEY_TEST_ID);
            testName = bundle.getString(AppUtility.KEY_TEST_NAME);
            testDuration = bundle.getInt("Test Time");
            ClassName = bundle.getString("ClassName");
            NegativeMarkingString = bundle.getString("NegativeMarking");
            Instruction = bundle.getString("Instruction");
        }

        txtinstruction.setText(Instruction);
        btn_startExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startExam();
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void startExam() {
        try {
            Intent examIntent = new Intent(InstructionActivity.this, ExamActivity.class);
            examIntent.putExtra(AppUtility.KEY_TEST_ID, testID);
            examIntent.putExtra(AppUtility.KEY_TEST_NAME, testName);
            examIntent.putExtra("Test Time", testDuration);
            examIntent.putExtra("ClassName", ClassName);
            examIntent.putExtra("NegativeMarking", NegativeMarkingString);
            startActivity(examIntent);
        } catch (Exception e) {
            PrintCatchException pc = new PrintCatchException(InstructionActivity.this, parentLayout, "InstructionActivity", "startExam", e);
            pc.showCatchException();
        }
    }
}
