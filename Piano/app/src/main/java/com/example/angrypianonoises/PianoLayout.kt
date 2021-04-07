package com.example.angrypianonoises

import android.os.Bundle
import android.os.SystemClock.uptimeMillis
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.angrypianonoises.data.Note
import com.example.angrypianonoises.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream

class PianoLayout : Fragment() {

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2")
    private val halfTones = listOf("C#", "D#", "F#", "G#", "A#", "C#2", "D#2", "F#2", "F#2")
    private val score:MutableList<Note> = mutableListOf<Note>() // Score == Noteark?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        var scoreStartTime = uptimeMillis()

        fullTones.forEach { orgNoteValue ->
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(orgNoteValue)
            var startPlay:Long = 0

            fullTonePianoKey.onKeyDown = { note ->
                startPlay = uptimeMillis()
                println("Piano key down $note")
            }

            fullTonePianoKey.onKeyUp = { note ->
                val endPlay = uptimeMillis()
                val saveNote = Note(note, scoreStartTime, startPlay, endPlay)
                score.add(saveNote)
                println("Piano key up $saveNote")
            }

            fragmentTransaction.add(view.fullToneKeysLayout.id, fullTonePianoKey, "note_$orgNoteValue")

        }

        halfTones.forEach { orgNoteValue ->
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(orgNoteValue)
            var startPlay:Long = 0

            halfTonePianoKey.onKeyDown = { note ->
                startPlay = uptimeMillis()
                println("Piano key down $note")
            }

            halfTonePianoKey.onKeyUp = { note ->
                val endPlay = uptimeMillis()
                val saveNote = Note(note, scoreStartTime, startPlay, endPlay)
                score.add(saveNote)
                println("Piano key up $saveNote")
            }

            fragmentTransaction.add(view.halfToneKeysLayout.id, halfTonePianoKey, "note_$orgNoteValue")
        }

        fragmentTransaction.commit()

        view.saveScoreBt.setOnClickListener {
            var fileName = view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)

            if (fileSaveConditions(fileName, path)) {
                // println(fileName)
                fileName = "$fileName.musikk"

                FileOutputStream(File(path, fileName), true).bufferedWriter().use { writer ->
                    // bufferedWriter lever her, men stenger ned utenfor
                    score.forEach {
                        writer.write("${it.toString()}\n")
                    }
                }
                Toast.makeText(this.activity, "Music score saved", Toast.LENGTH_SHORT).show()
                scoreStartTime = uptimeMillis()
                score.clear()

            }
        }

        return view
    }


    fun doesFileExist(filename: String, path: File?): Boolean {
        val extendedFileName = "$filename.musikk"
        val fileAndPath = File(path, extendedFileName)

        return fileAndPath.exists()
    }


    fun fileSaveConditions(filename:String, path: File?): Boolean{

        when {
            path == null -> {
                Toast.makeText(this.activity, "Missing directory path", Toast.LENGTH_SHORT).show()
                return false
            }
            filename.isEmpty() -> {
                Toast.makeText(this.activity, "File name is missing", Toast.LENGTH_SHORT).show()
                return false
            }
            doesFileExist(filename,path) -> {
                Toast.makeText(this.activity, "File already exists", Toast.LENGTH_SHORT).show()
                return false
            }
            score.count() <= 0 -> {
                Toast.makeText(this.activity, "No notes have been played", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }




}


