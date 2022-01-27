package com.example.netflix_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.netflix_clone.Adapter.FilmesAdapter
import com.example.netflix_clone.Model.addFilmes
import com.example.netflix_clone.databinding.ActivityDetalhesFilmeBinding
import com.squareup.picasso.Picasso

class DetalhesFilme : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesFilmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        Toobar()
        var recycler_outros_filmes = binding.recyclerOutrosFilmes
        recycler_outros_filmes.adapter = FilmesAdapter(addFilmes())
        recycler_outros_filmes.layoutManager = GridLayoutManager(applicationContext, 3)
        val capaTheWitcher = "https://firebasestorage.googleapis.com/v0/b/netflix-clone-c8686.appspot.com/o/video.jpg?alt=media&token=57367dab-70d0-4afe-b737-a11ed0d0b398"
        Picasso.get().load(capaTheWitcher).fit().into(binding.capa)

        binding.playVideo.setOnClickListener {
            val intent = Intent(this, Video::class.java)
            startActivity(intent)
        }

    }

    private fun Toobar(){
        val toolbar_detalhes = binding.toolbarDetalhes
        toolbar_detalhes.setNavigationIcon(getDrawable(R.drawable.ic_voltar))
        toolbar_detalhes.setNavigationOnClickListener {
            val intent = Intent(this, ListaFilmes::class.java)
            startActivity(intent)
            finish()
        }
    }

}