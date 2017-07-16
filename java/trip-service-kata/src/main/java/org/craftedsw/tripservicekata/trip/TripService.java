package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;
import org.springframework.beans.factory.annotation.Autowired;

public class TripService {

	@Autowired private TripDAO tripDAO;
	
	public List<Trip> getFriendTrips(User friend, User loggedInUser) throws UserNotLoggedInException {
		validate(loggedInUser);
		
		return friend.isFriendsWith(loggedInUser) ? tripsBy(friend) : noTrips();
	}

	private void validate(User loggedInUser) {
		if (loggedInUser == null) {
			throw new UserNotLoggedInException();
		}
	}

	private List<Trip> tripsBy(User user) {
		return tripDAO.tripsBy(user);
	}
	
	private ArrayList<Trip> noTrips() {
		return new ArrayList<Trip>();
	}
}
