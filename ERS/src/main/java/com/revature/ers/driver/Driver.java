package com.revature.ers.driver;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.impl.CompanyDaoImpl;
import com.revature.ers.dao.impl.InfoDaoImpl;
import com.revature.ers.dao.impl.ReimbursementDaoImpl;
import com.revature.ers.dao.impl.UserDaoImpl;
import com.revature.ers.model.Company;
import com.revature.ers.model.Info;
import com.revature.ers.model.Reimbursement;
import com.revature.ers.model.User;

public class Driver {
	private static Logger log = Logger.getRootLogger();
	public static void main(String[] args) {
		
		InfoDaoImpl idi = new InfoDaoImpl();
		CompanyDaoImpl cdi = new CompanyDaoImpl();
		UserDaoImpl udi = new UserDaoImpl();
		ReimbursementDaoImpl rdi = new ReimbursementDaoImpl();
//		System.out.println(companies);
//		System.out.println(allInfo);
//		System.out.println(users);
//		System.out.println(reimbursements);
		
//		System.out.println(company);
//		System.out.println(info);
//		System.out.println(user);
//		System.out.println(reimbursement);
		
		//test company methods
//		List<Company> companies = new ArrayList<Company>();
//		companies = cdi.getCompanies();
//		Company company = cdi.getCompany(1);
//		Company company1= new Company( "this is a company", "this is its address");
//		cdi.createCompany(company1);
//		cdi.updateCompany(2, company1);
//		cdi.deleteCompany(2);
//		System.out.println(companies);
		
		//test user methods
		//User user1 = new User("g@g.com", "pass", 0, 1, 1, false);
		//udi.createUser(user1);
		//udi.deleteUser(2);
		List<User> users = udi.getUsers();
//		
//		User user = udi.getUser(1);
//		User user2 = udi.getLastCreatedUser();
//		User user3 = udi.getUser("j@j.com");
//		User user4 = new User("f@f.com", "passingword", 0, 1, 1, false);
//		//udi.updateUser(3, user4);
//		users = udi.getUsers();
		//System.out.println(users);
		
		//test info methods
		
//		
//		Info info = idi.getInfo(1);
//		Info info1 = new Info("bill", "Billson", "71717171717", "this is an address", "proletariate","http://words.com", 3);
//		idi.createInfo(info1);
//		Info info2 = new Info("jill", "jillillson", "71717171717", "this is an address", "proletariate","http://words.com", 3);
//		update
//		delete
//		
//		
//		idi.updateInfo(4, info2);
//		idi.deleteInfo(5);
//		List<Info> allInfo = new ArrayList<Info>();
//		allInfo = idi.getAllInfo();
		
		//test reimbursements
		
		//create
		//update
		//delete
		
		
//		LocalDate date = LocalDate.of(2000, 10, 9);
//		//System.out.println(date);
//		Reimbursement rb = new Reimbursement(3,1,100,"illegitimate reason", date, false, false);
//		//rdi.createReimbursement(rb);
//		//rdi.updateReimbursement(2, rb);
//		rdi.deleteReimbursement(2);
//		List<Reimbursement> reimbursements = rdi.getReimbursementsByCompanyId(1);
//		reimbursements = rdi.getReimbursements();
//		Reimbursement reimbursement = rdi.getReimbursement(1);
		
		log.info(users);
		//System.out.println(idi.getInfoByUserId(17));
		//Info info1 = new Info("bill", "Billson", "71717171717", "this is an address", "proletariate","http://words.com", 3);
		
	}

}
