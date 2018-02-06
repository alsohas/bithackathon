package com.example.mustafdahir.bithackathon.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mustafdahir.bithackathon.MainActivity;
import com.example.mustafdahir.bithackathon.R;
import com.example.mustafdahir.bithackathon.interfaces.OnStartInteractionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStartInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private OnStartInteractionListener mListener;
    private String mUsername;
    private String mPassword;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        Button b = (Button) v.findViewById(R.id.loginButton);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartInteractionListener) {
            mListener = (OnStartInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFirstFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        View v = this.getView();
        mUsername = ((EditText) v.findViewById(R.id.loginEmail)).getText().toString();
        mPassword = ((EditText) v.findViewById(R.id.loginPassword)).getText().toString();

        try {
            mAuth.signInWithEmailAndPassword(mUsername, mPassword)
                    .addOnCompleteListener(((Activity) getContext()), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("Username", mUsername);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Invalid parameters.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
