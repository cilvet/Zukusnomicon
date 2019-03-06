package cilveti.inigo.cbmobile2.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.ui.fragments.ConjuroFragment;
import cilveti.inigo.cbmobile2.ui.fragments.CrearConjuroFragment;

public class ConjuroActivity extends AppCompatActivity {

    String id;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if(getIntent().getExtras()!=null){
            try{
                id = getIntent().getExtras().getString("id");

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        abrirFragmento();
    }

    private void abrirFragmento(){
        // Create fragment and give it an argument specifying the article it should show
        ConjuroFragment newFragment = ConjuroFragment.newInstance(id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        // Commit the transaction
        transaction.commit();
    }
}
