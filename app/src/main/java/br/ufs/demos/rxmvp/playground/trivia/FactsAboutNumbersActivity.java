package br.ufs.demos.rxmvp.playground.trivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.networking.RestWebService;
import dagger.android.AndroidInjection;

public class FactsAboutNumbersActivity extends AppCompatActivity {

    @Inject RestWebService webService;

    @Override protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

}
