package org.craftedsw.tripservicekata.trip;

import static org.craftedsw.tripservicekata.user.UserBuilder.aUser;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {
	
	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERD_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	
	private TripService tripService;
	
	@Before
	public void initialise() {
		tripService = new TestableTripService();
	}

	@Test (expected=UserNotLoggedInException.class)
	public void should_throw_an_exception_when_user_is_not_logged_in() throws Exception {
		TripService tripService = new TripService();
		
		
		tripService.getTripsByUser(UNUSED_USER, GUEST);
		
		
	}

	@Test
	public void should_not_return_any_trips_when_users_are_not_friends() throws Exception {
		User friend = aUser()
							.friendsWith(ANOTHER_USER)
							.withTrips(TO_BRAZIL)
							.build();
		
		List<Trip> friendTrips = tripService.getTripsByUser(friend, REGISTERD_USER);

		assertThat(friendTrips, is(0));
	}
	
	@Test
	public void should_return_friend_trips_when_users_are_friends() throws Exception {
		User friend = aUser()
						.friendsWith(ANOTHER_USER, REGISTERD_USER)
						.withTrips(TO_BRAZIL, TO_LONDON)
						.build();
		
		List<Trip> friendTrips = tripService.getTripsByUser(friend, REGISTERD_USER);
		
		assertThat(friendTrips, is(2));
	}
	
	public static class UserBuilder {
		
		private User[] friends = new User[] {};
		private Trip[] trips = new Trip[] {};

		public static UserBuilder aUser() {
			return new UserBuilder();
		}

		public UserBuilder friendsWith(User... friends) {
			this.friends = friends;
			return this;
		}
		public UserBuilder withTrips(Trip... trips) {
			this.trips = trips;
			return this;
		}
		
		public User build() {
			User user = new User();
			addTripsTo(user);
			addFriendsTo(user);
			
			return user;
		}

		private void addFriendsTo(User user) {
			for (User friend : friends) {
				user.addFriend(friend);
			}
		}

		private void addTripsTo(User user) {
			for (Trip trip : trips) {
				user.addTrip(trip);
			}
		}
	}
	
	private class TestableTripService extends TripService {
		
		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
		}
	}
}
