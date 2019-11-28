package com.example.healthylife6.usuario.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.healthylife6.R
import com.example.healthylife6.classe.idRecSelecionado
import com.example.healthylife6.model.ReceitaModel
import com.example.healthylife6.usuario.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalhes_cafe.*
import kotlinx.android.synthetic.main.fragment_detalhes_jantar.*

/**
 * A simple [Fragment] subclass.
 */
class DetalhesJantarFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalhes_jantar, container, false)
        infoReceita()

        val btnVoltarJan = view.findViewById<View>(R.id.voltar_jan) as Button
        btnVoltarJan.setOnClickListener {
            val fragment = ReceitasFragment()
            val transaction = (context as HomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }

        return view
    }

    private fun infoReceita() {

        var ref = FirebaseDatabase.getInstance().getReference("jantar").child(
            idRecSelecionado
        )
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rec = dataSnapshot.getValue(ReceitaModel::class.java)

                tit_jan.text = rec?.titulo
                ingred_jan.text = rec?.ingrediente
                desc_jan.text = rec?.desc
                Picasso.get().load(rec?.img).into(jan_det)
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

}
