package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.listener.MAccountListener;
import com.example.myapplication.model.Account;
import com.example.myapplication.viewmodel.BindingAccounts;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MAccountListener {

    private static final String BUNDLE_SELECTED_ACCOUNT = "binding_selected_account";
    ActivityMainBinding binding;
    private ArrayList<Account> account_list = new ArrayList<Account>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding == null) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
            Account account = new Account("Select Account","");
            account_list.add(account);
            binding.setSpinadapteraccount(new SpinnerAccountAdapter(this, account_list));
            binding.setBindingaccounts(new BindingAccounts(this, account_list.get(0)));

        }

        setObserver();
        loadAccounts();
    }

    private void loadAccounts() {
        Account account=new Account("123","IDFC");
        account_list.add(account);
         account=new Account("456","IDBI");
        account_list.add(account);
         account=new Account("789","PNB");
        account_list.add(account);
         account=new Account("098","AXIS");
        account_list.add(account);
         account=new Account("345","HDFC");
        account_list.add(account);
    }

    private void setObserver() {

    }

    @Override
    public void onEditTextChanged(String accountName) {
        Toast.makeText(MainActivity.this,"Account "+accountName,Toast.LENGTH_LONG).show();
        if (accountName != null && account_list != null) {
            Account targetPlanet = account_list.get(0); //set the blank item as the target first until the text input by users has a match
            for (Account account : account_list) {
                if (account != null) {
                    if (accountName.equalsIgnoreCase(account.getAccountNumber())) {
                        targetPlanet = account;
                        break;
                    }
                }
            }
            binding.getBindingaccounts().obvSelectedAccount_.set(targetPlanet);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_SELECTED_ACCOUNT, binding.getBindingaccounts().obvSelectedAccount_.get());
    }
}
