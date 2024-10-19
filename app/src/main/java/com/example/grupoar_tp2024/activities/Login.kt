package com.example.grupoar_tp2024.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.grupoar_tp2024.bd.AppDatabase
import com.example.grupoar_tp2024.R
import com.example.grupoar_tp2024.apiRest.IPokemonApi
import com.example.grupoar_tp2024.apiRest.PokemonDTO
import com.example.grupoar_tp2024.apiRest.RetrofitClient
import org.w3c.dom.Text
import retrofit2.*
import androidx.lifecycle.lifecycleScope
import com.example.grupoar_tp2024.apiRest.ResultadoDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Login : AppCompatActivity() {

    val api = RetrofitClient.retrofit.create(IPokemonApi::class.java)

    companion object {
        const val CHANNEL_ID = "user_channel"
        const val REQUEST_CODE = 1001
    }

    private val titleNotification = "Usuario Recordado"
    private val contentNotification = "Su Usuario será recordado para la próxima vez que inicie sesión."


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //CANAL DE NOTIFICACIONES
        createNotificationChannel()
        //PERMISO DEL USUARIO PARA LAS NOTIFICACIONES
        checkNotificationPermission()

        //Toolbar
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title= resources.getString(R.string.titulo)
        //Layout
        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        val btnRegistrarme: Button = findViewById(R.id.btnRegistrarme)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etContrasenia: EditText = findViewById(R.id.etContraseña)
        val cbRecordar: CheckBox = findViewById(R.id.cbRecordar)
        //BD
        val baseDeDatos = AppDatabase.getDatabase(applicationContext)
        //SharedPreferences para recordar usuario
        val preferencias = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
        val usuarioGuardado = preferencias.getString(resources.getString(R.string.nombre_usuario), "")
        val passwordGuardado = preferencias.getString(resources.getString(R.string.password_usuario), "")

        if (usuarioGuardado != "" && passwordGuardado != "" && usuarioGuardado != null)
            startMainActivity(usuarioGuardado)

        btnRegistrarme.setOnClickListener{
            startActivity(Intent(this, RegistrarUsuario::class.java))
        }

        btnIngresar.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etContrasenia.text.toString()
            var usuarioCount = 0

            if(email.isNotEmpty() && password.isNotEmpty())
                usuarioCount = baseDeDatos.usuarioRegistradoDao().validarUsuario(email, password)

            if (email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Se deben completar todos los campos indicados", Toast.LENGTH_SHORT).show()

            if (password.isNotEmpty() && password.length<5)
                    Toast.makeText(this,"La contraseña debe ser mayor a 5 caracteres",Toast.LENGTH_SHORT).show()

            if (usuarioCount > 0) {
                if (cbRecordar.isChecked) {
                    preferencias.edit().putString(resources.getString(R.string.nombre_usuario), email).apply()
                    preferencias.edit().putString(resources.getString(R.string.password_usuario), password).apply()

                    sendNotification(titleNotification, contentNotification)
                }
                startMainActivity(email)
            } else
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }


    }

    private fun startMainActivity(email: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(resources.getString(R.string.nombre_usuario), email)
        startActivity(intent)
        finish()
    }

    //CANAL DE NOTIFICACIONES
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelId = CHANNEL_ID
            val channelName = "Recordar Usuario"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Notificaciones para recordar usuario"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    //NOTIFICACION
    private fun sendNotification(title: String, content: String){
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_catching_pokemon_24)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(this, R.color.rojoPokebola))
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))

        with(NotificationManagerCompat.from(this)){
            if (ActivityCompat.checkSelfPermission(
                    this@Login,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, notificationBuilder.build())
        }
    }

    //PARA QUE EL USUARIO PERMITA LAS NOTIFICACIONES
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE)
            }
        }
    }

}

