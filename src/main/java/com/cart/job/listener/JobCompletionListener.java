package com.cart.job.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cart.entity.Product;


@Component
public class JobCompletionListener implements JobExecutionListener {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("Executing job id " + jobExecution.getId());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
	        List<Product> result = 
	        	jdbcTemplate.query("SELECT id, id_brand, name, price, stock_quantity, status, discount_percentage, creation_date, update_date FROM product", 
	        	new RowMapper<Product>() {
		            @Override
		            public Product mapRow(ResultSet rs, int row) throws SQLException {
		                	                
		                Product product = new Product();
		                product.setId( rs.getLong(1) );
		        		product.setIdBrand( rs.getInt(2) );
		        		product.setName( rs.getString(3) );
		        		product.setPrice( rs.getInt(4) );
		        		product.setStockQuantity( rs.getInt(5) );
		        		product.setStatus( rs.getString(6) );
		        		product.setDiscountPercentage( rs.getInt(7) );
		        		product.setCreationDate( rs.getDate(8) );
		        		product.setUpdateDate( rs.getDate(9) );
		        		return product;
		            }
	        });
	        System.out.println("Number of Records:"+result.size());
		}
	}
}