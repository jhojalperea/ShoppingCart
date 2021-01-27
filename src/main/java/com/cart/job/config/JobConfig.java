package com.cart.job.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cart.entity.Product;
import com.cart.exception.ProductCsvException;
import com.cart.job.listener.JobCompletionListener;
import com.cart.job.processor.ProductItemProcessor;
import com.cart.model.ProductCsv;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class JobConfig {

	@Autowired
	private  JobBuilderFactory jobBuilderFactory;

	@Autowired
	private  StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private ProductItemProcessor productItemProcessor;

	@Bean
	public LineMapper<ProductCsv> lineMapper() {		
		
		DefaultLineMapper<ProductCsv> lineMapper = new DefaultLineMapper<ProductCsv>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "name", "brand", "price", "stockQuantity", "status", "discountPercentage" });
                
        BeanWrapperFieldSetMapper<ProductCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<ProductCsv>();
        fieldSetMapper.setTargetType(ProductCsv.class);
        
		lineMapper.setFieldSetMapper( fieldSetMapper );
		lineMapper.setLineTokenizer(lineTokenizer);
		return lineMapper;
	}

	@Bean
	public FlatFileItemReader<ProductCsv> reader() {
		return new FlatFileItemReaderBuilder<ProductCsv>().name("productItemReader")
			.resource(new ClassPathResource("Productos.csv"))
			.lineMapper(lineMapper())
			.linesToSkip(1)
			.build();
	}

	@Bean
	public JdbcBatchItemWriter<Product> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Product>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>())
				.sql("INSERT INTO product( id_brand, name, price, stock_quantity, status, discount_percentage, creation_date ) "
						+ "VALUES ( :idBrand, :name, :price, :stockQuantity, :status, :discountPercentage, CURRENT_TIMESTAMP() )")
				.dataSource(dataSource).build();
	}

	@Bean
	public ItemProcessor<ProductCsv, Product> processor() {
		return productItemProcessor;
	}

	@Bean
	public Job createProductJob(JobCompletionListener listener, Step step1) {
		
		return jobBuilderFactory.get("createProductJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)				
				.end()
				.build();
	}

	@Bean
	public Step step1(ItemReader<ProductCsv> reader, ItemWriter<Product> writer, ItemProcessor<ProductCsv, Product> processor) {
		
		return stepBuilderFactory
				.get("step1")
				.<ProductCsv, Product>chunk(5)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.faultTolerant()
				.skip(ProductCsvException.class)
				.skipLimit(999)
				.build();
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

}
