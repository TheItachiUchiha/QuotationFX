package com.kc.constant;

public class SQLConstants {
	
	//Constants for ComponentsDAO
	
	public static final String SAVE_COMPONENT = "INSERT INTO components(name,category,subcategory,vendor,model,type,size,costprice,dealerprice,enduserprice) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_COMPONENT = "UPDATE components SET name=?,category=?,subcategory=?,vendor=?,model=?,type=?,size=?,costprice=?,dealerprice=?,enduserprice=? where ID=?";
	public static final String GET_COMPONENTS = "SELECT * FROM COMPONENTS";
	public static final String DELETE_COMPONENT = "DELETE FROM COMPONENTS WHERE ID=?";
	public static final String GET_COMPONENTS_CATEGORY_LIST = "SELECT distinct category FROM components";
	public static final String GET_COMPONENTS_SUBCATEGORY_LIST = "SELECT distinct subcategory FROM components";
	public static final String GET_COMPONENTS_NAME_LIST = "SELECT distinct name FROM components";
	public static final String GET_COMPONENTS_MODEL_LIST = "SELECT distinct model FROM components";
	public static final String GET_COMPONENTS_VENDOR_LIST = "SELECT distinct vendor FROM components";
	public static final String GET_COMPONENTS_TYPE_LIST = "SELECT distinct type FROM components";
	public static final String GET_COMPONENTS_SIZE_LIST = "SELECT distinct size FROM components";
	
	//Constants for CustomersDAO
	
	public static final String SAVE_CUSTOMER = "INSERT INTO customers(customername,companyname,address,city,state,email,contactnumber,type,tinnumber,telephone,website,complaint_count,service_count) VALUES(?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_CUSTOMER = "UPDATE customers SET customername=?,companyname=?,address=?,city=?,state=?,email=?,contactnumber=?,type=?,tinnumber=?,telephone=?,website=? where ID=?";
	public static final String GET_CUSTOMERS = "SELECT * FROM CUSTOMERS";
	public static final String DELETE_CUSTOMER = "DELETE FROM CUSTOMERS WHERE ID=?";
	public static final String GET_CUSTOMER_NAME_LIST = "SELECT distinct customername FROM customers";
	public static final String GET_CUSTOMER_COMPANY_LIST = "SELECT distinct companyname FROM customers";
	public static final String GET_CUSTOMER_CITY_LIST = "SELECT distinct city FROM customers";
	public static final String GET_CUSTOMER_STATE_LIST = "SELECT distinct state FROM customers";
	
	//Constants for DispatchDAO
	
	public static final String GET_DISPATH_ENQUIRY_LIST = "select e.ref_number,c.email from enquiry e , customers c where e.salesdone=? and e.dispatch_done=? and e.cust_id=c.id and "+
															"STR_TO_DATE(`sales_date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
															"STR_TO_DATE(`sales_date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String NEW_DISPATCH = "INSERT INTO dispatch(reference_no,invoice_no,invoice_date,billing_name,shipping_to,transporter,dispatch_date,tracking_no,items,freight_mode,freight_amount,company_name,cust_email) VALUES(?,?,?,?,?,?,?,?,?, ?, ?, ?, ?)";
	public static final String NEW_DISPATCH_SUB = "UPDATE enquiry SET dispatch_done='Y' where ref_number=?";
	public static final String UPDATE_DISPATCH = "Update dispatch SET invoice_no=?,invoice_date=?,billing_name=?,shipping_to=?,transporter=?,dispatch_date=?,tracking_no=?,items=?,freight_mode=?,freight_amount=?, company_name=? where id=?";
	public static final String GET_PRODUCT_DISPATCHS = "SELECT * FROM DISPATCH";
	public static final String DELETE_DISPATCH = "DELETE FROM DISPATCH WHERE ID=?";
	public static final String DELETE_DISPATCH_SUB = "UPDATE enquiry SET dispatch_done='N' where ref_number=?";
	
	//Constants for EnquiryDAO
	
	public static final String SAVE_ENQUIRY = "INSERT INTO enquiry(cust_id,referedby,cust_req,purchase_period,cust_doc,priceestimation,quotationpreparation,emailsent,date,prod_name,salesdone,type, ref_number, prod_id, margin, pe_date,qp_date,mail_sent_date,sales_date,reminder_sent,total_revenue,customer_type,dispatch_done,purchase_order_no,product_quantity) VALUES(?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_ENQUIRY = "UPDATE ENQUIRY SET cust_id=?,referedby=?,cust_req=?,purchase_period=?,cust_doc=?,priceestimation=?,quotationpreparation=?,emailsent=?,date=?,prod_name=?,salesdone=?,type=?, ref_number=?, prod_id=? where ID=?";
	public static final String GET_ENQUIRYS = "SELECT * FROM ENQUIRY";
	public static final String DELETE_ENQUIRY = "DELETE FROM ENQUIRY WHERE ID=?";
	public static final String SAVE_ENQUIRY_LOCATION = "UPDATE STATIC_UTIL SET value=?, LAST_UPDATED=? where `key`=?";
	public static final String GET_LATEST_ENQUIRY_NO = "SELECT value from STATIC_UTIL where `key`=?";
	public static final String INCREASE_ENQUIRY_NO = "UPDATE STATIC_UTIL SET VALUE =?, LAST_UPDATED=? where `key` = ?";
	public static final String CAU_ENQUIRY_NO = "SELECT LAST_UPDATED from STATIC_UTIL where `key`=?";
	public static final String GET_COMPONENTS_FOR_ENQUIRY = "SELECT c.id, `name`, category, subcategory, vendor, model, `type`, size, costprice, enduserprice, dealerprice, quantity  FROM COMPONENTS c inner join enquiry_component pc on (c.id = pc.component_id) where pc.ENQUIRY_ID=?";

	//Constants for HelpDAO
	
	public static final String UPDATE_COMPANY_DETAILS = "UPDATE company_details SET name=?,description=?,address=?,contact_details=?,company_logo=?,home_logo=? where ID=?";
	public static final String GET_COMPANY_DETAILS = "SELECT * FROM company_details";
	public static final String SAVE_EMPLOYEE = "INSERT INTO employee(name,designation,mobileno,address,rating) VALUES(?, ?, ?, ?, ?)";
	public static final String GET_EMPLOYEES = "SELECT * FROM employee";
	public static final String UPDATE_EMPLOYEE = "UPDATE employee SET name=?,designation=?,mobileno=?,address=?,rating=? where ID=?";
	public static final String DELETE_EMPLOYEE = "DELETE FROM employee WHERE ID=?";
	
	//Constants for LoginDAO
	
	public static final String VERIFY_USER = "SELECT * FROM USERS WHERE USERNAME=? and PASSWORD=?";
	
	//constants for PriceEstimationDAO
	
	public static final String SAVE_PE = "UPDATE ENQUIRY SET priceestimation = 'Y', margin=?, pe_date=?,total_revenue=?,customer_type=? where id=? ";
	public static final String SAVE_PE_SUB = "INSERT into enquiry_component(enquiry_id, component_id, quantity) values(";
	public static final String UPDATE_PE = "DELETE FROM ENQUIRY_COMPONENT WHERE ENQUIRY_ID=?";
	public static final String UPDATE_PE_SUB = "UPDATE ENQUIRY SET MARGIN=?,total_revenue=?, customer_type=? WHERE ID=?";
	public static final String UPDATE_PE_SUB2 = "INSERT into enquiry_component(enquiry_id, component_id, quantity) values(";
	public static final String GET_PE = "SELECT * FROM ENQUIRY WHERE PRICEESTIMATION ='Y'";
	public static final String DELETE_PE = "DELETE FROM ENQUIRY_COMPONENT WHERE ENQUIRY_ID=?";
	public static final String DELETE_PE_SUB = "UPDATE ENQUIRY SET priceestimation=?, pe_date='N/A' WHERE ID=?";
	
	//constants for ProductsDAO
	
	public static final String SAVE_PRODUCT = "INSERT INTO products(name,category,subcategory,code, path_set) VALUES(?, ?, ?, ?,?)";
	public static final String SAVE_PRODUCT_SUB = "INSERT into product_component(product_id, component_id, quantity) values(";
	public static final String UPDATE_PRODUCT = "DELETE FROM PRODUCT_COMPONENT WHERE PRODUCT_ID=?";
	public static final String UPDATE_PRODUCT_SUB = "UPDATE PRODUCTS SET NAME=?, CATEGORY=?, SUBCATEGORY=?, CODE=? WHERE ID=?";
	public static final String UPDATE_PRODUCT_SUB2 = "INSERT into product_component(product_id, component_id, quantity) values(";
	public static final String GET_PRODUCTS = "SELECT * FROM PRODUCTS";
	public static final String DELETE_PRODUCT = "DELETE FROM product_component WHERE PRODUCT_ID=?";
	public static final String DELETE_PRODUCT_SUB = "DELETE FROM PRODUCTS WHERE ID=?";
	public static final String GET_COMPONENTS_FOR_PRODUCT = "SELECT c.id, `name`, category, subcategory, vendor, model, `type`, size, costprice, enduserprice, dealerprice, quantity  FROM COMPONENTS c inner join product_component pc on (c.id = pc.component_id) where pc.PRODUCT_ID=?";
	public static final String GET_CATEGORY_FOR_PRODUCT = "SELECT DISTINCT id, category FROM PRODUCTS";
	public static final String GET_SUBCATEGORY_FOR_PRODUCT = "SELECT DISTINCT id, subcategory FROM PRODUCTS";
	public static final String GET_CUSTOMET_TYPE_FOR_PRODUCT = "SELECT DISTINCT id, type FROM CUSTOMERS";
	public static final String GET_CUSTOMET_STATE_FOR_PRODUCT = "SELECT DISTINCT id, state FROM CUSTOMERS";
	public static final String GET_PRODUCT_CATEGORY_LIST = "SELECT distinct category FROM products";
	public static final String GET_PRODUCT_SUBCATEGORY_LIST = "SELECT distinct subcategory FROM products";
	public static final String GET_PRODUCT_NAME_LIST = "SELECT distinct name FROM products";
	
}
