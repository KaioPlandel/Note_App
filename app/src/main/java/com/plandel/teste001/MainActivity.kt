package com.plandel.teste001

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.plandel.teste001.adapter.NoteRVAdapter
import com.plandel.teste001.model.Note
import com.plandel.teste001.ui.AddEditNoteActivity

class MainActivity : AppCompatActivity(), NoteRVAdapter.NoteClickInterface, NoteRVAdapter.NoteClickDeleteInterface {
    lateinit var viewModel: NoteViewModel
    lateinit var notesRv: RecyclerView
    lateinit var addFab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRv = findViewById(R.id.notesRV)
        addFab = findViewById(R.id.idFAB)


        val noteRvAdapter = NoteRVAdapter(this,this,this)
        notesRv.layoutManager = LinearLayoutManager(this)
        notesRv.adapter = noteRvAdapter

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)


        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                noteRvAdapter.updateList(it)
            }
        })

        addFab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription",note.noteDescription)
        intent.putExtra("noteId",note.id)
        startActivity(intent)
        this.finish()
    }
}