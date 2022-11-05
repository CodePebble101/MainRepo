package com.example.notes_01.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_01.R
import com.example.notes_01.databinding.FragmentUpdateBinding
import com.example.notes_01.model.User
import com.example.notes_01.viewmodel.UserViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateNoteText.setText(args.currentUser.note_text)
        binding.updateNoteTitle.setText(args.currentUser.note_title)


        binding.updateButton.setOnClickListener {
            updateData()
        }



        setHasOptionsMenu(true)

        return binding.root
        // return inflater.inflate(R.layout.fragment_update, container, false)
    }

    private fun updateData() {
        val note_title = binding.updateNoteTitle.text.toString().trim()
        val note_text = binding.updateNoteText.text.toString().trim()

        val current = LocalDateTime.now()
        val formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formatted1 = current.format(formatter1)

        val formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss")
        val formatted2 = current.format(formatter2)

        val note_update_day = formatted1
        val note_update_time = formatted2

        if (inputCheck(note_title, note_text)) {
            val updatedUser = User(
                args.currentUser.id,
                note_title,
                note_text,
                args.currentUser.note_date,
                args.currentUser.note_time,
                note_update_day,
                note_update_time
            )
            mUserViewModel.updateNote(updatedUser)
            Toast.makeText(requireContext(), "Заметка отредактирована.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Заполни все поля.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck(note_title: String, note_text: String): Boolean {
        return !(TextUtils.isEmpty(note_title) || TextUtils.isEmpty(note_text))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNote()
        }
        if (item.itemId == R.id.menu_info){
            infoAboutNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun infoAboutNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Свойства заметки")
        builder.setMessage("Дата и время создания:\n${args.currentUser.note_date}  ${args.currentUser.note_time}\n \nДата и время последнего изменения:\n${args.currentUser.note_update_day}  ${args.currentUser.note_update_time}")
        builder.create().show()
    }


    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Удалить \"${args.currentUser.note_title}\"?")
        builder.setMessage("Вы уверены, что хотите удалить \"${args.currentUser.note_title}\"?")
        builder.setPositiveButton("Да") { _, _ ->
            mUserViewModel.deleteNote(args.currentUser)
            Toast.makeText(requireContext(), "Заметка удалена", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Нет") { _, _ ->
        }
        builder.create().show()


    }

}