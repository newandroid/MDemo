package css.com.applab;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class JsonTset {
    @Test
    public void oho() {
        JSONObject jsonObject = new JSONObject();
        System.out.println("git add something");
        try {
            jsonObject.put("fuck", new byte[]{1, 2, 3});
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject.toString());
    }

    @Test
    public void jjj(){
    }
}
