package com.capgemini.wallet.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;
import com.capgemini.wallet.repo.WalletRepo;
import com.capgemini.wallet.service.WalletService;
import com.capgemini.wallet.service.WalletServiceImpl;

public class TestClass {


 WalletService service;
	WalletRepo repo;
	 Customer customer1;
	 Customer customer2;
	 Customer customer3;
	
	
	@Before
	public void initialiseData() throws InvalidInputException {
		
		service= new WalletServiceImpl();
		boolean b=service.truncate();
		customer1=service.createAccount("Shraddha", "9860586605", new BigDecimal(9000));
		customer2=service.createAccount("Shiwani", "9423417114", new BigDecimal(10000));
		customer3=service.createAccount("Shrutika", "9421880660", new BigDecimal(8000));
	}

	@Test
	public void testshowBalance1() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer=service.showBalance("9860586605");
		assertEquals(new BigDecimal(9000), customer.getWallet().getBalance());
	}
	
	@Test
	public void testshowBalance2() throws InvalidInputException 
	{
		Customer customer = new Customer();
		customer=service.showBalance("9423417114");
		assertEquals(new BigDecimal(10000), customer.getWallet().getBalance());
	}
	@Test
	public void testshowBalance3() throws InvalidInputException 
	{
		Customer customer = new Customer();
		customer=service.showBalance("9421880660");
		assertEquals(new BigDecimal(8000), customer.getWallet().getBalance());
	}
	
	//Exception Test Cases
	
	@Test(expected = InsufficientBalanceException.class)
	public void testException1() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer=service.withdrawAmount("9860586605",new BigDecimal(9000000));
	}
	
	
	@Test(expected = InsufficientBalanceException.class)
	public void testException2() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer=service.withdrawAmount("9423417114",new BigDecimal(5000000));
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void testException3() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer=service.withdrawAmount("9421880660",new BigDecimal(6520000));
	}
	
	@Test
	public void testDepositBalance1() throws InvalidInputException 
	{
		Customer customer = new Customer();
		customer=service.depositAmount("9860586605", new BigDecimal(500));
		assertEquals(new BigDecimal(9500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testDepositBalance2() throws InvalidInputException 
	{
		Customer customer = new Customer();
		customer=service.depositAmount("9423417114", new BigDecimal(500));
		assertEquals(new BigDecimal(10500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testDepositBalance3() throws InvalidInputException 
	{
		Customer customer = new Customer();
		customer=service.depositAmount("9421880660", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testFundTransfer1() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer=service.fundTransfer("9860586605", "9423417114", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testFundTransfer2() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer=service.fundTransfer("9423417114", "9421880660", new BigDecimal(500));
		assertEquals(new BigDecimal(9500), customer.getWallet().getBalance());
	}
	@Test
	public void testFundTransfer3() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer=service.fundTransfer("9421880660", "9860586605", new BigDecimal(500));
		assertEquals(new BigDecimal(7500), customer.getWallet().getBalance());
	}
	
	@Test
	public void testWithdraw1() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer=service.withdrawAmount("9860586605", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance() );
	}
	
	@Test
	public void testWithdraw2() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer=service.withdrawAmount("9423417114", new BigDecimal(500));
		assertEquals(new BigDecimal(9500), customer.getWallet().getBalance() );
	}
	
	@Test
	public void testWithdraw3() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer=service.withdrawAmount("9421880660", new BigDecimal(500));
		assertEquals(new BigDecimal(7500), customer.getWallet().getBalance() );
	}
	
	@Test
	public void testfundTransfer4() throws InvalidInputException
	{
		Customer customer = service.fundTransfer("9860586605", "9423417114",new BigDecimal(5000));
	}
	
	@Test(expected=NullPointerException.class)
	public void testfindRepo() throws InvalidInputException
	{
		Customer customer = new Customer("","",new Wallet(null));
		customer=repo.findOne(customer.getMobileNo());
	}
	
	@Test
	public void isValid1() throws InvalidInputException
	{
		Customer customer = new Customer("Pratiksha","9654582153",new Wallet(new BigDecimal(7000)));
		assertTrue(service.isValid(customer));
	}
	
	@Test
	public void isValid2() throws InvalidInputException
	{
		Customer customer = new Customer("Raunak","9876543210",new Wallet(new BigDecimal(7000)));
		assertTrue(service.isValid(customer));
	}
	
	@Test
	public void isValid3() throws InvalidInputException
	{
		Customer customer = new Customer("Ashutosh","9012345678",new Wallet(new BigDecimal(7000)));
		assertTrue(service.isValid(customer));
	}
}

