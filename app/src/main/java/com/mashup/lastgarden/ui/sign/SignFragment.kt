package com.mashup.lastgarden.ui.sign

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mashup.base.autoCleared
import com.mashup.base.extensions.underLine
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.FragmentSignBinding
import com.mashup.lastgarden.ui.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseViewModelFragment() {

    private var binding by autoCleared<FragmentSignBinding>()
    private val viewModel: SignViewModel by viewModels()
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }

    private val googleAuthLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken)
        } catch (exception: ApiException) {
            Log.e(SignInFragment::class.java.simpleName, exception.stackTraceToString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onSetupViews(view: View) {
        super.onSetupViews(view)

        binding.unUsedSign.underLine()
        binding.loginGoogleButton.setOnClickListener {
            requestGoogleLogin()
        }
    }

    private fun requestGoogleLogin() {
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    override fun onBindViewModelsOnCreate() {
        viewModel.accessToken.observe(this) {
            //TODO: 성별 나이 이름 모두 입력하면 TOKEN 저장해야 할듯?
            moveSignInformationFragment()
        }
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(), googleSignInOption)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) {
                idToken?.let {
                    viewModel.requestSignIn(idToken, AuthType.GOOGLE)
                }
            }
    }

    private fun moveSignInformationFragment() {
        findNavController().navigate(
            R.id.actionSignInFragmentToSignInInputNameFragment
        )
    }
}