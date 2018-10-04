package com.example.pokestar.vaccineremind.db;

import org.litepal.crud.DataSupport;

/**
 * Created by HP on 2018/9/28.
 */

public class County extends DataSupport {

    private int id;
    private int cityId;
    private String countyName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }


}
