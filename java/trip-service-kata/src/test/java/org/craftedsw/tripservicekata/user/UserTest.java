package org.craftedsw.tripservicekata.user;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class UserTest {
	
	private static final User BOB = new User();
	private static final User PAUL = new User();

	@Test
	public void should_inform_when_users_are_not_friends() throws Exception {
		
		User user = UserBuilder.aUser()
						.friendsWith(BOB)
						.build();
		
		assertThat(user.isFriendsWith(PAUL), is(false));
	}
	
	@Test
	public void should_inform_when_uesrs_are_friends() throws Exception {

		User user = UserBuilder.aUser()
				.friendsWith(BOB, PAUL)
				.build();
		
		assertThat(user.isFriendsWith(PAUL), is(true));
		
	}
}
