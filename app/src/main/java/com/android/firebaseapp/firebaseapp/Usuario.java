package com.android.firebaseapp.firebaseapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String nome;
    private String sobrenome;
    private String sexo;
    private int idade;

    public Usuario() {
    }

    public Usuario(Parcel in){
        this.nome = in.readString();
        this.sobrenome = in.readString();
        this.sexo = in.readString();
        this.idade = in.readInt();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nome);
        parcel.writeString(this.sobrenome);
        parcel.writeString(this.sexo);
        parcel.writeInt(this.idade);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Usuario createFromParcel(Parcel in){
            return new Usuario(in);
        }
        public Usuario[] newArray(int size){
            return new Usuario[size];
        }

    };

}
