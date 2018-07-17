package com.capgemini.wallet.service;

import java.math.BigDecimal;
import java.util.Scanner;
import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;
import com.capgemini.wallet.repo.WalletRepo;
import com.capgemini.wallet.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	WalletRepo repo = new WalletRepoImpl();
	Customer customer;
	Wallet wallet;
	
	public WalletServiceImpl() {
		super();
	}
	Scanner scanner = new Scanner(System.in);

	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		// validate here
		
		wallet = new Wallet(amount);
		customer = new Customer(name, mobileNo, wallet);
		
//		do {
//			b1 = isValid(customer);
//			
//		}while(!b1);
		
		if(isValid(customer)) {
			boolean b = repo.save(customer);
				return customer;
		}
		else {
			throw new InvalidInputException("Incorrect Mobile Number! Enter again ");
		}
			
		}
		
		
		
	

	@Override
	public Customer showBalance(String mobileno) throws InvalidInputException {
		
		 Customer customer=repo.findOne(mobileno);
		 if(customer!=null) {
			 return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
		
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException {
		
		Customer customerSource = repo.findOne(sourceMobileNo);
		Customer customerTarget = repo.findOne(targetMobileNo);
		
		int i=customerSource.getWallet().getBalance().compareTo(amount);
		
		if(i!=-1) {
			Wallet wallet0=customerSource.getWallet();
			wallet0.setBalance(wallet0.getBalance().subtract(amount));
			customerSource.setWallet(wallet0);
			boolean b = repo.save(customerSource);
			
			Wallet wallet1 = customerTarget.getWallet();
			wallet1.setBalance(wallet1.getBalance().add(amount));
			
			customerTarget.setWallet(wallet1);
			boolean b1 = repo.save(customerTarget);
			return customerSource;
			
		}
		else {
			throw new InvalidInputException("Invalid amount ");
		}

	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null) {
			wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));
			
			customer.setWallet(wallet);
			boolean b = repo.save(customer);
			 return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
		
	
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null) {
			
			wallet = customer.getWallet();
			// amount validation here
			int i = wallet.getBalance().compareTo(amount);
			if(i!=-1) {
				wallet.setBalance(wallet.getBalance().subtract(amount));
				
				customer.setWallet(wallet);
				boolean b = repo.save(customer);
				 
			}
			else {
				throw new InsufficientBalanceException("INSUFFICIENT BALANCE");
			}
			
			return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
		
	
	}
	@Override
	public boolean isValid(Customer customer) throws InvalidInputException {
		
		/*	
		Pattern pattern2=Pattern.compile("[1-9][0-9]{9}");
		Matcher m=pattern2.matcher(customer.getMobileNo());
			if(m.matches())
				return true;
			else
					return false;*/
		
		String pattern ="[7-9][0-9]{9}";
		String cust=customer.getMobileNo();
		if(cust.matches(pattern))
			return true;
		else
			return false;
	}

	public boolean truncate() {
		boolean b=repo.truncate();
		return b;
	}
	
	
}
