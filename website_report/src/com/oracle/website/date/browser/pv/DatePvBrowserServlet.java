package com.oracle.website.date.browser.pv;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



/**
 * Servlet implementation class FlowServlet
 */
public class DatePvBrowserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String type;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatePvBrowserServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		System.out.println(type);
		DatePvDao dao = new DatePvDao(type);
		DatePvEntity entity = dao.getDateUserList();
		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(entity);
		System.out.println(json);
		
		response.setContentType("json/application;charset=UTF-8");
		response.getWriter().write(json);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		type = request.getParameter("type");
	}

}
