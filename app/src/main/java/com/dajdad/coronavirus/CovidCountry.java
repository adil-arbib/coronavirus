package com.dajdad.coronavirus;

public class CovidCountry {
    private String mCovidCountry, mCases, mTodayCases, mRecovered, mCritical, mDeaths,
                    mTodayDeaths;

    public CovidCountry(String mCovidCountry, String mCases) {
        this.mCovidCountry = mCovidCountry;
        this.mCases = mCases;
        this.mTodayCases = mTodayCases;
        this.mRecovered = mRecovered;
        this.mCritical = mCritical;
        this.mDeaths = mDeaths;
        this.mTodayDeaths = mTodayDeaths;
    }

    public String getmCovidCountry() {
        return mCovidCountry;
    }

    public String getmCases() {
        return mCases;
    }
}
