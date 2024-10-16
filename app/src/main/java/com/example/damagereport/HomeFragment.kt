package com.example.damagereport

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {
    private lateinit var rvReport: RecyclerView
    private lateinit var btnAddReport: FloatingActionButton
    private lateinit var adapter: Adapter
    private lateinit var myDatabase: MyDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDatabase = MyDatabase(requireContext())

        rvReport = view.findViewById(R.id.rvReport)
        btnAddReport = view.findViewById(R.id.btnAddReport)

        setupRecyclerView()
        loadReports()

        btnAddReport.setOnClickListener { newReport() }
    }

    private fun setupRecyclerView() {
        adapter = Adapter(this)
        rvReport.layoutManager = LinearLayoutManager(requireContext())
        rvReport.adapter = adapter
    }

    private fun loadReports() {
        val reports = myDatabase.getAllReports()
        Data.listReport.clear()
        Data.listReport.addAll(reports)
        adapter.notifyDataSetChanged()
    }

    fun clickItem(position: Int) {
        val intent = Intent(requireContext(), ReportModifyDelete::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    private fun newReport() {
        val intent = Intent(requireContext(), NewReport::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadReports()
    }
}