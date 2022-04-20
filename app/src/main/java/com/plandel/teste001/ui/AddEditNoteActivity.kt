package com.plandel.teste001.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.plandel.teste001.MainActivity
import com.plandel.teste001.NoteViewModel
import com.plandel.teste001.R
import com.plandel.teste001.model.Note
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdit: EditText
    lateinit var noteEdit: EditText
    lateinit var saveBtn: Button

    lateinit var viewModel: NoteViewModel
    var noteID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        noteTitleEdit = findViewById(R.id.idEdtNoteName)
        noteEdit = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Note")
            noteTitleEdit.setText(noteTitle)
            noteEdit.setText(noteDescription)
        }else {
            saveBtn.setText("Save Note")
        }

        saveBtn.setOnClickListener {
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteEdit.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDataAndTime: String = sdf.format(Date())
                    val updatedNote = Note(noteID.toLong(),noteTitle,noteDescription,currentDataAndTime)
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
                }
            }else {
                if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    viewModel.addNote(Note(0,noteTitle, noteDescription, currentDateAndTime))
                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }
    }
}