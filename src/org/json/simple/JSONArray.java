/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A JSON array. JSONObject supports java.util.List interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONArray extends ArrayList implements List, JSONAware, JSONStreamAware {
	private static final long serialVersionUID = 3957988303675231981L;

    /**
     * Encode a list into JSON text and write it to out. 
     * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param list
     * @param out
     */
	public static void writeJSONString(List list, Writer out, int indent) throws IOException{
		String tabs = JSONObject.tabs(indent);
		String cr = JSONObject.cr(indent);

		if(list == null){
			out.write("null");
			return;
		}
		
		boolean first = true;
		Iterator iter=list.iterator();
		
        out.write('[');
		while(iter.hasNext()){
            if(first)
                first = false;
            else {
                out.write(',');
				out.write(cr);
				out.write(tabs);
			}
			Object value=iter.next();
			if(value == null){
				out.write("null");
				continue;
			}
			
			JSONValue.writeJSONString(value, out, indent);
		}
		out.write(']');
	}
	
	public void writeJSONString(Writer out, int indent) throws IOException{
		writeJSONString(this, out, indent);
	}
	
	/**
	 * Convert a list to JSON text. The result is a JSON array. 
	 * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
	 * 
	 * @see org.json.simple.JSONValue#toJSONString(Object)
	 * 
	 * @param list
	 * @return JSON text, or "null" if list is null.
	 */
	public static String toJSONString(List list, int indent){
		StringWriter sw = new StringWriter();
		try {
			writeJSONString(list, sw, indent);
		} catch (IOException ex) {
			Logger.getLogger(JSONArray.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sw.toString();
	}

	public String toJSONString(int indent){
		return toJSONString(this, indent);
	}
	
	public String toString() {
		return toJSONString(-1);
	}

	
		
}
