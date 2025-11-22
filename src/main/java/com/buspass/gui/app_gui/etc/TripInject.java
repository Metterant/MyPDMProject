package com.buspass.gui.app_gui.etc;

import com.buspass.auth.UserLoginSession;
import com.buspass.queries.TripQuery;

public class TripInject {
    private UserLoginSession userLoginSession;
    private TripQuery tripQuery;
    private Integer tripId;

    public TripInject(UserLoginSession userLoginSession, TripQuery tripQuery, Integer tripId) {
        this.userLoginSession = userLoginSession;
        this.tripQuery = tripQuery;
        this.tripId = tripId;
    }

    public UserLoginSession getUserLoginSession() { return userLoginSession; }
    public TripQuery getTripQuery() { return tripQuery; }
    public Integer getTripID() { return tripId;}
}
