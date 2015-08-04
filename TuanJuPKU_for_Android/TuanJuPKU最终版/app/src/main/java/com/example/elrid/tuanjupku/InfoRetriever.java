package com.example.elrid.tuanjupku;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.json.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elrid on 5/31/15.
 */
public class InfoRetriever {
    public static boolean firsttime = true;
    public static List<Union> unionList;
    public static int curUnion;
    public static int curNews;
    public static List<Union> myUnionList;
    public static List<Message> messageList;

    public static void getMessageList(JSONArray jsonArray) throws JSONException {
        if (messageList == null)
            messageList = new ArrayList<Message>();

        if(jsonArray == null) {
            messageList.add(new Message("学生会", "明天晚上八点在理科一号楼xxxx开会。请各个部员务必到场,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
            messageList.add(new Message("爱心社", "明天晚上八点在理科一号楼xxxx开会。请各个部员务必到场"));
            messageList.add(new Message("山鹰社", "明天晚上八点在理科一号楼xxxx开会。请各个部员务必到场"));
            messageList.add(new Message("台球协会", "明天晚上八点在理科一号楼xxxx开会。请各个部员务必到场"));
            messageList.add(new Message("自行车协会", "明天晚上八点在理科一号楼xxxx开会。请各个部员务必到场"));
            return;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject JSONNews = (JSONObject) jsonArray.get(i);

            String content = (String) JSONNews.get("content");
            String unionName = (String) JSONNews.get("name");

            messageList.add(new Message(unionName, content));
        }
    }

    public static List<Union> getUnionList() throws JSONException {
        unionList = new ArrayList<Union>();
        //downloadUnionList();
        unionList.add(new Union("阿卡贝拉清唱社", " 北京大学阿卡贝拉清唱社是全国第一支致力于聚集阿卡贝拉爱好者，研究、学习、传承和推广普及阿卡贝拉艺术及文化等相关工作的学生社团。", 1));
        unionList.add(new Union("爱心社", "北京大学爱心社，成立于1993 年11月23日。是中国高校第一家由学生自发成立的志愿服务社团。自成立以来，爱心社不断发展壮大，坚持从事爱心活动。现有注册社员上千人，遍布北大各个院系及北大附属小学等单位。", 2));
        unionList.add(new Union("北大剧社", "北京大学戏剧社由英达创立于1982年，戏剧社秉承北大勤于思考和创造的特点，以其富有特色的社团文化和影响力，在北京大学号称“百团大战”的学生社团活动中有着独特的位置，连获北京大学“十佳社团”称号。戏剧社现有成员70多人，遍及北大从本科生到博士生各个年级，有相当强的创造力。", 3));
        unionList.add(new Union("京昆社", "京昆社", 4));
        unionList.add(new Union("风雷街舞社", "风雷街舞社", 5));
        unionList.add(new Union("模拟联合国协会", "模拟联合国协会", 6));
        unionList.add(new Union("山鹰社", "山鹰社", 7));
        unionList.add(new Union("台球协会", "台球协会是中华全国体育总会单位会员。1986年12月成立。总部设在北京。最高权力机构是全国委员会，执行机构是常务委员会领导下的秘书处。下设训练、竞赛、开发和新闻等4个专项委员会。协会责任主席是张小宁。协会1988年加入国际台球联合会。1998年，协会编辑出版《中国台球》刊物。台球协会经常举办全国花式台球锦标赛、全国台球锦标赛、全国台球精英赛、全国台球争霸赛和全国台球南北明星对抗赛等。", 8));
        unionList.add(new Union("北京大学学生会", "北京大学学生会", 9));
        unionList.add(new Union("阳光志愿者协会", "阳光志愿者协会", 10));
        unionList.add(new Union("元火动漫社", "元火动漫社", 11));
        unionList.add(new Union("自行车协会", "自行车协会", 12));

        myUnionList = new ArrayList<>();

        System.out.println("abcdef");
        //xmlSerialize();
        if(firsttime)
            xmlDeserialize();
        if(myUnionList.size() == 0) {
            myUnionList.add(unionList.get(8));
            myUnionList.add(unionList.get(9));
            myUnionList.add(unionList.get(10));
        }

        return unionList;
    }

    public static List<News> getNewsList(JSONArray jsonArray) throws JSONException {
        unionList.get(curUnion).newsList = new ArrayList<News>();
        //downloadNewsList(unionID);
        //JSONArray jsonArray=json.getJSONArray("contents");

        for(int i=0;i<jsonArray.length();i++){
            JSONObject JSONNews = (JSONObject) jsonArray.get(i);
            if(!testJSON(JSONNews)) {
                unionList.get(curUnion).newsList.add(new News(null, null, null, null, null, -1));
                continue;
            }

            String newsTitle = (String) JSONNews.get("title");
            String newsAbstract = (String) JSONNews.get("abstract");
            String iconURL = (String) JSONNews.get("iconURL");
            String newsURL = (String) JSONNews.get("URL");
            String newsDate = (String) JSONNews.get("thedate");
            int newsID = Integer.parseInt((String)JSONNews.get("id"));
            News news = new News(newsTitle, newsAbstract, newsURL, iconURL, newsDate, newsID);
            unionList.get(curUnion).newsList.add(news);
        }

        return unionList.get(curUnion).newsList;
    }

    private static boolean testJSON(JSONObject JSONNews) throws JSONException {
        if(JSONNews.get("title") == null)
            return false;
        if(JSONNews.get("abstract") == null)
            return false;
        if(JSONNews.get("iconURL") == null)
            return false;
        if(JSONNews.get("URL") == null)
            return false;
        if(JSONNews.get("thedate") == null)
            return false;
        if(JSONNews.get("id") == null)
            return false;
        return true;
    }

    private static void downloadUnionList() throws JSONException {
        String jsonString = "{\"count\": value, \"contents\": [{\"name\": \"value\", \"URL\": \"value\"}, ......]}";
        JSONObject json= new JSONObject(jsonString);
        JSONArray jsonArray=json.getJSONArray("contents");

        for(int i=0;i<jsonArray.length();i++){
            JSONObject JSONUnion = (JSONObject) jsonArray.get(i);
            String unionName = (String) JSONUnion.get("name");
            String iconURL = (String) JSONUnion.get("URL");
            int unionID = Integer.parseInt((String) JSONUnion.get("id"));
            Union union = new Union(unionName, iconURL, unionID);
            unionList.add(union);
        }
    }

    private static void downloadNewsList(int unionID) throws JSONException {
        //String jsonString = "{\"count\": value, \"contents\": [{\"id\": value, \"URL\": \"value\", \"title\": \"value\", \"abstract\": \"value\", \"iconURL\": \"value\", \"thedate\": \"value\"}, ......]}";
        //JSONObject json= new JSONObject(jsonString);
    }

    public static void xmlSerialize() {
        Log.i("My", "herellllllllllll");
        System.out.println("abcdef");
        XmlSerializer serializer = Xml.newSerializer();// xmlÎÄ¼þÉú³ÉÆ÷
        File file = new File(Environment.getExternalStorageDirectory(),
                "unionss.xml");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            serializer.setOutput(fos, "utf-8");// ÎªxmlÉú³ÉÆ÷ÉèÖÃÊä³öÁ÷ºÍ×Ö·û±àÂë
            serializer.startDocument("utf-8", true);// ¿ªÊ¼ÎÄµµ£¬²ÎÊý·Ö±ðÎª×Ö·û±àÂëºÍÊÇ·ñ±£³Ö¶ÀÁ¢
            serializer.startTag(null, "unions"); // ¿ªÊ¼±êÇ©,²ÎÊý·Ö±ðÎª£ºÃüÃû¿Õ¼äºÍ±êÇ©Ãû

            for (Union u : myUnionList) {

                serializer.startTag(null, "union");
                if(u == null) {
                    serializer.endTag(null, "union");
                    continue;
                }
                serializer.attribute(null, "id", u.unionID + "");

                serializer.startTag(null, "name");
                Log.i("My", u.unionName);
                System.out.println("abcdef" + u.unionName);
                serializer.text(u.unionName);
                Log.i("My", u.unionName);
                serializer.endTag(null, "name");

                serializer.startTag(null, "iconURL");
                serializer.text(u.iconURL);
                serializer.endTag(null, "iconURL");

                serializer.startTag(null, "newsList");
                for(News n : u.newsList) {
                    serializer.startTag(null, "news");
                    if(n == null) {
                        serializer.endTag(null, "news");
                    }
                    serializer.attribute(null, "ID", n.newsID + "");


                    serializer.startTag(null, "abstr");
                    serializer.text(n.newsAbstract);
                    serializer.endTag(null, "abstr");

                    serializer.startTag(null, "URL");
                    serializer.text(n.newsURL);
                    serializer.endTag(null, "URL");

                    serializer.startTag(null, "nIconURL");
                    serializer.text(n.iconURL);
                    serializer.endTag(null, "nIconURL");

                    serializer.startTag(null, "date");
                    serializer.text(n.theDate);
                    serializer.endTag(null, "date");

                    serializer.startTag(null, "title");
                    serializer.text(n.newsTitle);
                    serializer.endTag(null, "title");

                    serializer.endTag(null, "news");
                }
                serializer.endTag(null, "newsList");

                serializer.endTag(null, "union");

            }
            serializer.endTag(null, "unions");// ½áÊø±êÇ©
            serializer.endDocument();// ½áÊøxmlÎÄµµ
            Log.i("My", "ToEnd");
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void xmlDeserialize() {
        try {
            Log.i("Them", "Start");
            myUnionList = new ArrayList<>();
            File file = new File(Environment.getExternalStorageDirectory(),
                    "unionss.xml");
            //debugMessage += "here1\n";
            FileInputStream fis = new FileInputStream(file);
            Union union = null;
            News news = null;
            List<News> newsList = null;
            XmlPullParser parser = Xml.newPullParser();// »ñÈ¡xml½âÎöÆ÷
            parser.setInput(fis, "utf-8");// ²ÎÊý·Ö±ðÎªÊäÈëÁ÷ºÍ×Ö·û±àÂë
            int type = parser.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {// Èç¹ûÊÂ¼þ²»µÈÓÚÎÄµµ½áÊøÊÂ¼þ¾Í¼ÌÐøÑ\u00ad»·
                //debugMessage += parser.getName() + "\n";
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("union".equals(parser.getName())) {
                            //debugMessage += "here2\n";
                            union = new Union("", "", 0);
                            String id = parser.getAttributeValue(0);
                            union.unionID = Integer.parseInt(id);

                        } else if ("name".equals(parser.getName())) {
                            //debugMessage += "here3\n";
                            union.unionName = parser.nextText();
                            Log.i("Them", union.unionName);
                        } else if ("iconURL".equals(parser.getName())) {
                            union.iconURL = parser.nextText();
                        } else if ("newsList".equals(parser.getName())) {
                            newsList = new ArrayList<News>();
                        } else if ("title".equals(parser.getName())) {
                            news.newsTitle = parser.nextText();
                        } else if ("abstr".equals(parser.getName())) {
                            news.newsAbstract = parser.nextText();
                        } else if ("URL".equals(parser.getName())) {
                            news.newsURL = parser.nextText();
                        } else if ("news".equals(parser.getName())) {
                            //debugMessage += "here5\n";
                            news = new News("", "", "", "", "", 0);
                            //debugMessage += news.newsID + "\n";
                            String id = parser.getAttributeValue(0);
                            //debugMessage += id + "\n";
                            news.newsID = Integer.parseInt(id);
                            //debugMessage += news.newsID + "\n";
                        } else if ("nIconURL".equals(parser.getName())) {
                            news.iconURL = parser.nextText();
                        } else if ("date".equals(parser.getName())) {
                            news.theDate = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //debugMessage += parser.getName() + "\n";
                        if ("union".equals(parser.getName())) {
                            //debugMessage += "here4\n";
                            if(union.unionName.equals("")) union = null;
                            //debugMessage += union.unionName + "\n";
                            myUnionList.add(union);
                            union = null;
                        } else if ("news".equals(parser.getName())) {
                            if(news.newsTitle.equals("")) news = null;
                            newsList.add(news);
                            news = null;
                        } else if ("newsList".equals(parser.getName())) {
                            union.newsList = newsList;
                            newsList = null;
                        }
                        break;
                }
                type = parser.next();// ¼ÌÐøÏÂÒ»¸öÊÂ¼þ
            }
            fis.close();
            Log.i("Them", "ToEnd");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
