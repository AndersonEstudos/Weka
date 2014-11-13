package Teste.Weka.Principal;

import java.io.File;

import Teste.Weka.Mineração.ClassificarNominalAtt;
import Teste.Weka.Mineração.DadosMineracao;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.experiment.InstanceQuery;

public class TesteWeka
{		
	
	
	
	public void Weka() throws Exception
	{
		ClassificarNominalAtt classificar = new ClassificarNominalAtt();
		DadosMineracao dados = new DadosMineracao();
		
		dados.dadosBanco();
		dados.setAtributo();
		classificar.imprimeArvore(dados.getDados());
		classificar.classificaInstancia(dados.dadosBanco());
	}
	public  void WekaTransf() throws Exception
	{
		


		// instancia uma conexão com o banco
		//e seleciona todas as tabelas de uma relação
	    InstanceQuery query = new InstanceQuery();
		query.connectToDatabase();
		query.setQuery("select * from previsaotempo");
		Instances data = query.retrieveInstances();
		 
       //instancia um arquivo csv
		File file = new File("C:\\Users\\Anderson Araujo\\Desktop\\previsao.csv");
		CSVLoader loader = new CSVLoader();
		loader.setSource(file);
	    Instances teste = loader.getDataSet(); 
	    
	    //seta o numero de atributos, e seleciona o atributo a ser classificado
	    teste.setClassIndex(teste.numAttributes()-1);
		teste.setClass(data.attribute("choveu"));
			 
		data.setClassIndex(data.numAttributes()-1);
		data.setClass(data.attribute("choveu"));
		
		
		//String[] options = new String[2];
		//options[0] = new String("C");
		//options[1] = new String("M");
		J48 tree = new J48();
		//tree.setOptions(options);
		tree.buildClassifier(data);
		
		 System.out.println(tree.toString());
		// cria uma classificação usando o algoritmo J48
		Classifier cls = new J48();
		cls.buildClassifier(data);
		 //System.out.println(teste);
		 
		// cria uma avaliação usando a classificação criada como base
		//e com isso avalia as intancias conforme "aprendeu" com a classificação
		
		
		
		Instance instancia = new Instance(4);
		
		instancia.setDataset(data);		
		instancia.setValue(1, "true");
		instancia.setValue(2, 29);
		instancia.setValue(3, 55);
	
		if (tree.classifyInstance(instancia) == 1)
		{
			System.out.println("Choveu");
		}
		
		else{System.out.println("não choveu");}
		
		Evaluation eval = new Evaluation(data);
		eval.evaluateModelOnce(cls, instancia);
		 
		 System.out.println(eval.toSummaryString("\nResults\n\n", false));
		 
		 eval.evaluateModel(cls, teste);
		 System.out.println(eval.toSummaryString("\nResults\n\n", false));
		 
		
	}
}
