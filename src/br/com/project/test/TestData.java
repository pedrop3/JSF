package br.com.project.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import br.com.project.report.util.DateUtils;

public class TestData {

	@Test
	public void testData() {

		try{
			assertEquals("15112017", DateUtils.getDateAtualReportName());
			
			
			assertEquals("'2017-11-15'", DateUtils.formatDateSql(Calendar.getInstance().getTime()));
			assertEquals("2017-11-15", DateUtils.formatDateSqlSimple(Calendar.getInstance().getTime()));

		}catch (Exception e) {
			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
