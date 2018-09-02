package co.com.almundo.callcenter.domain;

import org.junit.Test;

import co.com.almundo.callcenter.model.Call;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CallTest {

	@Test(expected = NullPointerException.class)
	public void testCallExecuteWithNullParameter() {
		new Call(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCallExecuteWithInvalidParameter() {
		new Call(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRandomCallExecuteWithInvalidSecondParameter() {
		Call.buildRandomCall(1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRandomCallExecuteWithInvalidFirstParameter() {
		Call.buildRandomCall(-1, 1);
	}

	@Test
	public void testRandomCallExecuteWithValidParameters() {
		Integer min = 5;
		Integer max = 10;
		Call call = Call.buildRandomCall(min, max);

		assertNotNull(call);
		assertTrue(min <= call.getDurationInSeconds());
		assertTrue(call.getDurationInSeconds() <= max);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRandomCallExecuteWithInvalidParameter() {
		Call.buildRandomCall(2, 1);
	}

	@Test
	public void testRandomCallExecuteWithValid() {
		Integer min = 4;
		Integer max = 11;
		Call call = Call.buildRandomCall(min, max);

		assertNotNull(call);
		assertTrue(min <= call.getDurationInSeconds());
		assertTrue(call.getDurationInSeconds() <= max);
	}

}
