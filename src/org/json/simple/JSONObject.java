/*
 * $Id: JSONObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 * 
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
public class JSONObject extends HashMap implements Map, JSONAware, JSONStreamAware{
	private static final long serialVersionUID = -503443796854799292L;

    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     * 
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     * 
     * @param map
     * @param out
     */
	public static void writeJSONString(Map map, Writer out, int indent) throws IOException {
		String tabs = tabs(indent);
		String cr = cr(indent);
		
		if(map == null) {
			out.write("null");
			out.write(cr);
			return;
		}
		
		boolean first = true;
		Iterator iter=map.entrySet().iterator();
		
        out.write('{');
		while(iter.hasNext()){
            if(first) {
				out.write(cr);
				out.write(tabs);
                first = false;
			}
            else {
                out.write(',');
				out.write(cr);
				out.write(tabs);
			}
			Map.Entry entry=(Map.Entry)iter.next();
            out.write('\"');
            out.write(escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
			JSONValue.writeJSONString(entry.getValue(), out, nextIndent(indent));
		}
		out.write(cr);
		out.write(tabs);
		out.write('}');
	}

	public void writeJSONString(Writer out, int indent) throws IOException{
		writeJSONString(this, out, indent);
	}

	public static String tabs(int times) {
		final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < times; i++) {
            sb.append('\t');
        }
        return sb.toString();
	}

	public static String cr(int times) {
		if (times == -1)
			return "";
		return "\n";
	}

	public static int nextIndent(int indent) {
		if (indent == -1)
			return -1;
		return indent + 1;
	}
	
	/**
	 * Convert a map to JSON text. The result is a JSON object. 
	 * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
	 * 
	 * @see org.json.simple.JSONValue#toJSONString(Object)
	 * 
	 * @param map
	 * @param ident -1 no indent, otherwise number of tabs
	 * @return JSON text, or "null" if map is null.
	 */
	public static String toJSONString(Map map, int indent){
		StringWriter sw = new StringWriter();
		try {
			writeJSONString(map, sw, indent);
		} catch (IOException ex) {
			Logger.getLogger(JSONObject.class.getName()).log(Level.SEVERE, null, ex);
		}
		return sw.toString();
	}
	
	public String toJSONString(int indent){
		return toJSONString(this, indent);
	}
	
	private static String toJSONString(String key,Object value, StringBuffer sb, int indent){
		sb.append('\"');
        if(key == null)
            sb.append("null");
        else
            JSONValue.escape(key, sb);
		sb.append('\"').append(':');
		
		sb.append(JSONValue.toJSONString(value, indent).trim());
		sb.append(cr(indent));
		return sb.toString();
	}
	
	public String toString(){
		return toJSONString(-1);
	}

	public static String toString(String key,Object value){
        StringBuffer sb = new StringBuffer();
		toJSONString(key, value, sb, -1);
        return sb.toString();
	}
	
	/**
	 * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
	 * It's the same as JSONValue.escape() only for compatibility here.
	 * 
	 * @see org.json.simple.JSONValue#escape(String)
	 * 
	 * @param s
	 * @return
	 */
	public static String escape(String s){
		return JSONValue.escape(s);
	}
}
