package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertEquals("EUR",EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		
		assertEquals("At 0.15",0.15,SEK.getRate(),0.001);
		assertEquals("At 0.20",0.20,DKK.getRate(),0.001);
		assertEquals("At 1.5",1.5,EUR.getRate(),0.001);
	}
	
	@Test
	public void testSetRate() {

		// Setting new rates with random values and verifying whether they have changed
		SEK.setRate(0.6);
		DKK.setRate(0.8);
		EUR.setRate(1.9);
		
		assertEquals("At 0.6",0.6,SEK.getRate(),0.001);
		assertEquals("At 0.8",0.8,DKK.getRate(),0.001);
		assertEquals("At 1.9",1.9,EUR.getRate(),0.001);
	}
	
	@Test
	public void testGlobalValue() {
//		Converting the currencies in a Universal value and verifying if they match 
		assertEquals("At SEK",225,SEK.universalValue(1500).intValue());
		assertEquals("At DKK",300,DKK.universalValue(1500).intValue());
		assertEquals("At EUR",2250,EUR.universalValue(1500).intValue());
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals("At SEK - DKK",2000,SEK.valueInThisCurrency(1500,DKK),0.001);
		assertEquals("At DKK-EUR",1125,DKK.valueInThisCurrency(150,EUR),0.001);
		assertEquals("At EUR-SEK",150,EUR.valueInThisCurrency(1500,SEK),0.001);
	}

}
