package com.thinkful.timeful;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;

public class EditTaskActivity extends AppCompatActivity {

   private Switch noteSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        EditText editName = (EditText) this.findViewById(R.id.editTextName);
        editName.setText(TimefulCore.inprogressTask.getName());
        EditText editDes = (EditText) this.findViewById(R.id.editTextDescript);
        editDes.setText(TimefulCore.inprogressTask.getDesript());
        this.noteSwitch = (Switch) this.findViewById(R.id.switch1);

    }

    public void editButtonPushed (View v)
    {
        EditText editName = (EditText) this.findViewById(R.id.editTextName);
        EditText editDes = (EditText) this.findViewById(R.id.editTextDescript);

        TimefulCore.inprogressTask.setName(editName.getText().toString());
        TimefulCore.inprogressTask.setDesript(editDes.getText().toString());



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


        Intent aIntent = new Intent(this, TimefulCalActivity.class);
        aIntent.putExtra("Task", TimefulCore.inprogressTask);
        this.startActivityForResult(aIntent, 1);
    }
}
