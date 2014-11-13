package Teste.Weka.Dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import Teste.Weka.Dominio.PadraoDominio;


/**
 * Classe respons�vel pelo padr�o dos DAOs do sistema.
 * 
 * @author Anderson Santos
 *
 * @param <T>
 */
public class PadraoDao<T extends PadraoDominio> {
	/**
	 * F�brica de sess�es.
	 */
	private SessionFactory fabricaSessoes;
	
	/**
	 * Sess�o para opera��es;
	 */
	private Session sessao;
	
	/**
	 * Transa��o da sess�o.
	 */
	private Transaction transacao;
	
	/**
	 * Construtor vazio.
	 */
	public PadraoDao() {
		Configuration configuracao = new Configuration();
		configuracao.configure("hibernate.cfg.xml");
		
		ServiceRegistry registroServico = new ServiceRegistryBuilder().applySettings(configuracao.getProperties()).buildServiceRegistry();
		
		fabricaSessoes = configuracao.buildSessionFactory(registroServico);
	}
	
	/**
	 * Encerra o DAO.
	 */
	public void fechar() {
		fabricaSessoes.close();
	}
	
	/**
	 * M�todo usado para atualizar um objeto.
	 * 
	 * @param objeto
	 * @return
	 * @throws HibernateException
	 */
	public boolean atualizar(T objeto) throws HibernateException {
		iniciarOperacao();
		sessao.merge(objeto);
		encerrarOperacao();
		
		return true;
	}
	
	/**
	 * M�todo usado para inserir um objeto.
	 * 
	 * @param objeto
	 * @return
	 * @throws HibernateException
	 */
	public boolean inserir(T objeto) throws HibernateException {
		iniciarOperacao();
		sessao.persist(objeto);
		encerrarOperacao();
		
		return true;
	}
	
	/**
	 * M�todo usado para remover um objeto.
	 * 
	 * @param objeto
	 * @return
	 * @throws HibernateException
	 */
	public boolean remover(T objeto) throws HibernateException {
		iniciarOperacao();
		sessao.delete(objeto);
		encerrarOperacao();
		
		return true;
	}
	
	/**
     * M�todo usado para buscar um objeto pela chave prim�ria.
     * 
     * @param id
     * @param classe
     * @return
	 * @throws HibernateException
	 * @throws IndexOutOfBoundsException
     */
	public T buscarPorIdClasse(long id, Class<T> classe) throws HibernateException, IndexOutOfBoundsException {
		iniciarOperacao();
		
		StringBuilder textoConsulta = new StringBuilder();
		textoConsulta.append("SELECT objeto FROM ");
		textoConsulta.append(classe.getSimpleName());
		textoConsulta.append(" objeto WHERE id = ");
		textoConsulta.append(id);
		
		Query consulta = (Query) sessao.createQuery(textoConsulta.toString());
		@SuppressWarnings("unchecked")
		T resultado = (T) consulta.uniqueResult();
		encerrarOperacao();
		
		return resultado;
	}
	
	/**
	 * M�todo usado para obter a sess�o atual.
	 * 
	 * @return
	 * @throws HibernateException
	 */
	private Session getNovaSessao() throws HibernateException {
    	return fabricaSessoes.getCurrentSession();
    }
	
	/**
	 * M�todo usado para iniciar uma opera��o com o banco de dados.
	 */
	private void iniciarOperacao() {
		sessao = getNovaSessao();
		transacao = sessao.beginTransaction();
	}
	
	/**
	 * M�todo usado para encerrar uma opera��o com o banco de dados.
	 */
	private void encerrarOperacao() {
		transacao.commit();
	}
}
