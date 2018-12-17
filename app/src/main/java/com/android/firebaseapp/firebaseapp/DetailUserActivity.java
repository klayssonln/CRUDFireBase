package com.android.firebaseapp.firebaseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        listView = (ListView) findViewById(R.id.listViewId);
        listView.setLongClickable(true);

        try {
            //get data from FireBase
            getUserData();

            //click for detail
            selectItem();

            //long click for delete
            deleteItem();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void selectItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                intent.putExtra("usuario",usuarios.get(i));
                intent.putExtra("index", idsUser.get(i));
                startActivity(intent);
            }
        });
    }

    public void deleteItem(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                int position = i;
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailUserActivity.this);
                alert.setTitle("Delete?");
                alert.setMessage("Are you Sure you want to delete " + usuarios.get(i).getNome() + " " + usuarios.get(i).getSobrenome());
                alert.setNegativeButton("Cancel",null);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userReference.child(idsUser.get(position)).removeValue();
                        arrayAdapter.remove(arrayAdapter.getItem(position));
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                alert.create();
                alert.show();

                return true;
            }
        });
    }

    public void getUserData(){
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                namesUser.clear();
                idsUser.clear();
                usuarios.clear();

                String index;

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Usuario usuario = new Usuario();

                    index = ds.getKey().toString();
                    usuario.setNome(ds.getValue(Usuario.class).getNome());
                    usuario.setSobrenome(ds.getValue(Usuario.class).getSobrenome());
                    usuario.setSexo(ds.getValue(Usuario.class).getSexo());
                    usuario.setIdade(ds.getValue(Usuario.class).getIdade());

                    namesUser.add(usuario.getNome() + " " + usuario.getSobrenome());
                    idsUser.add(index);
                    usuarios.add(usuario);
                }

//                for (Usuario user : usuarios) {
//                    Log.v("USER: ", user.getNome() + " " + user.getSobrenome() + " " + user.getSexo() + " " + user.getIdade());
//                }

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
