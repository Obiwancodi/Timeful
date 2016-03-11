package com.thinkful.timeful;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.parse.ParseUser;

public class EditTaskActivity extends AppCompatActivity {

   private Switch noteSwitch;
    private SeekBar seekBar1;
    private TextView expEditText;
    private TextView skillEditText;
    private TextView diffExp;
    private int exp;
    private int skillExp;
    private RadioGroup editRadioGroup;
    private String taskSkill;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        EditText editName = (EditText) this.findViewById(R.id.editEditName);
        editName.setText(TimefulCore.inprogressTask.getName());
        EditText editDes = (EditText) this.findViewById(R.id.editEditDescr);
        editDes.setText(TimefulCore.inprogressTask.getDesript());
        editRadioGroup = (RadioGroup) findViewById(R.id.radioGroupEdit);

        if (TimefulCore.inprogressTask.getSkill().equals("fit"))
        {
            editRadioGroup.check(R.id.fitnessRadioEdit);
            taskSkill = "fit";
        }

        else if (TimefulCore.inprogressTask.getSkill().equals("social"))
        {
            editRadioGroup.check(R.id.socialRadioEdit);
            taskSkill = "social";
        }

        else if (TimefulCore.inprogressTask.getSkill().equals("career"))
        {
            editRadioGroup.check(R.id.careerRadioEdit);
            taskSkill = "career";
        }

        else
        {
            editRadioGroup.check(R.id.hobbiesRadioEdit);
            taskSkill = "hobbies";
        }

        editRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.fitnessRadioEdit) {
                    taskSkill = "fit";
                } else if (checkedId == R.id.socialRadioEdit) {
                    taskSkill = "social";
                    System.out.println("Social WORKS");
                } else if (checkedId == R.id.careerRadioEdit) {
                    taskSkill = "career";
                } else if (checkedId == R.id.hobbiesRadioEdit) {
                    taskSkill = "hobbies";
                } else {
                    taskSkill = "n/a";
                }
            }
        });






         expEditText = (TextView) findViewById(R.id.editExpText);
        expEditText.setText(TimefulCore.inprogressTask.getExp() + "");
         skillEditText = (TextView) findViewById(R.id.skillEditText);
        skillExp = TimefulCore.inprogressTask.getExp()/4;
        skillEditText.setText(skillExp + "");
         diffExp = (TextView) findViewById(R.id.diffEdtitText);
        diffExp.setText(diffSetting(TimefulCore.inprogressTask.getExp()));
        seekBar1 = (SeekBar) findViewById(R.id.editSeekBar);
        seekBar1.setMax(50);
        seekBar1.setProgress(TimefulCore.inprogressTask.getExp());

                seekBar1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                    int progress = TimefulCore.inprogressTask.getExp();


                    @Override

                    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                        progress = progresValue;
                        diffExp.setText(diffSetting(progress));
                        expEditText.setText(progress + "");
                        skillEditText.setText(progress / 4 + "");


                    }


                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                        diffExp.setText(diffSetting(progress));
                        expEditText.setText(progress + "");
                        skillEditText.setText(progress / 4 + "");


                    }


                    @Override

                    public void onStopTrackingTouch(SeekBar seekBar) {

                        diffExp.setText(diffSetting(progress));
                        expEditText.setText(progress + "");
                        skillEditText.setText(progress / 4 + "");
                        exp = progress;
                        skillExp = progress / 4;

                    }

                });


    }





    public void editButtonPushed (View v)
    {
        EditText editName = (EditText) this.findViewById(R.id.editEditName);
        EditText editDes = (EditText) this.findViewById(R.id.editEditDescr);
        TimefulCore.editTask = TimefulCore.inprogressTask;

        //TimefulCore.userTasks.remove(TimefulCore.inprogressTask);
        //TimefulCore.staticAdapter.notifyDataSetChanged();
        TimefulCore.inprogressTask = new Tasks();
        TimefulCore.inprogressTask.setUser(ParseUser.getCurrentUser());
        TimefulCore.inprogressTask.setName(editName.getText().toString());
        TimefulCore.inprogressTask.setDesript(editDes.getText().toString());
        TimefulCore.inprogressTask.setExp(exp);
        TimefulCore.inprogressTask.setSkillPoints(skillExp);
        TimefulCore.inprogressTask.setCompleted(false);
        TimefulCore.inprogressTask.setCanceled(false);
        TimefulCore.inprogressTask.setEdited(false);
        TimefulCore.inprogressTask.setExpired(false);
        TimefulCore.inprogressTask.setSkill(taskSkill);
        TimefulCore.inprogressTask.setReType(TimefulCore.editTask.getReType());

        Intent aIntent = new Intent(this, StartDateCalActivity.class);
        aIntent.putExtra("Task", TimefulCore.inprogressTask);
        this.startActivityForResult(aIntent, 1);
    }
    public String diffSetting(int exp)
    {
        if (exp <= 5)
        {
            return "Trivial";
        }
        else if (exp <= 20)
        {
            return "Easy";
        }
        else if (exp<= 35)
        {
            return "Normal";
        }
        else if (exp<= 45)
        {
            return "Hard";
        }
        else
        {
            return "Very Hard";
        }

    }
}
