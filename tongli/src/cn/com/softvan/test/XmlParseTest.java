package cn.com.softvan.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.jsoup.select.Elements;

public class XmlParseTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		getDocument(inputStream2String(new FileInputStream(new File("d:/test.xml"))));
	}
	/**
	 * InputStream --> String
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	/**
	 * 提取HTML或xnl文件的文本内容
	 * 
	 * @author Jeelon
	 * @param html
	 *            提取的html文件名
	 * @return 返回提取内容String
	 */
	private static void getDocument(String str) {
		try {
			// 设置编码集
			org.jsoup.nodes.Document doc = Jsoup.parse(str, "utf-8", new Parser(
					new XmlTreeBuilder()));
			// 提取标题信息
			Elements title = doc.select("root>item>title");
			for (org.jsoup.nodes.Element temp : title) {
				System.out.println(temp.text());
			}
			// 提取
			Elements links = doc.select("root>item>link");
			for (org.jsoup.nodes.Element temp : links) {
				System.out.println(temp.text());
			}
			// 提取
			Elements desc = doc.select("root>item>description");
			for (org.jsoup.nodes.Element temp : desc) {
				System.out.println(temp.text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
