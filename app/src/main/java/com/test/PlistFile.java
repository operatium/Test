package com.test;

import android.graphics.Rect;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2015-12-24.
 */
public class PlistFile {

    public ArrayList<Rect> readAnimatorPlist(InputStream inStream) {
        try {
            //创建解析器
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            //设置解析器的相关特性，true表示开启命名空间特性
//            saxParser.setProperty("http://xml.org/sax/features/namespaces",true);
            XMLContentHandler handler = new XMLContentHandler();
            saxParser.parse(inStream, handler);
            inStream.close();
            return handler.getList();
        } catch (Exception e) {
            Log.e("异常201512241258",e.toString());
        }
        return null;
    }

    //SAX类：DefaultHandler，它实现了ContentHandler接口。在实现的时候，只需要继承该类，重载相应的方法即可。
    public class XMLContentHandler extends DefaultHandler {
        private ArrayList<String> list = null;
        private String tagName = null;//当前解析的元素标签
        private String data = null;

        public ArrayList<Rect> getList() {
            ArrayList<Rect> PLists = new ArrayList<Rect>();
            for (String str : list)
            {
                str = str.replace("{","");
                str = str.replace("}","");
                Log.i("正常",str);
                String[] strs = str.split(",");
                PLists.add(new Rect(Integer.valueOf(strs[0]),Integer.valueOf(strs[1]),
                        Integer.valueOf(strs[2]),Integer.valueOf(strs[3])));
            }
            return PLists;
        }

        //接收文档开始的通知。当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作。
        @Override
        public void startDocument() throws SAXException {
            list = new ArrayList<>();
        }

        //接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中namespaceURI表示元素的命名空间；
        //localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；atts 表示元素的属性集合
        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
            if(localName.equals("key") || localName.equals("string")){
                this.tagName = localName;
            }
        }

        //接收字符数据的通知。该方法用来处理在XML文件中读到的内容，第一个参数用于存放文件的内容，
        //后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容。
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if(tagName!=null){
                String data = new String(ch, start, length).trim();
                if (this.tagName.equals("string") && this.data.equals("textureRect"))
                {
                    list.add(data);
                }
                if (!data.isEmpty())
                {
                    this.data = data;
                }
            }
        }

        //接收文档的结尾的通知。在遇到结束标签的时候，调用这个方法。其中，uri表示元素的命名空间；
        //localName表示元素的本地名称（不带前缀）；name表示元素的限定名（带前缀）
        @Override
        public void endElement(String uri, String localName, String name) throws SAXException {
            this.tagName = null;
        }
    }
}
