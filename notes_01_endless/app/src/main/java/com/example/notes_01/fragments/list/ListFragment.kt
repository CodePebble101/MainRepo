package com.example.notes_01.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes_01.viewmodel.UserViewModel
import com.example.notes_01.R
import com.example.notes_01.databinding.FragmentListBinding


class ListFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentListBinding

    private val mUserViewModel: UserViewModel by viewModels()
    private val myAdapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myAdapter

        mUserViewModel.readAllDAta.observe(viewLifecycleOwner) { myAdapter.setData(it) }
        binding.adNoteButton1.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_ADDFragment)
        }

        setHasOptionsMenu(true)


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllNotes()
        }
        if (item.itemId == R.id.menuSortByDate){
            sortByDate()
        }
        if (item.itemId == R.id.menuSortByTitle){
            sortByTitle()
        }
        return super.onContextItemSelected(item)
    }

    private fun sortByTitle() {
        Log.i("list_fragment","Сортируем по заголовку")
//        Toast.makeText(requireContext(), "Модуль в разработке.", Toast.LENGTH_SHORT).show()
        mUserViewModel.sortByTitle
        mUserViewModel.sortByTitle.observe(viewLifecycleOwner) { myAdapter.setData(it) }

    }

    private fun sortByDate() {
        Log.i("list_fragment","Сортируем по дате")
//        Toast.makeText(requireContext(), "Модуль в разработке.", Toast.LENGTH_SHORT).show()
        mUserViewModel.sortByDate
        mUserViewModel.sortByDate.observe(viewLifecycleOwner) { myAdapter.setData(it) }
    }

    private fun deleteAllNotes() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Начать все с чистого листа?")
        builder.setMessage("Вы уверены? Все данные будут удалены безвозвратно")
        builder.setPositiveButton("Да") { _, _ ->
            mUserViewModel.deleteAllNotes()
            Toast.makeText(requireContext(), "Удаление завершено", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Нет") { _, _ ->
        }
        builder.create().show()

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"
        mUserViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {
                myAdapter.setData(it)

            }

        }
    }
}