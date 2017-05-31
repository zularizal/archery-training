package ar.com.tzulberti.archerytraining.fragments.tournament;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import ar.com.tzulberti.archerytraining.MainActivity;
import ar.com.tzulberti.archerytraining.R;
import ar.com.tzulberti.archerytraining.model.tournament.Tournament;
import ar.com.tzulberti.archerytraining.model.tournament.TournamentSerie;

/**
 * Created by tzulberti on 5/17/17.
 */

public class AddTournamentFragment extends BaseTournamentFragment {

    private static final int[] REQUIRED_VALUES = {
            R.id.name,
            R.id.distance,
            R.id.target_size
    };

    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.cleanState(container);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tournament_add_new, container, false);
        this.setObjects();

        return view;
    }

    @Override
    public void handleClick(View clickButton) {
        // validate the values to make sure everything is ok
        View v = this.getView();
        for (int elementId : this.REQUIRED_VALUES) {
            EditText element = (EditText) v.findViewById(elementId);

            if (element == null) {
                // TODO check what to do in this case
            }
            String value = element.getText().toString();

            if (StringUtils.isBlank(value)) {
                element.setError(getText(R.string.commonRequiredValidationError));
                return ;
            }
        }

        Tournament tournament = this.tournamentDAO.createTournament(
                ((EditText) v.findViewById(R.id.name)).getText().toString(),
                Integer.valueOf(((EditText) v.findViewById(R.id.distance)).getText().toString()),
                Integer.valueOf(((EditText) v.findViewById(R.id.target_size)).getText().toString()),
                ((CheckBox) v.findViewById(R.id.is_outdoor)).isChecked(),
                ((CheckBox) v.findViewById(R.id.is_tournament)).isChecked()
        );

        
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        MainActivity activity = (MainActivity) this.getActivity();
        Bundle bundle = new Bundle();
        bundle.putLong("tournamentId", tournament.id);
        bundle.putInt("creating", 1);

        ViewTournamentSeriesFragment tournamentSeriesFragment = new ViewTournamentSeriesFragment();
        tournamentSeriesFragment.setArguments(bundle);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, tournamentSeriesFragment)
                .commit();
    }
}
