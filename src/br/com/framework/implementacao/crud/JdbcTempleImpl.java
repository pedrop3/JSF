package br.com.framework.implementacao.crud;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) //Quando transa��o for por spring ser� criado e caso tenha algum erro rollback ir� matar
public class JdbcTempleImpl extends JdbcTemplate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public JdbcTempleImpl(DataSource dataSource) {
		super(dataSource);
	}

}
