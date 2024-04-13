package com.login.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final long serialVersionUID = 1L;
	       
	
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session =request.getSession();
		RequestDispatcher dispatcher=null;
		
		if(uemail==null || uemail.equals("")) {
			request.setAttribute("status", "invalidEmail");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
		}
		if(upwd==null || upwd.equals("")) {
			request.setAttribute("status", "invalidUpwd");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request,response);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/records?useSSL=false","root","MySQL@123456");
			PreparedStatement pst=con.prepareStatement("select * from  users where uemail=? and upwd=?");
			pst.setString(1, uemail);//same name as table in sql
			pst.setString(2,upwd);
			
			ResultSet rs=pst.executeQuery();
			
			 
			
			if(rs.next()) {
				
				session.setAttribute("name", rs.getString("uname"));
				
				dispatcher =request.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
				System.out.println("if succesfully login : ");
				System.out.println(uemail);
				System.out.println(upwd);
				
			}
			else {
				request.setAttribute("status", "failed");
				dispatcher =request.getRequestDispatcher("/login.jsp");
				dispatcher.forward(request,response);
				System.out.println("if succesfully not login : ");
				System.out.println(uemail);
				System.out.println(upwd);
			}
				
		}
		catch(Exception e){
        e.printStackTrace();
		}
	}
	

}
