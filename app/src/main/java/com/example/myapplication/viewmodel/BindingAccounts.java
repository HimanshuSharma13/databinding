package com.example.myapplication.viewmodel;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;

import com.example.myapplication.SpinnerAccountAdapter;
import com.example.myapplication.listener.MAccountListener;
import com.example.myapplication.model.Account;

public class BindingAccounts {

    public final ObservableField<Account> obvSelectedAccount_ = new ObservableField<Account>();
    public MAccountListener listener;

    public BindingAccounts(MAccountListener listener, Account selectedAccount) {
this.listener=listener;
        obvSelectedAccount_.set(selectedAccount);
    }

    public ObservableField<Account> getObvSelectedAccount_() {
        return obvSelectedAccount_;
    }

    private static class SpinAccountOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        private Account initialSelectedAccount_;
        private InverseBindingListener inverseBindingListener_;

        public SpinAccountOnItemSelectedListener(Account initialSelectedAccount, InverseBindingListener inverseBindingListener) {
            initialSelectedAccount_ = initialSelectedAccount;
            inverseBindingListener_ = inverseBindingListener;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("Account","onItemSelected "+initialSelectedAccount_);
            if (initialSelectedAccount_ != null) {
                //Adapter is not ready yet but there is already a bound data,
                //hence we need to set a flag so we can implement a special handling inside the OnItemSelectedListener
                //for the initial selected item
                Integer positionInAdapter = getAccountPositionInAdapter((SpinnerAccountAdapter) adapterView.getAdapter(), initialSelectedAccount_);
                if (positionInAdapter != null) {
                    adapterView.setSelection(positionInAdapter); //set spinner selection as there is a match
                }
                initialSelectedAccount_ = null; //set to null as the initialization is done
            } else {
                if (inverseBindingListener_ != null) {
                    inverseBindingListener_.onChange();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    @BindingAdapter(value = {"bind:selectedAccount", "bind:selectedAccountAttrChanged"}, requireAll = false)
    public static void bindAccountSelected(final Spinner spinner, Account accountSetByViewModel,
                                           final InverseBindingListener inverseBindingListener) {

        Log.d("Account","bindAccountSelected Selected Account "+accountSetByViewModel.getBankName()+" "+accountSetByViewModel.getAccountNumber());
        Account initialSelectedAccount = null;
        if (spinner.getAdapter() == null && accountSetByViewModel != null) {
            //Adapter is not ready yet but there is already a bound data,
            //hence we need to set a flag in order to implement a special handling inside the OnItemSelectedListener
            //for the initial selected item, otherwise the first item will be selected by the framework
            initialSelectedAccount = accountSetByViewModel;
        }

        spinner.setOnItemSelectedListener(new SpinAccountOnItemSelectedListener(initialSelectedAccount, inverseBindingListener));

        //only proceed further if the newly selected planet is not equal to the already selected item in the spinner
        if (accountSetByViewModel != null && !accountSetByViewModel.equals(spinner.getSelectedItem())) {
            //find the item in the adapter
            Integer positionInAdapter = getAccountPositionInAdapter((SpinnerAccountAdapter) spinner.getAdapter(), accountSetByViewModel);
            if (positionInAdapter != null) {
                spinner.setSelection(positionInAdapter); //set spinner selection as there is a match
            }
        }
    }

    @InverseBindingAdapter(attribute = "bind:selectedAccount", event = "bind:selectedAccountAttrChanged")
    public static Account captureSelectedAccount(Spinner spinner) {
        Log.d("Account","captureSelectedAccount Selected Account "+((Account)spinner.getSelectedItem()).getAccountNumber());
        return (Account) spinner.getSelectedItem();
    }

    public void onEditTextChanged(CharSequence s, int start, int before, int count) {
        if (listener != null) {
            listener.onEditTextChanged(s.toString());
        }
    }

    @BindingConversion
    public static String convertAccountToString(Account account) {
        Log.d("Account","convertAccountToString  Account "+account.getAccountNumber());
        return account != null? account.getAccountNumber() : null;
    }

    private static Integer getAccountPositionInAdapter(SpinnerAccountAdapter adapter, Account account) {
        if (adapter != null && account != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                Account adapterAccount = ((Account)adapter.getItem(i));
                if (adapterAccount != null && adapterAccount.getAccountNumber() != null && adapterAccount.getAccountNumber().equals(account.getAccountNumber())) {
                    return i;
                }
            }
        }
        return null;
    }

}
