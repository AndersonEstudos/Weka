package Teste.Weka.Mineração;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.experiment.InstanceQuery;

public class DadosMineracao 
{	
	
	private Instances dados;
	public DadosMineracao()
	{
		
	}
	
	public Instances dadosBanco() throws Exception
	{
		InstanceQuery query = new InstanceQuery();
		query.connectToDatabase();
		query.setQuery("select * from previsaotempo");
		dados = query.retrieveInstances();
		dados.setClassIndex(dados.numAttributes() - 1);
		return dados;
	}
	
	public Instances dadosCSV() throws IOException
	{

	       
			File file = new File("C:\\Users\\Anderson Araujo\\Desktop\\previsao.csv");
			CSVLoader loader = new CSVLoader();
			loader.setSource(file);
		    dados = loader.getDataSet();
		    dados.setClassIndex(dados.numAttributes() - 1);
		    return dados;
	}
	public Instances dadosCSV(String caminho) throws IOException
	{

	       
			File file = new File(caminho);
			CSVLoader loader = new CSVLoader();
			loader.setSource(file);
		    dados = loader.getDataSet();
		    dados.setClassIndex(dados.numAttributes() - 1);
		    return dados;
	}
	
	public Instances getDados() 
	{
		return dados;
	}
	
	public void setAtributo()
	{
		dados.setClass(dados.attribute("choveu"));
	}
}
