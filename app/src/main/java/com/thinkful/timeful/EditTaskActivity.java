package com.thinkful.timeful;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        EditText editName = (EditText) this.findViewById(R.id.editTextName);
        editName.setText(TimefulCore.inprogressTask.getName());
        EditText editDes = (EditText) this.findViewById(R.id.editTextDescript);
        editDes.setText(TimefulCore.inprogressTask.getDesript());
        editRadioGroup = (RadioGroup) findViewById(R.id.editRadioGroup);

        if (TimefulCore.inprogressTask.getSkill().equals("fit"))
        {
            editRadioGroup.check(R.id.fitEdit);
            taskSkill = "fit";
        }

        else if (TimefulCore.inprogressTask.getSkill().equals("social"))
        {
            editRadioGroup.check(R.id.socialEdit);
            taskSkill = "social";
        }

        else if (TimefulCore.inprogressTask.getSkill().equals("career"))
        {
            editRadioGroup.check(R.id.careerEdit);
            taskSkill = "career";
        }

        else
        {
            editRadioGroup.check(R.id.hobEdit);
            taskSkill = "hobbies";
        }

        editRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.fitEdit) {
                    taskSkill = "fit";
                } else if (checkedId == R.id.socialEdit) {
                    taskSkill = "social";
                    System.out.println("Social WORKS");
                } else if (checkedId == R.id.careerEdit) {
                    taskSkill = "career";
                } else if (checkedId == R.id.hobEdit) {
                    taskSkill = "hobbies";
                } else {
                    taskSkill = "n/a";
                }
            }
        });


        this.noteSwitch = (Switch) this.findViewById(R.id.switch1);



         expEditText = (TextView) findViewById(R.id.editExpText);
        expEditText.setText(TimefulCore.inprogressTask.getExp() + "");
         skillEditText = (TextView) findViewById(R.id.skillExpEdit);
        skillEditText.setText(TimefulCore.inprogressTask.getSkill() + "");
         diffExp = (TextView) findViewById(R.id.diffExpEdit);
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
        EditText editName = (EditText) this.findViewById(R.id.editTextName);
        EditText editDes = (EditText) this.findViewById(R.id.editTextDescript);
        TimefulCore.inprogressTask.setEdited(true);
        TimefulCore.inprogressTask.saveInBackground();
        TimefulCore.userTasks.remove(TimefulCore.inprogressTask);
        TimefulCore.staticAdapter.notifyDataSetChanged();
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



        if (this.noteSwitch.isChecked())
        {
            TimefulCore.inprogressTask.setNote("yes");
           /* final ViewGroup rootFrameLayout = (ViewGroup) this.getWindow().peekDecorView();
            LayoutInflater li = LayoutInflater.from(this);
            View noteview = li.inflate(R.layout.switchonform, null);
            LinearLayout placeHolder = (LinearLayout)this.findViewById(R.id.placeHolderLayout);
            placeHolder.addView(noteview);
            */
        }
        else
        {
            TimefulCore.inprogressTask.setNote("no");
        }

        TimefulCore.inprogressTask.saveInBackground();
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
