package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("From Swebank","SweBank",SweBank.getName());
		assertEquals("From Nordea","Nordea",Nordea.getName());
		assertEquals("From DanskeBank","DanskeBank",DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("From SwebankC",SEK,SweBank.getCurrency());
		assertEquals("From NordeaC",SEK,Nordea.getCurrency());
		assertEquals("From DanskeBankC",DKK,DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		try {
			SweBank.openAccount("Vinit");
		} catch (AccountExistsException e) {
			fail("Vinit 1");
		}
		try {
			SweBank.openAccount("Bob");
			fail("already exist1");
			Nordea.openAccount("Bob");
			fail("already exist2");
			DanskeBank.openAccount("Gertrud");
			fail("already exist3");
			SweBank.openAccount("Vinit");
			fail("already exist4");
		} catch (AccountExistsException ignored) {
		}
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertEquals("Before Deposit", 0, SweBank.getBalance("Bob").intValue());
		SweBank.deposit("Bob", new Money(10000, SEK));
		assertEquals("After Deposit", 10000, SweBank.getBalance("Bob").intValue());
//		
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(10000, SEK));
		SweBank.withdraw("Bob", new Money(500, SEK));
		assertEquals("After withdrawal",9500, SweBank.getBalance("Bob").intValue()); 
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(10000, SEK));
		assertEquals("After withdrawal",10000, SweBank.getBalance("Bob").intValue());
		Nordea.deposit("Bob", new Money(5000, SEK));
		assertEquals(5000, Nordea.getBalance("Bob").intValue());
	}
	
	@Test(expected= AccountDoesNotExistException.class)
	public void testTransfer() throws AccountDoesNotExistException, AccountExistsException {
	
		assertEquals(0, SweBank.getBalance("Bob").intValue());
		assertEquals(0, Nordea.getBalance("Bob").intValue());
		DanskeBank.openAccount("Oleksii");
		assertEquals(0, DanskeBank.getBalance("Oleksii").intValue());
		SweBank.deposit("Bob", new Money(1000, SEK));
		SweBank.transfer("Bob", Nordea, "Bob", new Money(500, SEK));
		SweBank.transfer("Bob", DanskeBank, "Oleksii", new Money(1500, SEK));
		assertEquals(-1000, SweBank.getBalance("Bob").intValue());
		assertEquals(500, Nordea.getBalance("Bob").intValue());
		assertEquals(1125, DanskeBank.getBalance("Oleksii").intValue());
		DanskeBank.transfer("Oleksii", SweBank, "Glob", new Money(10000, SEK));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		
		
		SweBank.deposit("Ulrika", new Money(100_000_00, SEK));
		Nordea.deposit("Bob", new Money(10_000_00, SEK));
		SweBank.addTimedPayment("Ulrika", "payment1", 2, 1, new Money(10_000_00, SEK), Nordea, "Bob");
		assertEquals("before tick1 Ulrika", 100_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("before tick1 Bob", 10_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick1 Ulrika", 100_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick1 Bob", 10_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick2 Ulrika", 90_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick2 Bob", 20_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick3 Ulrika", 90_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick3 Bob", 20_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick4 Ulrika", 90_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick4 Bob", 20_000_00, Nordea.getBalance("Bob").intValue());
		SweBank.tick();
		assertEquals("after tick5 Ulrika", 80_000_00, SweBank.getBalance("Ulrika").intValue());
		assertEquals("after tick5 Bob", 30_000_00, Nordea.getBalance("Bob").intValue());
	}
}
