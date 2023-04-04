package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		assertFalse("To check with null", testAccount.timedPaymentExists("27897"));
		testAccount.addTimedPayment("27897", 15, 1, new Money(10000000, SEK), SweBank, "Alice");
		assertTrue("Checking account after adding", testAccount.timedPaymentExists("27897"));
		testAccount.removeTimedPayment("27897");
		assertFalse("Checking account after removing", testAccount.timedPaymentExists("27897"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("27897", 1, 1, new Money(10_00_000, SEK), SweBank, "Alice");
		assertEquals("Before tick",10_000_000, testAccount.getBalance().getAmount().intValue());
		assertEquals("Before tick SweBank",10_00_000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(10_000_000, testAccount.getBalance().getAmount().intValue());
		assertEquals(10_00_000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(90_00_000, testAccount.getBalance().getAmount().intValue());
		assertEquals(20_00_000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(9000000, testAccount.getBalance().getAmount().intValue());
		assertEquals(2000000, SweBank.getBalance("Alice").intValue());
		testAccount.tick();
		assertEquals(8000000, testAccount.getBalance().getAmount().intValue());
		assertEquals(3000000, SweBank.getBalance("Alice").intValue());
	
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(5000, SEK));
		assertEquals(10_005_000, testAccount.getBalance().getAmount().intValue());
		testAccount.withdraw(new Money(1000, SEK));
		assertEquals(10_004_000, testAccount.getBalance().getAmount().intValue());
		
	}
	
	@Test
	public void testGetBalance() {
		assertEquals("Hans", 10000000, testAccount.getBalance().getAmount().intValue());
	}
}
