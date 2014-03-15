/*
 * html解析  
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.07.11  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.AppletTag;
import org.htmlparser.tags.BaseHrefTag;
import org.htmlparser.tags.BodyTag;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.DefinitionList;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.DoctypeTag;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.FrameSetTag;
import org.htmlparser.tags.FrameTag;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.Html;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.JspTag;
import org.htmlparser.tags.LabelTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ObjectTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.ProcessingInstructionTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.StyleTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;

/**
 * <p>
 * html解析 多线程处理。
 * </p>
 * <ol>
 * [功能概要] <div>html解析 。</div>
 * </ol>
 * 
 * @author wuxiaogang
 */
public class HtmlUtil{
	/**
	 * 根据条件获取html过滤对象
	 * 
	 * @param tag
	 * @param pro_name
	 * @param pro_value
	 * @return
	 */
	public static NodeFilter geNodeFilter2(String tag, String pro_name,
			String pro_value) {
		NodeFilter nf = null;
		if (Validator.notEmpty(tag) && Validator.notEmpty(pro_name)) {
			nf = new AndFilter(new TagNameFilter(tag), new HasAttributeFilter(
					pro_name, pro_value));
		} else if (Validator.notEmpty(tag)) {
			nf = new TagNameFilter(tag);
		}
		return nf;
	}

	/**
	 * 获取信息的内容
	 * 
	 * @param filter
	 * @param parser
	 * @return info 网址信息内容
	 */
	public static String getFilterHtmlInfo(NodeFilter filter, Parser parser) {
		if (filter == null) {
			return null;
		}
		String info = null;
		try {
			// TODO 获取签的值
			StringBuffer data = new StringBuffer("");
			NodeList nodeList = (NodeList) parser.parse(filter);
			for (int i = 0; i < nodeList.size(); i++) {
				data.append(getNodeText(false,nodeList.elementAt(i)));
			}
			info = data.toString();

		} catch (Exception e) {
		}
		parser.reset();// 记得每次用完parser后，要重置一次parser。要不然就得不到我们想要的内容了。
		return info;
	}

	/**
	 * 获取标签节点的值
	 * 
	 * @param is_self
	 *            是否抓取标签本身false否true是
	 * @param node
	 * @return
	 */
	public static String getNodeText(boolean is_self, Node node) {
		String infoText = null;
		// TODO--每种标签都试一下---
		try {
			Div xx = (Div) node;
			if (is_self) {
				infoText = xx.toHtml();
			} else {
				infoText = xx.getStringText();
			}
		} catch (Exception e1) {
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				AppletTag xx = (AppletTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				BaseHrefTag xx = (BaseHrefTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				BodyTag xx = (BodyTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				Bullet xx = (Bullet) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				BulletList xx = (BulletList) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				CompositeTag xx = (CompositeTag) node;
				infoText = xx.getStringText();
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				DefinitionList xx = (DefinitionList) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				DefinitionListBullet xx = (DefinitionListBullet) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				DoctypeTag xx = (DoctypeTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				FormTag xx = (FormTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				FrameSetTag xx = (FrameSetTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				FrameTag xx = (FrameTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				HeadingTag xx = (HeadingTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				HeadTag xx = (HeadTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				Html xx = (Html) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				ImageTag xx = (ImageTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getImageURL();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				InputTag xx = (InputTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				JspTag xx = (JspTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				LabelTag xx = (LabelTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				LinkTag xx = (LinkTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				MetaTag xx = (MetaTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				ObjectTag xx = (ObjectTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				OptionTag xx = (OptionTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				ParagraphTag xx = (ParagraphTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				ProcessingInstructionTag xx = (ProcessingInstructionTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				ScriptTag xx = (ScriptTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				SelectTag xx = (SelectTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				Span xx = (Span) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				StyleTag xx = (StyleTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				TableColumn xx = (TableColumn) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				TableHeader xx = (TableHeader) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				TableRow xx = (TableRow) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				TableTag xx = (TableTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				TextareaTag xx = (TextareaTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (Validator.isEmpty(infoText)) {
			try {
				TitleTag xx = (TitleTag) node;
				if (is_self) {
					infoText = xx.toHtml();
				} else {
					infoText = xx.getStringText();
				}
			} catch (Exception e) {
			}
		}
		// -----------------
		if (infoText == null) {
			infoText = "";
		}
		return infoText;
	}

	/**
	 * 获取域名
	 * 
	 * @param url
	 * @return
	 */
	public String getDomain(String url) {
		String reg = "(?<=http\\://[a-zA-Z0-9]{0,100}[.]{0,1})[^.\\s]*?\\.(com|cn|net|org|biz|info|cc|tv)";
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(url);
		boolean blnp = m.find();
		if (blnp == true) {
			return m.group(0);
		}
		return null;
	}

	/**
	 *  test
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String str="<script id=\"json-friendList\" type=\"json/text\">\"nickName\": \"<span class=\"emoji emoji1f388\"></span>『洋少』<span class=\"emoji emoji1f48b\"></span>\",</script>";
		
		NodeFilter filter= geNodeFilter2("script", "id", "json-friendList");
		Parser parser=new Parser();
		parser.setInputHTML(str);
		
		System.out.println(getFilterHtmlInfo(filter, parser));
		
	}

}
