package com.android.firebaseapp.firebaseapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailUserActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userReference = databaseReference.child("usuario");
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> namesUser = new ArrayList<String>();
    private ArrayList<String> idsUser = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuario = new Usuario();
                String index;

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    index = ds.getKey().toString();
                    usuario.setNome(ds.getValue(Usuario.class).getNome());
                    usuario.setSobrenome(ds.getValue(Usuario.class).getSobrenome());
                    usuario.setSexo(ds.getValue(Usuario.class).getSexo());
                    usuario.setIdade(ds.getValue(Usuario.class).getIdade());

                    namesUser.add(usuario.getNome() + " " + usuario.getSobrenome());
                    idsUser.add(index);
                }

                listView = (ListView) findViewById(R.id.listViewId);

                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        namesUser){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                listView.setAdapter( arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
