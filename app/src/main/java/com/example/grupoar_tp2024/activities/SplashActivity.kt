package com.example.grupoar_tp2024.activities

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.example.grupoar_tp2024.R

class SplashActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private val delayMillis: Long = 700 //pongo demora antes de la animacion (0.7 segundos)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mediaPlayer = MediaPlayer.create(
            this,
            R.raw.pikachu_splash //reproduzco el "Pikaaa" al inicio
        )

        val logoImageView = findViewById<ImageView>(R.id.logoImageView)
        Handler().postDelayed({
            playAnimation(logoImageView) //Doy arranque a animacion
        }, delayMillis) //Demoro los 0.7 segundos antes de animacion
    }


    private fun playAnimation(logo: ImageView) {
        // "Pikaaaaaaaaaa"
        mediaPlayer.start()

        // Animación
        val escalaAnimacion = ScaleAnimation(
            1f, 1.2f, // Aumento la escala de 100 a 120
            1f, 1.2f, // lo mismo en eje y
            Animation.RELATIVE_TO_SELF, 0.5f, // 50% eje x
            Animation.RELATIVE_TO_SELF, 0.5f // 50% eje y
        ).apply {
            duration = 1200 // Duración de la animación
            repeatCount = 1
            repeatMode = Animation.REVERSE
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    // Termina la app y vamos al login
                    startActivity(Intent(this@SplashActivity, Login::class.java))
                    finish() // Finalizacion
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

        logo.startAnimation(escalaAnimacion)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}