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
	public static final String GET_PRODUCT_CODE_LIST = "SELECT distinct code FROM products";
	
	//constants for QuotationDAO
	
	public static final String GET_QUOTATION_PRODUCTS = "SELECT * FROM PRODUCTS where path_set='N'";
	public static final String SAVE_STANDARD_PATH = "INSERT INTO quotation(product_id,default_file,word_file_path,pdf_file_path) VALUES(?, ?, ?, ?)";
	public static final String SAVE_STANDARD_PATH_SUB = "UPDATE PRODUCTS SET path_set=? WHERE ID=?";
	public static final String UPDATE_QUOTATION_ENQUIRY = "UPDATE ENQUIRY SET quotationpreparation=?, QP_DATE=? where ID=?";
	public static final String GET_STANDARD_PATH = "SELECT * FROM quotation where product_id=?";
	public static final String GET_PRODUCT_PATH = "SELECT PATH_SET FROM PRODUCTS where id=?";
	public static final String UPDATE_EMAIL_STATUS = "UPDATE ENQUIRY SET emailsent=?, mail_sent_date=? where ID=?";
	public static final String GET_EMAIL_DETAILS = "SELECT `KEY`, VALUE FROM STATIC_UTIL";
	
	//constants for ReminderDAO
	
	public static final String CREATE_REMINDER = "INSERT INTO REMINDER(reference_no,total_reminder,frequency,last_sent,next_send,status,subject,message,reciever) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_REMINDER = "UPDATE Reminder SET total_reminder=?,frequency=?,last_sent=?,next_send=?,status=?,subject=?,message=?,reciever=? where reference_no=?";
	public static final String GET_REMINDERS = "SELECT * from REMINDER";
	public static final String GET_MODIFY_REMINDERS = "SELECT e.REF_NUMBER FROM ENQUIRY e ,REMINDER r WHERE e.REF_NUMBER = r.REFERENCE_NO and " +
														"STR_TO_DATE(e.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and " + 
														"STR_TO_DATE(e.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String GET_CREATE_REMINDERS = "SELECT ref_number, c.email FROM ENQUIRY e, customers c where e.cust_id=c.id and e.ref_number NOT IN (select r1.reference_no from reminder r1) and e.salesdone='N' and " +
														"STR_TO_DATE(e.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and " +
														"STR_TO_DATE(e.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String GET_STATUS_ENQUIRY = "select * from enquiry where salesdone=? and " +
														"STR_TO_DATE(`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and " + 
														"STR_TO_DATE(`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String DELETE_REMINDER = "DELETE FROM REMINDER WHERE reference_no=?";
	public static final String GET_REMINDERS_FOR_EMAIL = "SELECT * FROM reminder q where status='ON' and next_send=? and q.total_reminder>(select reminder_sent from enquiry e where e.ref_number = q.reference_no)";
	public static final String UPDATE_REMINDER_SENT = "UPDATE ENQUIRY SET REMINDER_SENT = REMINDER_SENT + 1 where REF_NUMBER=?";
	public static final String GET_REMINDER_SENT = "SELECT reminder_sent FROM enquiry where ref_number=?";
	
	//constants for ReportsDAO
	
	public static final String SALES_REPORT = "select ref_number,salesdone,prod_id,cust_id,total_revenue from enquiry e where STR_TO_DATE(e.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
													"STR_TO_DATE(e.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String GET_SERVICE_CHARGE = "SELECT charge FROM SERVICE where reference_no=?";
	public static final String GET_SERVICE_ENGINEER_DETAILS = "SELECT s.reference_no, s.date, c.customername, c.companyname, c.state, s.charge FROM SERVICE s, " + 
																"Customers c, enquiry e where e.ref_number=s.reference_no and e.cust_id=c.id and s.engineer_name=? and " +
																"STR_TO_DATE(s.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
																"STR_TO_DATE(s.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String GET_ENQUIRIES_FROM_REFNO = "SELECT e.ref_number, e.`date`, e.QP_DATE, e.sales_date, e.prod_name, "+
																"c.customername, c.companyname, c.state, e.referedby, count(s.id), e.total_revenue, "+ 
																"SUM(s.charge) FROM ENQUIRY e,"+
																"service s, customers c where e.ref_number=s.reference_no and s.date!='N/A' and e.cust_id=c.id and e.ref_number=?";
	public static final String GET_ENQUIRIES_FROM_REFEREDBY = "SELECT e.ref_number, e.`date`, e.QP_DATE, e.sales_date, e.prod_name, "+
																"c.customername, c.companyname, c.state, e.referedby, count(s.id), e.total_revenue, "+ 
																"SUM(s.charge) FROM ENQUIRY e,"+
																"service s, customers c where e.ref_number=s.reference_no and s.date!='N/A' and e.cust_id=c.id and e.referedby=?";
	public static final String GET_ENQUIRIES_FROM_CUST_NAME = "SELECT e.ref_number, e.`date`, e.QP_DATE, e.sales_date, e.prod_name, "+
																"c.customername, c.companyname, c.state, e.referedby, count(s.id), e.total_revenue, "+ 
																"SUM(s.charge) FROM ENQUIRY e,"+
																"service s, customers c where e.ref_number=s.reference_no and s.date!='N/A' and e.cust_id=c.id and c.customername=?";
	public static final String GET_ENQUIRIES_FROM_COMP_NAME = "SELECT e.ref_number, e.`date`, e.QP_DATE, e.sales_date, e.prod_name, "+
																"c.customername, c.companyname, c.state, e.referedby, count(s.id), e.total_revenue, "+ 
																"SUM(s.charge) FROM ENQUIRY e,"+
																"service s, customers c where e.ref_number=s.reference_no and s.date!='N/A' and e.cust_id=c.id and c.companyname=?";
	public static final String GET_REFNO = "SELECT ref_number FROM ENQUIRY";
	public static final String GET_REFEREDBY = "SELECT referedby FROM ENQUIRY";
	public static final String GET_COMPANY = "SELECT companyname FROM CUSTOMERS";
	public static final String GET_CUST_NAME = "SELECT customername FROM CUSTOMERS";
	
	//constants for SalesDAO
	
	public static final String GET_ENQUIRY_FOR_SALE = "SELECT * FROM ENQUIRY WHERE priceestimation='Y' AND quotationpreparation='Y' AND emailsent='Y' AND salesdone='N'";
	public static final String SAVE_SALES_DATE = "UPDATE ENQUIRY SET sales_date=?, salesdone=?,purchase_order_no=?,product_quantity=? where ID=?";
	public static final String SAVE_SALES_DATE_SUB = "UPDATE REMINDER SET status =? where reference_no=?";
	public static final String UPDATE_SALES_DATE = "UPDATE ENQUIRY SET sales_date=? where ID=?";
	public static final String DELETE_SALES = "UPDATE ENQUIRY SET sales_date=?, salesdone=?,purchase_order_no=?,product_quantity=? where ID=?";
	
	//constants for ServiceDAO
	
	public static final String NEW_COMPLAINT = "UPDATE customers SET complaint_count = complaint_count + 1 where id=?";
	public static final String NEW_COMPLAINT_SUB = "SELECT complaint_count FROM customers WHERE id = ?";
	public static final String NEW_COMPLAINT_SUB2 = "INSERT INTO service(reference_no,engineer_name,complaint,date,rating,charge,complaint_date,feedback,complaint_id,product_name,customer_id,customer_name,contact_no) VALUES(?,?,?,?,?,?,?, ?, ?, ?, ?, ?,?)";
	public static final String UPDATE_COMPLAINT = "UPDATE SERVICE SET complaint=?,complaint_date=? where id=?";
	public static final String GET_COMPLAINT_LIST = "select s.id,s.reference_no,s.complaint,s.complaint_date,s.complaint_id,s.product_name,s.customer_id,s.contact_no, c.service_count from service s, customers c where c.id=s.customer_id and s.date=?";
	public static final String DELETE_COMPLAINT = "DELETE FROM Service WHERE ID=?";
	public static final String NEW_SERVICE = "UPDATE SERVICE SET engineer_name=?,complaint=?,date=?,rating=?,charge=?,feedback=? where complaint_id=?";
	public static final String NEW_SERVICE_SUB = "UPDATE customers SET service_count = service_count + 1 where id=?";
	public static final String UPDATE_SERVICE = "UPDATE SERVICE SET engineer_name=?,complaint=?,date=?,rating=?,charge=?,feedback=? where complaint_id=?";
	public static final String GET_SERVICE_LIST = "select * from service where date!=? and "+
														"STR_TO_DATE(`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
														"STR_TO_DATE(`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String GET_SERVICE_DETATILS = "select * from SERVICE where date!=?";
	public static final String GET_SERVICE_HISTORY = "SELECT s.reference_no , s.product_name, s.complaint_date, s.date,s.contact_no, c.customername, c.companyname, c.state  FROM SERVICE s, "+
														"Customers c where s.customer_id=c.id and s.date!=? and "+
														"STR_TO_DATE(s.`date`, '%d/%m/%Y') >= STR_TO_DATE(?, '%d/%m/%Y') and "+
														"STR_TO_DATE(s.`date`, '%d/%m/%Y') <= STR_TO_DATE(?, '%d/%m/%Y')";
	public static final String GET_SERVICE_FOR_CONTACT = "SELECT c.service_count,(select count(*) from service where YEAR(STR_TO_DATE(`date`, '%d/%m/%Y'))=YEAR(NOW()) and contact_no=?), s.engineer_name,s.charge,s.rating FROM customers c ,service s where c.contactnumber=s.contact_no and s.date!='N/A' and c.contactnumber=?";
	public static final String DELETE_SERVICE = "UPDATE SERVICE SET engineer_name=?,date=?,rating=?,charge=?,feedback=? where complaint_id=?";
	public static final String DELETE_SERVICE_SUB = "UPDATE customers SET service_count = service_count - 1 where id=?";
	
	//constants for UserDAO
	
	public static final String SAVE_USER = "INSERT INTO users(name,designation,mobilenumber,username,password,quotation,priceestimation,report,salesorder,statusreminder,view,`edit`,`delete`,usertype,enquiry,product_dispatch,service_registry) VALUES(?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATE_USER = "UPDATE users SET name=?,designation=?,mobilenumber=?,username=?,password=?,quotation=?,priceestimation=?,report=?,salesorder=?,statusreminder=?,view=?,`edit`=?,`delete`=?,usertype=?,enquiry=?,product_dispatch=?,service_registry=? where ID=?";
	public static final String GET_USERS = "SELECT * FROM USERS";//Also For getModule
	public static final String DELETE_USER = "DELETE FROM USERS WHERE ID=?";
	
}
