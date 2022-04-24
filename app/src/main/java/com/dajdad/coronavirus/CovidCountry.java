package com.dajdad.coronavirus;

public class CovidCountry {
    private String mCovidCountry, mCases, mTodayCases, mRecovered, mCritical, mDeaths,
                    mTodayDeaths;

    public CovidCountry(String mCovidCountry, String mCases) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
    }

    public CovidCountry(String mCovidCountry, String mCases, String mRecovered, String mDeaths) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
        this.mRecovered = mRecovered;
        this.mDeaths = mDeaths;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public String getmCases() {
        return mCases;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public String getmDeaths() {
        return mDeaths;
    }
}
