package com.example.praktikum5_recyclerview.repositories;

import android.os.Environment;
import android.util.Log;

import com.example.praktikum5_recyclerview.models.Anggota;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class AnggotaRepo {
    private final File file = new File(
            Environment.getExternalStorageDirectory(),
            "anggota.txt"
    );

    public ArrayList<Anggota> getAnggota() {
        ArrayList<Anggota> listAnggota = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String temp;
            while ((temp = reader.readLine()) != null) {
                String[] split = temp.split(";");
                listAnggota.add(new Anggota(split[0], split[1]));
            }
            reader.close();
        } catch (Exception err) {
            Log.e("FILE HANDLING ERROR", err.toString());
        }
        return listAnggota;
    }

    public void addAnggota(String nama, String nim) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,
                    true));
            writer.write(String.format("%s;%s", nama, nim));
            writer.newLine();
            writer.close();
        } catch (Exception err) {
            Log.e("FILE HANDLING ERROR", err.toString());
        }
    }

    public void deleteAnggota(String nim) {
        String listAnggota = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String temp;
            while ((temp = reader.readLine()) != null) {
                String[] split = temp.split(";");
                if (split[1].equals(nim)) continue;
                listAnggota += temp + "\n";
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(
                    file,
                    false
            ));
            writer.write(listAnggota);
            writer.close();
        } catch (Exception err) {
            Log.e("FILE HANDLING ERROR", err.toString());
        }
    }
}
