package applab.veiligthuis.activity.meldingen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import applab.veiligthuis.R;
import applab.veiligthuis.activity.home.MainActivity;
import applab.veiligthuis.viewmodel.MeldingViewModel;

public class MeldingMakenFragment extends Fragment {

    private MeldingViewModel meldingViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_melding_maken, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        meldingViewModel = new ViewModelProvider(requireActivity()).get(MeldingViewModel.class);
        initMeldingObservers();

        initPlaatsnaamSpinner();
        initOpslaanButton();
        initSluitAppButton();
    }

    public void initPlaatsnaamSpinner(){
        Spinner plaatsnaamSpinner = getView().findViewById(R.id.plaatsnaam_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.plaatsnamen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plaatsnaamSpinner.setAdapter(adapter);
    }


    public void initOpslaanButton(){

        getView().findViewById(R.id.opslaan_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plaatsnaam = ((Spinner) getView().findViewById(R.id.plaatsnaam_spinner)).getSelectedItem().toString();

                final EditText meldingEditText = getView().findViewById(R.id.meldingmaken_editTextTextMultiLine);
                String beschrijving = meldingEditText.getText().toString().trim();

                if (plaatsnaam.isEmpty() || beschrijving.isEmpty()){
                    Toast.makeText(getActivity(), "Zorg dat de beschrijving en de plaatsnaam ingevuld zijn.", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MeldingMakenFragment.this.getContext());
                    builder.setMessage("Wil je je melding opsturen?")
                            .setCancelable(true)
                            .setPositiveButton("Bevestig", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    slaMeldingOp(plaatsnaam, beschrijving);
                                    returnToMain();
                                }
                            })
                            .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }


    public void slaMeldingOp(String plaatsnaam, String beschrijving){
        LocalDateTime datum = LocalDateTime.now();

        meldingViewModel.insertMelding(plaatsnaam, beschrijving, datum.toString());
    }

    public void initMeldingObservers(){
        meldingViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                // Show a Toast or handle the error message
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        meldingViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null) {
                Toast.makeText(requireContext(), successMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void returnToMain(){
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        getActivity().finish();
    }

    private void initSluitAppButton() {
        View sluitButton = getView().findViewById(R.id.sluitApp);
        sluitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}