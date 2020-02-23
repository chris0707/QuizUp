package albarillo.barebarz.quizup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import albarillo.barebarz.quizup.Retrofit.IMyService;
import albarillo.barebarz.quizup.Retrofit.RetrofitClient;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRegisterActivity extends AppCompatActivity {

    TextView text_createAccount;
    MaterialEditText edit_login_email, edit_login_password;
    Button button_login;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //Init View
        edit_login_email = findViewById(R.id.edit_email);
        edit_login_password = findViewById(R.id.edit_password);

        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**loginUser(edit_login_email.getText().toString(),
                        edit_login_password.getText().toString()); */
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });

        text_createAccount = findViewById(R.id.tv_createAccount);

        text_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final View register_layout = LayoutInflater.from(LoginRegisterActivity.this)
                        .inflate(R.layout.activity_register, null);

                new MaterialStyledDialog.Builder(LoginRegisterActivity.this)
                        .setIcon(R.drawable.ic_email_icon)
                        .setTitle("REGISTRATION")
                        .setDescription("Please fill all fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText editText_register_email = register_layout.findViewById(R.id.edit_email);
                                MaterialEditText editText_register_name = register_layout.findViewById(R.id.edit_name);
                                MaterialEditText editText_register_password = register_layout.findViewById(R.id.edit_password);

                                if(TextUtils.isEmpty(editText_register_email.getText().toString())){
                                    Toast.makeText(LoginRegisterActivity.this,"Email cannot be empty or null", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(editText_register_name.getText().toString())){
                                    Toast.makeText(LoginRegisterActivity.this,"Name cannot be empty or null", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(TextUtils.isEmpty(editText_register_password.getText().toString())){
                                    Toast.makeText(LoginRegisterActivity.this,"Password cannot be empty or null", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                registerUser(editText_register_email.getText().toString(),
                                        editText_register_name.getText().toString(),
                                        editText_register_password.getText().toString());


                            }
                        }).show();

            }
        });

    }

    private void registerUser(String email, String name, String password) {

        compositeDisposable.add(iMyService.registerUser(email,name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(LoginRegisterActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LoginRegisterActivity.this, "No Internet connection: " + throwable, Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    private void loginUser(String email, String password) {

        final String connect = "";

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
            compositeDisposable.add(iMyService.loginUser(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Toast.makeText(LoginRegisterActivity.this, "1: " + s, Toast.LENGTH_SHORT).show();
                            String trimmedS = s.replace("\"","");
                            if(trimmedS.equals("Success"))
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(LoginRegisterActivity.this, "No connection: " + throwable, Toast.LENGTH_SHORT).show();
                        }
                    }));
    }
}
