package com.android.firebaseapp.firebaseapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userReference = databaseReference.child("usuario");

    private static final String SHARED_PREFERENCES = "fileIndex";
    private int index;

    private Button button;
    private EditText name, lastName, age;
    private RadioGroup group;
    private RadioButton sex;

    private Usuario user = new Usuario();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            button = (Button) findViewById(R.id.buttonId);
            group = (RadioGroup) findViewById(R.id.groupId); //Sex
            name = (EditText) findViewById(R.id.nameId);
            lastName = (EditText) findViewById(R.id.lastNameId);
            age = (EditText) findViewById(R.id.ageId);

            final SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, 0);
            if(sharedPreferences.contains("index")){
                index = sharedPreferences.getInt("index", 1);
            } else {
                index = 1;
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(group.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getApplicationContext(), "Inform the Sex", Toast.LENGTH_SHORT).show();
                    } else if (name.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inform the Name", Toast.LENGTH_SHORT).show();
                    } else if(lastName.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inform the Lastname", Toast.LENGTH_SHORT).show();
                    } else if(age.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inform the Age", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        int option = group.getCheckedRadioButtonId();
                        sex = (RadioButton) findViewById(option);

                        String currentIndex = (index < 10 ? "0" + index : Integer.toString(index));
                        user.setNome(name.getText().toString());
                        user.setSobrenome(lastName.getText().toString());
                        user.setIdade(Integer.parseInt(age.getText().toString()));
                        user.setSexo(sex.getText().toString());

                        name.setText("");
                        lastName.setText("");
                        age.setText("");
                        sex.setChecked(false);

                        index++;
                        userReference.child(currentIndex).setValue(user);
                        Toast.makeText(getApplicationContext(),"User on FireBase.", Toast.LENGTH_LONG).show();

                        editor.putInt("index", index);
                        editor.commit();
                    }

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
