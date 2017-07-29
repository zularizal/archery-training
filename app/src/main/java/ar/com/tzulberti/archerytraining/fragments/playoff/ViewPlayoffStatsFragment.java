package ar.com.tzulberti.archerytraining.fragments.playoff;

import ar.com.tzulberti.archerytraining.MainActivity;
import ar.com.tzulberti.archerytraining.fragments.common.AbstractArrowSeriesStatsFragment;

/**
 * Created by tzulberti on 6/26/17.
 */

public class ViewPlayoffStatsFragment extends AbstractArrowSeriesStatsFragment {


    protected void setBaseArrowSeriesDAO() {
        MainActivity activity = (MainActivity) getActivity();
        this.baseArrowSeriesDAO = activity.getPlayoffDAO();
    }
}