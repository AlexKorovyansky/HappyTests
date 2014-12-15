package ru.happydev.androidtesting;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.name) EditText nameEdit;
    @InjectView(R.id.surname) EditText surnameEdit;

    private BackendService backendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backendService = ServiceProvider.getInstance().getBackendService();
        ButterKnife.inject(this);

    }

    @SuppressWarnings("UnusedDeclaration") // Used by butter knife
    @OnClick(R.id.register)
    /*injected*/ void clickToRegister() {
        final ProgressDialog dialog = ProgressDialog.show(this, "Operation in progress", "Please wait...", true);
        backendService.regisiter(nameEdit.getText().toString(), surnameEdit.getText().toString(), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                dialog.cancel();
                startActivity(new Intent(MainActivity.this, SuccessActivity.class));
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.cancel();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Unexpected Error")
                        .setMessage("Please try again later.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

}
