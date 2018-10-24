package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import dal.DatabaseRepo;
import model.Person;

public class PersonServlet extends HttpServlet {

	// Method to handle GET method request.
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().write("doGET is not allowed to be used in this context");
		response.getWriter().flush();
	}

	// Method to handle POST method request.
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("first_name"); //preluam parametrul de nume din formular din request
		String email = request.getParameter("email"); //preluam parametrul de email din formular din request

		Person person = new Person(email, name); //creeam un obiect de tip person

		DatabaseRepo repo = new DatabaseRepo("homework1"); //creeam un nou repository pentru baza de date

		repo.createOrUpdate(person); //apelam metoda de inserare/update in DB

		response.getWriter().write("<html>" + "<body>" + "A new person was inserted with the following <br> Id: " + person.getId() + "<br> Nume: " + person.getName() + "<br> Email: " + person.getEmail() + "</body>" + "</html>");
		response.getWriter().flush();
	}
}