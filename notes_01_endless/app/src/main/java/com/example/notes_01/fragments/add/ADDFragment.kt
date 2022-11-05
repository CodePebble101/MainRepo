package com.example.notes_01.fragments.add

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notes_01.viewmodel.UserViewModel
import com.example.notes_01.R
import com.example.notes_01.model.User
import com.example.notes_01.databinding.FragmentADDBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar



class ADDFragment : Fragment() {
    private lateinit var binding: FragmentADDBinding

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentADDBinding.inflate(inflater, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        binding.saveButton.setOnClickListener {
            insertDataToDB()
        }
        return binding.root
    }

    private fun insertDataToDB() {
        val note_title = binding.noteTitle.text.toString().trim()
        val note_text = binding.noteText.text.toString().trim()

        val current = LocalDateTime.now()

        val formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted1 = current.format(formatter1)

        val formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss")
        val formatted2 = current.format(formatter2)

        val note_date = formatted1
        val note_time = formatted2

        if (inputCheck(note_title, note_text)) {
            //Если проверка на пустоту пройдена, то заносим данные в БД

            val note = User(0, note_title, note_text, note_date, note_time, "Пусто","Пусто")
            mUserViewModel.add_note(note)
            // Всплывашка для проверки
            Toast.makeText(requireContext(), "Заметка добавлена.", Toast.LENGTH_LONG).show()
            //Перебрасываем на главный экран
            findNavController().navigate(R.id.action_ADDFragment_to_listFragment)

        } else {
            Toast.makeText(requireContext(), "Заполни все поля.", Toast.LENGTH_LONG).show()
        }

    }

    private fun inputCheck(note_title: String, note_text: String): Boolean {
        return !(TextUtils.isEmpty(note_title) || TextUtils.isEmpty(note_text))
    }



}