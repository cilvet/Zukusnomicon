package cilveti.inigo.cbmobile2.ui.activities;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.ui.fragments.CrearConjuroFragment;
import cilveti.inigo.cbmobile2.ui.fragments.MainSearchFragment;

public class CrearConjuroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        abrirFragmento();
    }

    private void abrirFragmento(){
        // Create fragment and give it an argument specifying the article it should show
        CrearConjuroFragment newFragment = new CrearConjuroFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        // Commit the transaction
        transaction.commit();
    }
}
