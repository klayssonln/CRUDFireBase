package com.android.firebaseapp.firebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference().child("usuario");

    private Button button;
    private EditText name, lastName, age;
    private RadioGroup group;
    private RadioButton sexButton;
    private Usuario usuario = new Usuario();
    private String index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        button = (Button) findViewById(R.id.buttonUpId);
        name = (EditText) findViewById(R.id.nameUpId);
        lastName = (EditText) findViewById(R.id.lastNameUpId);
        age = (EditText) findViewById(R.id.ageUpId);
        group = (RadioGroup) findViewById(R.id.groupUpId);

        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null){
                usuario = (Usuario) bundle.getParcelable("usuario");
                index = bundle.getString("index");

                name.setText(usuario.getNome());
                lastName.setText(usuario.getSobrenome());
                age.setText(Integer.toString(usuario.getIdade()));
                if(usuario.getSexo().equals("M")){
                    group.check(R.id.buttonUpMascId);
                } else {
                    group.check(R.id.buttonUpFemId);
                }
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (name.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inform the Name", Toast.LENGTH_SHORT).show();
                    } else if(lastName.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inform the Lastname", Toast.LENGTH_SHORT).show();
                    } else if(group.getCheckedRadioButtonId() == -1){
                        Toast.makeText(getApplicationContext(), "Inform the Sex", Toast.LENGTH_SHORT).show();
                    } else if(age.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Inform the Age", Toast.LENGTH_SHORT).show();
                    } else {
                        int option = group.getCheckedRadioButtonId();
                        sexButton = (RadioButton) findViewById(option);

                        usuario.setNome(name.getText().toString());
                        usuario.setSobrenome(lastName.getText().toString());
                        usuario.setIdade(Integer.parseInt(age.getText().toString()));
                        usuario.setSexo(sexButton.getText().toString());

                        userReference.child(index).setValue(usuario);
                        Toast.makeText(getApplicationContext(),"User Updated on FireBase.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
