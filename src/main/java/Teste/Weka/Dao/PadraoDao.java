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
 * Classe responsável pelo padrão dos DAOs do sistema.
 * 
 * @author Anderson Santos
 *
 * @param <T>
 */
public class PadraoDao<T extends PadraoDominio> {
	/**
	 * Fábrica de sessões.
	 */
	private SessionFactory fabricaSessoes;
	
	/**
	 * Sessão para operações;
	 */
	private Session sessao;
	
	/**
	 * Transação da sessão.
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
	 * Método usado para atualizar um objeto.
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
	 * Método usado para inserir um objeto.
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
	 * Método usado para remover um objeto.
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
     * Método usado para buscar um objeto pela chave primária.
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
	 * Método usado para obter a sessão atual.
	 * 
	 * @return
	 * @throws HibernateException
	 */
	private Session getNovaSessao() throws HibernateException {
    	return fabricaSessoes.getCurrentSession();
    }
	
	/**
	 * Método usado para iniciar uma operação com o banco de dados.
	 */
	private void iniciarOperacao() {
		sessao = getNovaSessao();
		transacao = sessao.beginTransaction();
	}
	
	/**
	 * Método usado para encerrar uma operação com o banco de dados.
	 */
	private void encerrarOperacao() {
		transacao.commit();
	}
}
