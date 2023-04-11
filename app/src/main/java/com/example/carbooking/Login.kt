package com.example.carbooking

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val CHANNEL_ID = "i.apps.notifications"
    private val description = "Test notification"

    private lateinit var googleSignInClient: GoogleSignInClient


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    // [START on_start_check_user]
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = Color.WHITE
        }


        // [START config_signin]
        // Configure Google Sign In
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        // [END config_signin]

        // Initialize Firebase Auth
        auth = Firebase.auth

        val login_button: Button = findViewById(R.id.login_button)
        val google_login_button: Button = findViewById(R.id.google_sign_in)
        val register_button: TextView = findViewById(R.id.register_button)
        val email_login: EditText = findViewById(R.id.email_login)
        val password_login: EditText = findViewById(R.id.password_login)
        val invalid_password: TextView = findViewById(R.id.invalid_password)


        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Create an explicit intent for an Activity in your app
        val intent = Intent(this, Login::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        // pendingIntent is an intent for future use i.e after
        // the notification is clicked, this intent will come into action
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)


        register_button.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }

        google_login_button.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            // Get the current user
            val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

            // Get the ID token
            user?.getIdToken(true)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idToken: String? = task.result?.token
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    startActivity(Intent(this, MainActivity::class.java))
                    displayNotification(pendingIntent)
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "Google sign in failed", task.exception)
                    updateUI(null)
                }
            }
        }

        login_button.setOnClickListener {
            auth.signInWithEmailAndPassword(email_login.text.toString(), password_login.text.toString())
                .addOnCompleteListener (this) {
                    if(it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        displayNotification(pendingIntent)
                    } else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }

        }

    }

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    displayNotification(pendingIntent =
                    PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_IMMUTABLE))
                    val user = auth.currentUser
                    Log.d(TAG, "signInWithCredential:success: ${user?.displayName}")
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "Google sign in failed", task.exception)
                    updateUI(null)
                }
            }
    }
    // [END auth_with_google]

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun displayNotification(pendingIntent:PendingIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(CHANNEL_ID, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, CHANNEL_ID)
                .setContentText("Signed in successfully")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground))
                .setContentIntent(pendingIntent)
                Toast.makeText(this, "Signed in successfully", Toast.LENGTH_LONG).show()
        } else {

            builder = Notification.Builder(this)
                .setContentText("contentView")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

    private fun updateUI(user: FirebaseUser?) {

    }
}