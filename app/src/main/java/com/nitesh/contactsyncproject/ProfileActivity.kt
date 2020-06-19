package com.nitesh.contactsyncproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import kotlinx.android.synthetic.main.activity_login.*

class ProfileActivity : AppCompatActivity() {
    class GoogleConnection : GoogleApiClient.OnConnectionFailedListener {
        override fun onConnectionFailed(p0: ConnectionResult) {

        }
    }

    lateinit var img_profile: ImageView
    lateinit var txtName: TextView
    lateinit var txtEmail: TextView
    lateinit var txtId: TextView
    lateinit var btnLogout: Button
    lateinit var googleApiClient: GoogleApiClient
    lateinit var gso: GoogleSignInOptions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        img_profile = findViewById(R.id.img_profile)
        txtName = findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtId = findViewById(R.id.txtId)
        btnLogout = findViewById(R.id.btnLogout)

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        // googleApiClient=GoogleApiClient.Builder(this).enableAutoManage(this,this)
        //   .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()

        btnLogout.setOnClickListener {
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(ResultCallback<Status> {
                fun onActivityResult(status: Status) {
                    if (status.isSuccess) {
                        val intent = Intent(this@ProfileActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@ProfileActivity, "Logout failed", Toast.LENGTH_SHORT)
                            .show()

                    }

                }

            })
        }
        fun handleSignInResult(GoogleSignInResult result) {
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount ();
                txtName.text(account.getDisplayName());
                userEmail.setText(account.getEmail());
                userId.setText(account.getId());
                try {
                    Glide.with(this).load(account.getPhotoUrl()).into(profileImage);
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "image not found", Toast.LENGTH_LONG)
                        .show();
                }

            }


        }
    }
}


