package ar.com.tzulberti.archerytraining.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ar.com.tzulberti.archerytraining.dao.TournamentConstraintDAO;
import ar.com.tzulberti.archerytraining.database.consts.TournamentConstraintConsts;
import ar.com.tzulberti.archerytraining.database.consts.TournamentConsts;
import ar.com.tzulberti.archerytraining.model.common.TournamentConstraint;

/**
 * Used to insert all the fixture values into the database.
 *
 * Created by tzulberti on 7/19/17.
 */
public class InsertConstantValues {


    public void insertAllFixtureData(SQLiteDatabase db) {
        this.insertTournamentConstrainsData(db);
    }

    public void insertTournamentConstrainsData(SQLiteDatabase db) {
        List<TournamentConstraint> constraints = new ArrayList<TournamentConstraint>();
        constraints.add(new TournamentConstraint(1, "FITA 70x70 - 70m", 70, 6, 6, 1, "complete_archery_target.png", false));
        constraints.add(new TournamentConstraint(2, "FITA 70x70 - 60m", 60, 6, 6, 1, "complete_archery_target.png", false));
        constraints.add(new TournamentConstraint(3, "FITA 70x70 - 50m", 50, 6, 6, 1, "complete_archery_target.png", false));
        constraints.add(new TournamentConstraint(4, "FITA 70x70 - 30m", 30, 6, 6, 1, "complete_archery_target.png", false));
        constraints.add(new TournamentConstraint(5, "FITA 70x70 - 20m", 20, 6, 6, 1, "complete_archery_target.png", false));
        constraints.add(new TournamentConstraint(6, "Indoor - 18m - Triple Spot", 18, 10, 3, 6, "triple_spot_target.png", true));
        constraints.add(new TournamentConstraint(7, "Indoor - 18m - 40cm", 18, 10, 3, 1, "complete_archery_target.png", true));
        constraints.add(new TournamentConstraint(8, "Indoor - 18m - 60cm", 18, 10, 3, 1, "complete_archery_target.png", true));
        constraints.add(new TournamentConstraint(9, "Indoor - 18m - 80cm", 18, 10, 3, 1, "complete_archery_target.png", true));

        for (TournamentConstraint tournamentConstraint : constraints) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TournamentConstraintConsts.ID_COLUMN_NAME, tournamentConstraint.id);
            contentValues.put(TournamentConstraintConsts.NAME_COLUMN_NAME, tournamentConstraint.name);
            contentValues.put(TournamentConstraintConsts.ARROWS_PER_SERIES_COLUMN_NAME, tournamentConstraint.arrowsPerSeries);
            contentValues.put(TournamentConstraintConsts.DISTANCE_COLUMN_NAME, tournamentConstraint.distance);
            contentValues.put(TournamentConstraintConsts.IS_OUTDOOR_COLUMN_NAME, tournamentConstraint.isOutdoor ? 1: 0);
            contentValues.put(TournamentConstraintConsts.MIN_SCORE_COLUMN_NAME, tournamentConstraint.minScore);
            contentValues.put(TournamentConstraintConsts.SERIES_PER_ROUND_COLUMN_NAME, tournamentConstraint.seriesPerRound);
            contentValues.put(TournamentConstraintConsts.TARGET_IMAGE_COLUMN_NAME, tournamentConstraint.targetImage);

            db.insertOrThrow(TournamentConstraintConsts.TABLE_NAME, null, contentValues);
        }
    }
}
