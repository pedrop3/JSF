package br.com.framework.implementacao.crud;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) //Quando transação for por spring será criado e caso tenha algum erro rollback irá matar
public class SimpleJdbcTempleImpl extends SimpleJdbcTemplate implements Serializable{

	private static final long serialVersionUID = 1L;

	public SimpleJdbcTempleImpl(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}

}
