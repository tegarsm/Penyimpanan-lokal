package com.example.penyimpananlokal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.penyimpananlokal.databinding.ActivityMainBinding
import com.example.penyimpananlokal.model.InternalFileRepository
import com.example.penyimpananlokal.model.Note
import com.example.penyimpananlokal.model.NoteRepository

class MainActivity : AppCompatActivity() {

    private val repo: NoteRepository by lazy { InternalFileRepository(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWrite.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    repo.addNote(
                        Note(
                            binding.editFileName.text.toString(),
                            binding.editTeksCatatan.text.toString()
                        )
                    )
                } catch (e: Exception) {
                    Toast.makeText(this, "File Write Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
        // btn write digunakan untuk menyimpan text atau kata kata yang barusaja dibuat, apabila nama file serta format file salah akan menampilkan text Please provide a File Write Failed dan Filename//

        binding.btnRead.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    val note = repo.getNote(binding.editFileName.text.toString())
                    binding.editTeksCatatan.setText(note.noteText)
                } catch (e: Exception) {
                    Toast.makeText(this, "File Read Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
            }
        }
        // btn read akan membaca file yang sudah diketik sebelumnya, apabola filename/format filenya salah akan memunculkan text File Read Failed dan Please provide a Filename//

        binding.btnDelete.setOnClickListener {
            if (binding.editFileName.text.isNotEmpty()) {
                try {
                    if (repo.deleteNote(binding.editFileName.text.toString())) {
                        Toast.makeText(this, "File Deleted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "File Could Not Be Deleted", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "File Delete Failed", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
                binding.editFileName.text.clear()
                binding.editTeksCatatan.text.clear()
            } else {
                Toast.makeText(this, "Please provide a Filename", Toast.LENGTH_LONG).show()
                // Btn delete digunakan untuk mengahapus kata kata yang sudah dimasukan, karna terdapat perintah if repo deleteNote, lalu akan muncul kata file delete//
                // else file could not be deleted//
            }
        }
    }
}