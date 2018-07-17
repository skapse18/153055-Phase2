package com.capgemini.wallet.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.capgemini.wallet.beans.Customer;
import com.capgemini.wallet.beans.Wallet;
import com.capgemini.wallet.exception.InsufficientBalanceException;
import com.capgemini.wallet.exception.InvalidInputException;




public class WalletRepoImpl implements WalletRepo{
	Customer customer;
	Connection con;

	Wallet wallet;
	

	public WalletRepoImpl() {

		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/test";
		String uid="root";
		String pwd="corp123";
		
	 	con=DriverManager.getConnection(url,uid,pwd);
	 
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SQLException e) {
			 // TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	@Override
	public boolean save(Customer customer) throws InvalidInputException {
		
		Wallet wallet=customer.getWallet();
		BigDecimal balance=wallet.getBalance();
		

		System.out.println(balance);
		try
		{
		
				PreparedStatement pstmt = con.prepareStatement("insert into customer4 values(?,?,?)");
				pstmt.setString(1,customer.getMobileNo());
				pstmt.setString(2,customer.getName());
				pstmt.setBigDecimal(3,balance);
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			return true;
		
	}

	@Override
	public Customer findOne(String mobileNo) throws InvalidInputException{
		
			Customer customer = null;
			Wallet wallet=null;
		
	 	try
		{
			PreparedStatement pstm = con.prepareStatement("select * from Customer4 where MobileNo=?");
			
			pstm.setString(1,mobileNo);
			
			ResultSet res = pstm.executeQuery();
			
			if( res.next() == false)
				throw new InvalidInputException("No Registration with Mobile Number " + mobileNo);
			
			customer = new Customer();
			wallet = new Wallet();
			
			customer.setMobileNo(res.getString(1));
			customer.setName( res.getString(2) );
			wallet.setBalance(res.getBigDecimal(3));
		
			customer.setWallet(wallet);
			
		
		}
		catch(Exception e)
		{
			throw new InvalidInputException( e.getMessage() );
		}
		
		return customer ;

}


	@Override
	public Customer updateRecords(Customer customer) {
		
		Wallet wallet=null;
		wallet=customer.getWallet();
		
		BigDecimal balance=wallet.getBalance();
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt=con.prepareStatement("update Customer4 set balance=?");
			pstmt.setString(1, customer.getMobileNo());
			pstmt.setBigDecimal(2,balance);
			pstmt.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return customer;
	}


	@Override
	public boolean truncate() {

				try {
					
					String truncateQuery="truncate Customer4";
					Statement pstmt=con.createStatement();
					pstmt.executeUpdate(truncateQuery);
					return true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
				}
				return true;
			
	

	}		
}

