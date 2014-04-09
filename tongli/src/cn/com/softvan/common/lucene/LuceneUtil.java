/*
 * 
 * lucene 操作 工具类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.08  wuxiaogang 	             程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.common.lucene;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * lucene 操作 工具类
 * @author wuxiaogang 
 *
 */
public class LuceneUtil {
	/**
	 * 创建索引
	 * @param path  索引文件保存路径
	 * @param fields  索引字段集合
	 * @param texts 索引字段值集合
	 * @param analyzer 索引分词器 
	 * @param stores 字段索引持久化参数集合
	 *  1,Field.Store.YES:索引时会被持久化,查找时可以查到相应的值
	 *  2,Field.Store.NO:索引时不被持久化.不会被存储
	 * @param indexs 字段是否要被分词参数集合 
	 *  1,Field.Index.ANALYZED:所传字段会被分词,会根据分词后进行查找
	 *	2,Field.Index.NOT_ANALYZED:所传字段不会被分词,会根据(原值)进行查找
	 *	3,Field.Index.NO:不参与分词,也不参与查找
	 * @throws IOException 
	 * @throws LockObtainFailedException 
	 * @throws CorruptIndexException 
	 */
	public static void creatIndex(String path,String[] fields,String[] texts,Analyzer  analyzer, Store[] stores,Index[] indexs) throws CorruptIndexException, LockObtainFailedException, IOException{
			if(fields==null){
				throw new IllegalArgumentException("parameter \"fields\" is null");
			}
			if(texts==null){
				throw new IllegalArgumentException("parameter \"texts\" is null");
			}
			if(stores==null){
				throw new IllegalArgumentException("parameter \"stores\" is null");
			}
			if(indexs==null){
				throw new IllegalArgumentException("parameter \"indexs\" is null");
			}
			if (!(fields.length == texts.length && fields.length == stores.length  && stores.length == indexs.length)){  
	            throw new IllegalArgumentException("fields,texts,stores and indexs array have have different length");  
	        }
			Document doc = new Document();
			for(int i=0;i<fields.length;i++){
				doc.add(new Field(fields[i],texts[i], stores[i],indexs[i]));
			}
			if(analyzer==null){
				analyzer=new IKAnalyzer();
			}
			
			Directory dir = new SimpleFSDirectory(new File(path));
			boolean exist = IndexReader.indexExists(dir);
			IndexWriter writer = new IndexWriter(dir, analyzer, !exist, IndexWriter.MaxFieldLength.LIMITED);
//			writer.setMaxFieldLength(3);
			writer.addDocument(doc);
			writer.optimize();//添加完所有document，我们对索引进行优化，优化主要是将多个索引文件合并到一个，有利于提高索引速度。 
			writer.close();//随后将writer关闭，这点很重要。
	}
	/**
	 * 创建索引
	 * @param writer  索引文件对象
	 * @param fields  索引字段集合
	 * @param texts 索引字段值集合
	 * @param stores 字段索引持久化参数集合
	 *  1,Field.Store.YES:索引时会被持久化,查找时可以查到相应的值
	 *  2,Field.Store.NO:索引时不被持久化.不会被存储
	 * @param indexs 字段是否要被分词参数集合 
	 *  1,Field.Index.ANALYZED:所传字段会被分词,会根据分词后进行查找
	 *	2,Field.Index.NOT_ANALYZED:所传字段不会被分词,会根据(原值)进行查找
	 *	3,Field.Index.NO:不参与分词,也不参与查找
	 * @throws IOException 
	 * @throws LockObtainFailedException 
	 * @throws CorruptIndexException 
	 */
	public static void creatIndex(IndexWriter writer,String[] fields,String[] texts, Store[] stores,Index[] indexs) throws CorruptIndexException, LockObtainFailedException, IOException{
			if(fields==null){
				throw new IllegalArgumentException("parameter \"fields\" is null");
			}
			if(texts==null){
				throw new IllegalArgumentException("parameter \"texts\" is null");
			}
			if(stores==null){
				throw new IllegalArgumentException("parameter \"stores\" is null");
			}
			if(indexs==null){
				throw new IllegalArgumentException("parameter \"indexs\" is null");
			}
			if (!(fields.length == texts.length && fields.length == stores.length  && stores.length == indexs.length)){  
	            throw new IllegalArgumentException("fields,texts,stores and indexs array have have different length");  
	        }
			Document doc = new Document();
			for(int i=0;i<fields.length;i++){
				doc.add(new Field(fields[i],texts[i], stores[i],indexs[i]));
			}
//			writer.setMaxFieldLength(3);
			writer.addDocument(doc);
	}
	/**
	 * 删除索引
	 * @param field
	 * @param text
	 * @param writer
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void delete(String path,String field,String text) throws CorruptIndexException, IOException, ParseException {
		Directory dir = new SimpleFSDirectory(new File(path));
		boolean exist = IndexReader.indexExists(dir);
		if (exist) {
			IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_29), !exist, IndexWriter.MaxFieldLength.LIMITED);
			delete(field, text, writer);
			writer.close();
		}
	}
	/**
	 * 删除索引
	 * @param field
	 * @param text
	 * @param writer
	 * @throws CorruptIndexException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void delete(String field,String text, IndexWriter writer) throws CorruptIndexException, IOException, ParseException {
		writer.deleteDocuments(new Term(field,text));
	}
    /** 
     * 分词及打印分词结果的方法 
     * @param analyzer     分词器名称 
     * @param text         要分词的字符串 
     * @throws IOException 抛出的异常 
     */  
    public static void showToken(Analyzer analyzer, String text) throws IOException {  
        Reader reader = new StringReader(text);  
        if(analyzer==null){
        	analyzer=new IKAnalyzer();
        }
        TokenStream stream = (TokenStream)analyzer.tokenStream("", reader);  
        //添加工具类  注意：以下这些与之前lucene2.x版本不同的地方  
        TermAttribute termAtt  = (TermAttribute)stream.addAttribute(TermAttribute.class);  
        OffsetAttribute offAtt  = (OffsetAttribute)stream.addAttribute(OffsetAttribute.class);  
        // 循环打印出分词的结果，及分词出现的位置  
        while(stream.incrementToken()){  
            System.out.print(termAtt.term() + "|("+ offAtt.startOffset() + " " + offAtt.endOffset()+")");   
        }  
    }
    /** 
     * 分词及打印分词结果的方法 
     * @param analyzer     分词器名称 
     * @param text         要分词的字符串 
     * @throws IOException 抛出的异常 
     */  
    public static List<String> getKeys(Analyzer analyzer, String text) throws IOException {  
        List<String> keys=new ArrayList<String>();
    	Reader reader = new StringReader(text);  
        if(analyzer==null){
        	analyzer=new IKAnalyzer();
        }
        TokenStream stream = (TokenStream)analyzer.tokenStream("", reader);  
        //添加工具类  注意：以下这些与之前lucene2.x版本不同的地方  
        TermAttribute termAtt  = (TermAttribute)stream.addAttribute(TermAttribute.class);  
//        OffsetAttribute offAtt  = (OffsetAttribute)stream.addAttribute(OffsetAttribute.class);  
        // 循环打印出分词的结果，及分词出现的位置  
        while(stream.incrementToken()){
            //System.out.print(termAtt.term() + "|("+ offAtt.startOffset() + " " + offAtt.endOffset()+")");   
        	keys.add(termAtt.term());
        }  
        return keys;
    }
    /**
     * 初始化IndexWriter对象
     * 
     * @param path 索引库路径
     * @return
     */
    public static IndexWriter getIndexWriter(String path){
    	IndexWriter writer=null;
        try {
            boolean exist = IndexReader.indexExists(path);
            writer =new IndexWriter(path, new StandardAnalyzer(Version.LUCENE_29), !exist, IndexWriter.MaxFieldLength.LIMITED);
        } catch (Exception e) {
            e.printStackTrace();
        }  
        return writer;
    }
    
    /**
     * 初始化IndexSearcher对象
     * 
     * @param path 索引库路径
     * @return
     */
    public static IndexSearcher getIndexSearcher(String path){
    	IndexSearcher searcher=null;
        try {
            IndexReader reader = IndexReader.open(FSDirectory.open(new File(path)), true);
            searcher = new IndexSearcher(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return searcher;
    }
}
