package com.example.netflix_clone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.netflix_clone.databinding.ActivityFormLoginBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        verificarUsuarioLogado()
        changeScreen()
        login()
    }
    private fun changeScreen(){
        binding.txtTelaCadastro.setOnClickListener{
            val intent = Intent(this, FromCadastro::class.java)
            startActivity(intent)
        }
    }
    private fun login(){
        binding.let{
            it.btEntrar.setOnClickListener {
                val email = binding.editEmail.text.toString()
                val senha = binding.editSenha.text.toString()
                val mensagem_error = binding.mensagemError

                if(email.isEmpty() || senha.isEmpty()){
                    mensagem_error.setText("Preencha todos os campos")
                }else{
                    autenticarUsuario()
                }
            }
        }
    }
    private fun autenticarUsuario(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_error = binding.mensagemError

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this,"Login efetuado com sucesso!",Toast.LENGTH_SHORT).show()
                goToHome()
            }
        }.addOnFailureListener {
            var error = it
            when{
                error is FirebaseAuthInvalidCredentialsException -> mensagem_error.setText("Email ou senha estao incorretos")
                error is FirebaseNetworkException -> mensagem_error.setText("Sem conexao com a internet")
                else -> mensagem_error.text = "Error ao logar"
            }
        }
    }

    private fun verificarUsuarioLogado(){
        var usuarioLogado = FirebaseAuth.getInstance().currentUser

        if(usuarioLogado!=null){
            goToHome()
        }
    }

    private fun goToHome(){
        val intent = Intent(this, ListaFilmes::class.java)
        startActivity(intent)
        finish()
    }
}