package com.example.netflix_clone

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.netflix_clone.databinding.ActivityFormLoginBinding
import com.example.netflix_clone.databinding.ActivityFromCadastroBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FromCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFromCadastroBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFromCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        Toolbar()
        cadastro()
    }

    private fun cadastro(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_error = binding.mensagemError

        binding.btCadastrar.setOnClickListener {
            if(email.isEmpty() || senha.isEmpty()){
                mensagem_error.setText("Escreva seu Email ou senha")
            }
                cadastrarUsuario()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun Toolbar(){
        val toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_netflix_official_logo))
    }

    @SuppressLint("SetTextI18n")
    private fun cadastrarUsuario(){

        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_error = binding.mensagemError

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this, "usuario cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                binding.editEmail.setText("")
                binding.editSenha.setText("")
                mensagem_error.setText("")
            }
        }.addOnFailureListener {

            var error = it

            when{
                error is FirebaseAuthWeakPasswordException -> mensagem_error.setText("Digite uma senha com no minimo 6 caracteres")
                error is FirebaseAuthUserCollisionException -> mensagem_error.setText("Essa conta ja foi cadastrada")
                error is FirebaseNetworkException -> mensagem_error.setText("Sem conexao com a Internet")
                else -> mensagem_error.setText("Error a cadastrar o usuario")
            }
        }

    }
}