package com.example.praktikum5_recyclerview.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum5_recyclerview.databinding.CardBinding;
import com.example.praktikum5_recyclerview.models.Anggota;
import com.example.praktikum5_recyclerview.repositories.AnggotaRepo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private final Activity context;
    private final ArrayList<Anggota> listAnggota;
    private final AnggotaRepo anggotaRepo;

    public ListAdapter(
            Activity context,
            AnggotaRepo anggotaRepo
    ) {
        this.context = context;
        this.anggotaRepo = anggotaRepo;
        this.listAnggota = anggotaRepo.getAnggota();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private final CardBinding cardBinding;
        private final Activity context;
        private final AnggotaRepo anggotaRepo;

        public ListViewHolder(
                @NonNull CardBinding cardBinding,
                Activity context,
                AnggotaRepo anggotaRepo
        ) {
            super(cardBinding.getRoot());
            this.cardBinding = cardBinding;
            this.context = context;
            this.anggotaRepo = anggotaRepo;
        }

        public void bindView(Anggota anggota) {
            cardBinding.namaTextView.setText(anggota.getNama());
            cardBinding.nimTextView.setText(anggota.getNim());
            cardBinding.deleteButton.setOnClickListener(view -> deleteAlert());
        }

        private void deleteAlert() {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Hapus");
            alert.setMessage("Apakah anda yakin ingin menghapus?");
            alert.setPositiveButton("Ya", (dialog, id) -> {
                anggotaRepo.deleteAnggota(
                        cardBinding.nimTextView.getText().toString()
                );
                Toast.makeText(
                        context,
                        "Berhasil menghapus anggota!",
                        Toast.LENGTH_LONG
                ).show();
                context.finish();
                Intent intent = context.getIntent();
                try {
                    // an OutputStream file
                    // "namesListData" is
                    // created
                    FileOutputStream fos
                            = new FileOutputStream("namesListData");

                    // an ObjectOutputStream object is
                    // created on the FileOutputStream
                    // object
                    ObjectOutputStream oos
                            = new ObjectOutputStream(fos);

                    // calling the writeObject()
                    // method of the
                    // ObjectOutputStream on the
                    // OutputStream file "namesList"
                    oos.writeObject(new ArrayList<>());

                    // close the ObjectOutputStream
                    oos.close();

                    // close the OutputStream file
                    fos.close();

                    System.out.println("namesList serialized");
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

                context.startActivity(context.getIntent());
            });
            alert.setNegativeButton("Tidak", (dialog, id) -> dialog.dismiss());
            alert.show();
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CardBinding cardBinding = CardBinding.inflate(
                layoutInflater,
                parent,
                false
        );

        return new ListViewHolder(cardBinding, context, anggotaRepo);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ListViewHolder holder,
            int position
    ) {
        holder.bindView(listAnggota.get(position));
    }

    @Override
    public int getItemCount() {
        return listAnggota.size();
    }
}
