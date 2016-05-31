package app.we.go.movies.model.local;

/**
 * Enum that offers various sorting options
 * Created by apapad on 14/11/15.
 */
public enum SortByCriterion   {
    POPULARITY(0),
    VOTE(1),
    FAVORITES(2);

    /**
     * The index of the criterion, that must match the order in which it is displayed in the UI.
     */
    private final int index;

    SortByCriterion(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static SortByCriterion byIndex(int index) {
        for (SortByCriterion criterion: SortByCriterion.values()) {
            if (criterion.getIndex() == index) {
                return criterion;
            }
        }
        return null;
    }
}
