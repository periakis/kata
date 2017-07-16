package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		if (getLoggedInUser() == null) {
			throw new UserNotLoggedInException();
		}

		return user.isFriendsWith(getLoggedInUser()) ? tripsBy(user) : noTrips();
	}

	protected User getLoggedInUser() {
		return UserSession.getInstance().getLoggedUser();
	}

	protected List<Trip> tripsBy(User user) {
		return TripDAO.findTripsByUser(user);
	}
	
	private ArrayList<Trip> noTrips() {
		return new ArrayList<Trip>();
	}
}
