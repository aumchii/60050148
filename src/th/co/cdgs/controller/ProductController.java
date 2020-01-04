package th.co.cdgs.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import th.co.cdgs.bean.ProductDto;

public class ProductController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getProduct() {
		List<ProductDto> list = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement pst =null;
		Connection conn =null;
		
		try {
			 conn = DriverManager.getConnection(
					 "jdbc:mysql://localhost:3306/workshop", "root", "p@ssw0rd");
			 pst = conn.prepareStatement(
					 "SELECT product_name,product_desc,price FROM workshop.product"+" WHERE active ='y'");
			 rs = pst.executeQuery();
			 ProductDto productDto = null;
			 
			 while(rs.next()) {
				 productDto = new ProductDto();
				 productDto.setProductId(rs.getLong("product_id"));
				 productDto.setProductName(rs.getString("product_name"));
				 productDto.setProductDesc(rs.getString("product_desc"));
				 productDto.setPrice(rs.getBigDecimal("price"));
				 productDto.setActive(rs.getString("active"));
				 list.add(productDto);
			 }
	   }
		catch (SQLException e) {
			e.printStackTrace();
		
		//close connection 
		}finally {
			//cos at the begin we set rs= null
			if (rs != null) {
				
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {	
				}
			}
		}
		return Response.ok().entity(list).build();

    }
}
