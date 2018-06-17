package compactador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import compactadorArquivo.*;

public class Compactador
{
	public static final String STR_FINAL_ARQ = "-[Compacted]";
	
	//compactar
	public static void compactar(File arquivo) throws Exception
	{
		if (arquivo == null)
			throw new FileNotFoundException("Arquivo nulo");
		
		if(!arquivo.isDirectory())
		{
			CompactadorArquivo compactadorArq = new CompactadorArquivo();
			compactadorArq.compactar(arquivo);
		}else
		{
			String diretorio = arquivo.getCanonicalPath() + STR_FINAL_ARQ;
			
			String nomeArq = diretorio;
			int n = 1;
			while (new File(nomeArq).exists()) {
				//se jah existe esse arquivo criar outro com " (1)" na frente
				nomeArq = diretorio + " (" + n + ")";
				n++;
			}
			
			Compactador.compactarAux(arquivo, nomeArq);
		}
	}
	
	public static void compactar(String nomeArquivo) throws Exception
	{
		if(!new File(nomeArquivo).exists())
			throw new FileNotFoundException("Arquivo inexistente!");

		Compactador.compactar(new File(nomeArquivo));
	}
	
	protected static void compactarAux(File arquivo, String nomeArq)
	{
		if(!arquivo.isDirectory())
		{
			CompactadorArquivo compactadorArq = new CompactadorArquivo();

			try
			{
				compactadorArq.compactar(arquivo, nomeArq);
			}catch (Exception e) {e.printStackTrace();}
		}else
		{
			for (final File arq : arquivo.listFiles())
			{
				try {
					boolean worked = new File(nomeArq).mkdirs();
					String path = arq.getCanonicalPath();
					int indexUltimoPonto = path.lastIndexOf(".");
					String nomeArquivo;
					if(indexUltimoPonto < 0)
						nomeArquivo = nomeArq + path.substring(path.lastIndexOf("\\"));
					else
						nomeArquivo = nomeArq + path.substring(path.lastIndexOf("\\"), indexUltimoPonto);
					Compactador.compactarAux(arq, nomeArquivo);
				} catch (IOException e) {e.printStackTrace();}
		    }
		}
	}
	
	//compactar
	public static void descompactar(File arquivo) throws Exception
	{
		if (arquivo == null)
			throw new FileNotFoundException("Arquivo nulo");
		
		if(!arquivo.isDirectory())
		{
			CompactadorArquivo compactadorArq = new CompactadorArquivo();
			compactadorArq.descompactar(arquivo);
		}else
		{
			String diretorio = arquivo.getCanonicalPath();

			int indComp = diretorio.indexOf(STR_FINAL_ARQ);
			if(indComp < 0)
				throw new Exception("Essa pasta ainda n�o foi compactada!");

			diretorio = diretorio.substring(0, indComp);

			String nomeArq = diretorio;
			int n = 1;
			while (new File(nomeArq).exists()) {
				//se jah existe esse arquivo criar outro com " (1)" na frente
				nomeArq = diretorio + " (" + n + ")";
				n++;
			}

			Compactador.descompactarAux(arquivo, nomeArq);
		}
	}
	
	public static void descompactar(String nomeArquivo) throws Exception
	{
		if(!new File(nomeArquivo).exists())
			throw new FileNotFoundException("Arquivo inexistente!");

		Compactador.descompactar(new File(nomeArquivo));
	}
	
	protected static void descompactarAux(File arquivo, String nomeArq)
	{
		if(!arquivo.isDirectory())
		{
			CompactadorArquivo compactadorArq = new CompactadorArquivo();

			try
			{
				compactadorArq.descompactar(arquivo, nomeArq);
			}catch (Exception e) {e.printStackTrace();}
		}else
		{
			for (final File arq : arquivo.listFiles())
			{
				try {
					boolean worked = new File(nomeArq).mkdirs();
					String path = arq.getCanonicalPath();
					int indexUltimoPonto = path.lastIndexOf(".");
					String nomeArquivo;
					if(indexUltimoPonto < 0)
						nomeArquivo = nomeArq + path.substring(path.lastIndexOf("\\"));
					else
						nomeArquivo = nomeArq + path.substring(path.lastIndexOf("\\"), indexUltimoPonto);
					Compactador.descompactarAux(arq, nomeArquivo);
				} catch (IOException e) {e.printStackTrace();}
		    }
		}
	}
}