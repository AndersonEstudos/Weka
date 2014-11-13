package Teste.Weka.Processador;


import org.hibernate.HibernateException;

import Teste.Weka.Dao.PrevisaoTempoDao;
import Teste.Weka.Dominio.PrevisaoTempo;



public class ProcessadorPrevisaoTempo {
	
	private PrevisaoTempoDao previsaoDAO;
	
	
	public ProcessadorPrevisaoTempo() {
		previsaoDAO = new PrevisaoTempoDao();
	}
	
	
	public boolean atualizar(PrevisaoTempo previsao) throws HibernateException {
		return previsaoDAO.atualizar(previsao);
	}
	
	
	public PrevisaoTempo buscarPorId(long id) throws HibernateException, IndexOutOfBoundsException {
		return previsaoDAO.buscarPorId(id);
	}
	
	
	public boolean inserir(PrevisaoTempo previsao) throws HibernateException {
		return previsaoDAO.inserir(previsao);
	}
	
	
	public boolean remover(PrevisaoTempo previsao) throws HibernateException {
		return previsaoDAO.remover(previsao);
	}
	
}
