package com.example.healthylife6.usuario.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.healthylife6.R
import com.example.healthylife6.adapter.ExercicioAdapter
import com.example.healthylife6.adapter.ListaCafeAdapter
import com.example.healthylife6.adapter.ListaExercicioAdapter
import com.example.healthylife6.model.ExercicioModel
import com.example.healthylife6.model.ReceitaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_exercicios.view.*
import kotlinx.android.synthetic.main.fragment_lista_cafe.view.*
import kotlinx.android.synthetic.main.fragment_lista_exercicio.view.*

/**
 * A simple [Fragment] subclass.
 */
class ExerciciosFragment : Fragment() {

    private var listExer = arrayListOf<com.example.healthylife6.model.ExercicioModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_exercicios, container, false)

        preencherRecyclerView(view)

        return view
    }

    private fun preencherRecyclerView(view: View) {
        val exer = FirebaseDatabase.getInstance().getReference("exercicios")

        val adapter = ExercicioAdapter(activity!!, listExer)
        var recyclerView = view.recyclerViewListaExerUser
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        exer.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listExer.clear()

                    for (rec in dataSnapshot.children) {
                        val r = rec.getValue(ExercicioModel::class.java)
                        listExer.add(r!!)
                    }

                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError?) {
                }
            }
        )
    }
}