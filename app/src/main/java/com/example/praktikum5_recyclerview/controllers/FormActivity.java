package com.example.praktikum5_recyclerview.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.praktikum5_recyclerview.databinding.FormBinding;
import com.example.praktikum5_recyclerview.repositories.AnggotaRepo;

public class FormActivity extends AppCompatActivity {

    private FormBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnggotaRepo anggotaRepo = new AnggotaRepo();

        binding.submitButton.setOnClickListener(view -> {
            String nama = binding.namaEditText.getText().toString();
            String nim = binding.nimEditText.getText().toString();

            if (nama.equals("") || nim.equals("")) {
                Toast.makeText(this, "Harap isi semua data",
                        Toast.LENGTH_LONG).show();
                return;
            }
            anggotaRepo.addAnggota(nama, nim);
            Toast.makeText(this, "Berhasil Menambah Anggota",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, ListActivity.class));
        });
    }
}