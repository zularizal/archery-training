package ar.com.tzulberti.archerytraining.fragments.playoff;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import ar.com.tzulberti.archerytraining.MainActivity;
import ar.com.tzulberti.archerytraining.R;
import ar.com.tzulberti.archerytraining.fragments.common.AbstractSerieArrowsFragment;
import ar.com.tzulberti.archerytraining.model.playoff.ComputerPlayOffConfiguration;
import ar.com.tzulberti.archerytraining.model.playoff.Playoff;

/**
 * Created by tzulberti on 6/2/17.
 */
public class AddPlayoffFragment extends BasePlayoffFragment {

    public static final String DISTANCE = "ar.com.tzulberti.archerytraining.distance";
    public static final String MIN_SCORE = "ar.com.tzulberti.archerytraining.minscore";
    public static final String MAX_SCORE = "ar.com.tzulberti.archerytraining.maxscore";

    private Map<String, EditText> inputMapping;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.cleanState(container);
        this.setObjects();

        View view = inflater.inflate(R.layout.playoff_add_new, container, false);
        MainActivity activity = (MainActivity) getActivity();

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        this.inputMapping = new HashMap<>();
        this.inputMapping.put(DISTANCE, (EditText) view.findViewById(R.id.distance));
        this.inputMapping.put(MIN_SCORE, (EditText) view.findViewById(R.id.min_socre));
        this.inputMapping.put(MAX_SCORE, (EditText) view.findViewById(R.id.max_socre));

        for (Map.Entry<String, EditText> info : inputMapping.entrySet()) {
            int existingValue = sharedPref.getInt(info.getKey(), -1);
            if (existingValue >= 0) {
                info.getValue().setText(String.valueOf(existingValue));
            }
        }

        return view;
    }

    @Override
    public void handleClick(View v) {
        Map<String, Integer> constructorKwargs = new HashMap<>();

        MainActivity activity = (MainActivity) getActivity();

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Bundle bundle = new Bundle();

        boolean foundError = false;
        String requiredValueError = getResources().getString(R.string.commonRequiredValidationError);
        for (Map.Entry<String, EditText> info : inputMapping.entrySet()) {
            String inputValue = info.getValue().getText().toString();
            if (StringUtils.isBlank(inputValue)) {
                info.getValue().setError(requiredValueError);
                foundError = true;
                continue;
            }

            int value = Integer.parseInt(inputValue);
            if (value <= 0) {
                info.getValue().setError(requiredValueError);
                foundError = true;
                continue;
            }

            editor.putInt(info.getKey(), value);
            constructorKwargs.put(info.getKey(), value);
            bundle.putInt(info.getKey(), value);
        }

        if (foundError) {
            editor.clear();
            return;
        }

        editor.commit();

        ComputerPlayOffConfiguration computerPlayOffConfiguration = new ComputerPlayOffConfiguration();
        computerPlayOffConfiguration.maxScore = bundle.getInt(MAX_SCORE);
        computerPlayOffConfiguration.minScore = bundle.getInt(MIN_SCORE);

        // Create the playoff
        Playoff playoff = this.playoffDAO.createPlayoff(
                this.getString(R.string.playoff_computer_name),
                bundle.getInt(DISTANCE),
                computerPlayOffConfiguration
                );


        Bundle arguments = new Bundle();
        arguments.putSerializable(AbstractSerieArrowsFragment.CONTAINER_ARGUMENT_KEY, playoff);
        arguments.putInt("creating", 1);

        ViewPlayoffSeriesFragment viewPlayoffSeriesFragment = new ViewPlayoffSeriesFragment();
        viewPlayoffSeriesFragment.setArguments(arguments);

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, viewPlayoffSeriesFragment)
                .commit();
    }

}

