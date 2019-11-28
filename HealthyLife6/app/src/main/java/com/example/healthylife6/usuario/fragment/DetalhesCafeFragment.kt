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
import com.example.healthylife6.model.UsuarioModel
import com.example.healthylife6.usuario.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalhes_cafe.*
import kotlinx.android.synthetic.main.fragment_editar_perfil_user.*
import kotlinx.android.synthetic.main.receitas_home_cafe.*

/**
 * A simple [Fragment] subclass.
 */
class DetalhesCafeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalhes_cafe, container, false)
        infoReceita()

        val btnVoltarCafe = view.findViewById<View>(R.id.voltar_cafe) as Button
        btnVoltarCafe.setOnClickListener {
            val fragment = ReceitasFragment()
            val transaction = (context as HomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment).addToBackStack(null).commit()
        }
        return view
    }

    private fun infoReceita() {

        var ref = FirebaseDatabase.getInstance().getReference("cafe da manha").child(idRecSelecionado)
        val info = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //erro
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rec = dataSnapshot.getValue(ReceitaModel::class.java)

                tit_cafe.text = rec?.titulo
                ingred_cafe.text = rec?.ingrediente
                desc_cafe.text = rec?.desc
                Picasso.get().load(rec?.img).into(cafe_det)
            }
        }
        ref.addListenerForSingleValueEvent(info)
    }

}
