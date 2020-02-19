/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author alumno
 *
 */
public class ReusablePoolTest {
	
	private ReusablePool reusablePool;
	private Reusable reusable;
	private List<Reusable> reusables;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		reusablePool = ReusablePool.getInstance();
		reusable = null;
		reusables = new ArrayList<>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		for (Reusable reusable : reusables) {
			reusablePool.releaseReusable(reusable);
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool instance1 = ReusablePool.getInstance();
		ReusablePool instance2 = ReusablePool.getInstance();
		assertTrue("Instacias diferentes", instance1 == instance2);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {
		try {
			reusable = this.reusablePool.acquireReusable();
		} catch (NotFreeInstanceException e) {
			fail("Salta la excepcion");
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		try {
			reusablePool.releaseReusable(reusable);
		} catch (DuplicatedInstanceException e) {
			fail("Salta la excepcion");
		}
	}
	
	@Test(expected = NotFreeInstanceException.class)
	public void testNotFreeInstanceException() throws NotFreeInstanceException {
		ReusablePool pool = ReusablePool.getInstance();
		reusables.add(pool.acquireReusable());
		reusables.add(pool.acquireReusable());
		reusables.add(pool.acquireReusable());
	}
	
	@Test(expected = DuplicatedInstanceException.class)
	public void testDuplicatedInstanceException() throws NotFreeInstanceException, DuplicatedInstanceException {
		ReusablePool pool = ReusablePool.getInstance();
		Reusable reusable = pool.acquireReusable();
		pool.releaseReusable(reusable);
		pool.releaseReusable(reusable);
	}
}
