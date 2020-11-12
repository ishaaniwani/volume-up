package com.example.volumeup;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FeedbackDeterminer {

    private static FeedbackDeterminer mFeedbackDeterminer;

    private static final int speechMax = 20;
    private static final int amplitudeMax = 100;

    private ArrayList<Integer> amplitudeValues = new ArrayList<Integer>();
    private ArrayList<Boolean> speechValues = new ArrayList<Boolean>();

    private FeedbackDeterminer() {
        amplitudeValues = new ArrayList<Integer>();
        speechValues = new ArrayList<Boolean>();
    }

    public static synchronized FeedbackDeterminer getInstance() {
        if (mFeedbackDeterminer == null) {
            mFeedbackDeterminer = new FeedbackDeterminer();
        }
        return mFeedbackDeterminer;
    }

    public boolean addAmplitude(int amplitude) {
        amplitudeValues.add(amplitude);
        if (amplitudeValues.size() >= amplitudeMax) {
            amplitudeValues.remove(0);
            return false;
        }
        return true;
    }

    public boolean addSpeech(boolean isSpeech) {
        speechValues.add(isSpeech);
        if (speechValues.size() >= speechMax) {
            speechValues.remove(0);
            return false;
        }
        return true;
    }

    public void clear() {

    }

    /**
     * Determines whether the app shoudl provide biofeedback or not.
     * true - yes, the app should provide feedback
     * false - no, the app should not provide feedback
     * @param dbThreshold
     * @return
     */
    public boolean determineToProvideFeedback(int dbThreshold) {
        int avgAmp = 0;
        int speechAmp = 0;
        for (Integer amp: amplitudeValues) {
            avgAmp += amp;
        }
        for (Boolean speech: speechValues) {
            if (speech) {
                speechAmp += 1;
            }
            else if (!speech) {
                speechAmp -= 1;
            }
        }
        return avgAmp <= dbThreshold && speechAmp >= 0;
    }
}
