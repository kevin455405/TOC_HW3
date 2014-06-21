import java.io.*;
import java.net.*;
import org.json.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TocHw3 {

    public static void download(String source, String destination) throws IOException, ConnectException{
        InputStream is = new URL(source).openConnection().getInputStream();
        FileOutputStream fos = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        for (int length; (length = is.read(buffer)) > 0; fos.write(buffer, 0, length));
            fos.close();
            is.close();
        }


    public static void main(String[] args) throws IOException, JSONException{
        download(args[0],"data.json");
        
        int price = 0,num = 0;
        FileReader fin;
        fin = new FileReader("data.json");
        JSONArray Data = new JSONArray(new JSONTokener( fin ));
        String a = args[1], b = args[2], c = args[3];
        //System.out.println(Data.length());
        for( int i= 0 ; i<Data.length() ;i++ )
        {
            Pattern place = Pattern.compile(".*"+a+".*");
            Matcher match1 = place.matcher((Data.getJSONObject(i)).getString("鄉鎮市區"));
            if(match1.find())
            {
                Pattern road = Pattern.compile(".*"+b+".*");
                Matcher match2 = road.matcher((Data.getJSONObject(i)).getString("土地區段位置或建物區門牌"));
                if(match2.find())
                {
                    Pattern year = Pattern.compile(".*"+c+".*");
                    Matcher match3 = year.matcher(Integer.toString((Data.getJSONObject(i)).getInt("交易年月")));
                    if(match3.find())
                    {
                        //System.out.println((Data.getJSONObject(i)).getString("鄉鎮市區")+"\t"+(Data.getJSONObject(i)).getString("土地區段位置或建物區門牌")+"\t"+(Data.getJSONObject(i)).getInt("交易年月"));
                        num++;
                        price += (Data.getJSONObject(i)).getInt("總價元");
                    }
                }
            } 
        }
        System.out.println(price/num);
    }
}
