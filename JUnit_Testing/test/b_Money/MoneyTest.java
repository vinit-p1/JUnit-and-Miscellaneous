package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000,SEK100.getAmount().intValue());
		assertEquals(1000,EUR10.getAmount().intValue());
		assertEquals(20000,SEK200.getAmount().intValue());
		assertEquals(2000,EUR20.getAmount().intValue());
		assertEquals(0,SEK0.getAmount().intValue());
		assertEquals(0,EUR0.getAmount().intValue());
		assertEquals(-10000,SEKn100.getAmount().intValue());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK,SEK100.getCurrency());
		assertEquals(EUR,EUR10.getCurrency());
		assertEquals(SEK,SEK200.getCurrency());
		assertEquals(EUR,EUR20.getCurrency());
		assertEquals(SEK,SEK0.getCurrency());
		assertEquals(EUR,EUR0.getCurrency());
		assertEquals(SEK,SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100.0 SEK",SEK100.toString());
		assertEquals("10.0 EUR",EUR10.toString());
		assertEquals("100.0 SEK",SEK100.toString());
		assertEquals("10.0 EUR",EUR10.toString());
		assertEquals("100.0 SEK",SEK100.toString());
		assertEquals("10.0 EUR",EUR10.toString());
		assertEquals("-100.0 SEK",SEKn100.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(1500, SEK100.universalValue().intValue());
		assertEquals(1500, EUR10.universalValue().intValue());
		assertEquals(3000, SEK200.universalValue().intValue());
		assertEquals(3000, EUR20.universalValue().intValue());
		assertEquals(0, SEK0.universalValue().intValue());
		assertEquals(0, EUR0.universalValue().intValue());
		assertEquals(-1500, SEKn100.universalValue().intValue());
	}

	@Test
	public void testEqualsMoney() {
		assertFalse("SEK100 != SEK200", SEK100.equals(SEK200));
		assertFalse("SEKn100 != EUR20", SEKn100.equals(EUR20));
		assertTrue("SEK100 = EUR10", SEK100.equals(EUR10));
	}

	@Test
	public void testAdd() {
		assertEquals("100 SEK + 200 SEK",30000,SEK100.add(SEK200).getAmount().intValue());
		assertEquals("100 SEK + 20 EUR",30000,SEK100.add(EUR20).getAmount().intValue());
		assertEquals("200 SEK + 20 EUR", 40000, SEK200.add(EUR20).getAmount().intValue());
		assertEquals("0 SEK + 10 EUR", 10000, SEK0.add(EUR10).getAmount().intValue());
		assertEquals("10 EUR + -100 SEK", 0, EUR10.add(SEKn100).getAmount().intValue());
	}

	@Test
	public void testSub() {
		assertEquals("200 SEK - 100 SEK",10000,SEK200.sub(SEK100).getAmount().intValue());
		assertEquals("20 EUR - 100 SEK",1000,EUR20.sub(SEK100).getAmount().intValue());
		assertEquals("20 EUR - 200 SEK", 0, EUR20.sub(SEK200).getAmount().intValue());
		assertEquals("10 EUR - 0 SEK", 1000, EUR10.sub(SEK0).getAmount().intValue());
		assertEquals("10 EUR - -100 SEK", 2000, EUR10.sub(SEKn100).getAmount().intValue());
	}

	@Test
	public void testIsZero() {
		assertFalse(SEK100.isZero());
		assertFalse(SEKn100.isZero());
		assertTrue(SEK0.isZero());
		assertFalse(SEK200.isZero());
		assertFalse(EUR10.isZero());
		assertTrue(EUR0.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(-10000,SEK100.negate().getAmount().intValue());
		assertEquals(-1000,EUR10.negate().getAmount().intValue());
		assertEquals(-20000,SEK200.negate().getAmount().intValue());
		assertEquals(-2000,EUR20.negate().getAmount().intValue());
		assertEquals(0,SEK0.negate().getAmount().intValue());
		assertEquals(0,EUR0.negate().getAmount().intValue());
		assertEquals(10000,SEKn100.negate().getAmount().intValue());
	}

	@Test
	public void testCompareTo() {
		assertFalse("SEKn100 > SEK100", SEKn100.compareTo(SEK100) > 0);
		assertTrue("EUR0 > SEKn100", EUR0.compareTo(SEKn100) > 0);
		assertTrue("EUR0 = SEK0", EUR0.compareTo(SEK0) == 0);
		assertTrue("EUR10 = SEK100", EUR10.compareTo(SEK100) == 0);
	}
}
