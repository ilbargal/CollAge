package machineLearning;

/**
 * Constants for machine learning utility.
 */
public class mlConsts {
    //KNN
    public static int k = 5;
    public static int refreshInterval = 7200000;

    // This constants are used to calculate distance between
    // users in the knn algorithm.
    public static double matchingUserCategoryScore = -0.5;
    public static double unMatchingUserCategoryScore = 0.5;
}
