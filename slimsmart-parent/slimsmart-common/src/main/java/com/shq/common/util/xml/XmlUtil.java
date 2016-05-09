package com.shq.common.util.xml;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.shq.common.util.string.StringUtil;
import com.thoughtworks.xstream.XStream;

@SuppressWarnings("rawtypes")
public class XmlUtil {

	/**
	 * 解析发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());
		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}
	
	 /**
     * java 转换成xml
     * @Title: toXml 
     * @param obj 对象实例
     * @return String xml字符串
     */
    public static String toXml(Object obj){
        ////如果没有这句，xml中的根元素会是<包.类名>；或者说：注解根本就没生效，所以的元素名就是类的属性
    	XStream xstream= new XStream();
    	xstream.processAnnotations(obj.getClass()); //通过注解方式的，一定要有这句话
        return xstream.toXML(obj);
    }
    /**
     *  将传入xml文本转换成Java对象
     * @Title: toBean 
     * @param xmlStr
     * @param cls  xml对应的class类
     * @return T   xml对应的class类的实例对象
     * 
     * 调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
     */
    @SuppressWarnings("unchecked")
	public static <T> T  toBean(String xmlStr,Class<T> cls){
    	XStream xstream= new XStream();
        xstream.processAnnotations(cls);
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;         
    } 

    
    public static String mapToXml(String root,Map<String,Object> paramsMap) {  
    	StringBuffer xml = new StringBuffer();
    	xml.append("<").append(root).append(">");
    	for(Entry<String, Object> entry : paramsMap.entrySet()){
    		xml.append("<").append(entry.getKey()).append(">");
    		if(entry.getValue()!=null && StringUtil.isNotBlank(entry.getValue().toString())){
    			xml.append("<![CDATA[");
        		xml.append(entry.getValue());
        		xml.append("]]>");
    		}else{
    			xml.append(entry.getValue());
    		}
    		xml.append("</").append(entry.getKey()).append(">");
    	}
    	xml.append("</").append(root).append(">");
    	return xml.toString();
    }
    
    
    public static String getRootAttrValue(String xml,String attr){
    	 try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();  
			return root.attribute(attr).getText();
		} catch (Exception e) {
		}
    	return "";
    }
    
    
    public static String addRootAttr(String xml,String key,String value){
   	 try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();  
			root.addAttribute(key,value);
			return root.asXML();
		} catch (Exception e) {
		}
   	 return xml;
   }
    
	public static Map<String,String> getRootElement2Map(String xml){
   	 try {
   		 	Map<String,String> map = new HashMap<String, String>();
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement(); 
			for(Iterator it=root.elementIterator();it.hasNext();){
				Element element = (Element) it.next();
				map.put(element.getName(), element.getText());
			}
			return map;
		} catch (Exception e) {
		}
   	 	return null;
   }
    
    public static String formatXml(String str,String charset){
    	  Document document = null;
    	  StringWriter writer = new StringWriter();
    	  try {
			document = DocumentHelper.parseText(str);
			// 格式化输出格式
	    	  OutputFormat format = OutputFormat.createPrettyPrint();
	    	  format.setEncoding(charset);
	    	  // 格式化输出流
	    	  XMLWriter xmlWriter = new XMLWriter(writer, format);
	    	  // 将document写入到输出流
	    	  xmlWriter.write(document);
	    	  xmlWriter.close();
		} catch (Exception e) {
		}
    	return writer.toString().length() > 0 ? writer.toString()  : str ;
    }
}
