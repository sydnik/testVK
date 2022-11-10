package utils;

import aquality.selenium.core.logging.Logger;
import com.mashape.unirest.http.JsonNode;
import models.PhotoResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class VkApiUtils {

    public static int sendPostOnWall(int userId, String message){
        String url = String.format("%swall.post?owner_id=%d&message=%s&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userId, message, JsonUtil.getString("token"), JsonUtil.getString("version"));
        JSONObject jsonArray = ApiUtil.get(url).getObject();
        return jsonArray.getJSONObject("response").getInt("post_id");
    }

    public static void deletePost(int userId, int postId){
        ApiUtil.get(String.format("%swall.delete?owner_id=%d&post_id=%d&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userId, postId, JsonUtil.getString("token"), JsonUtil.getString("version")));
    }

    public static List<Integer> getUsersWhoLikePost(int userId, int postId){
        String url = String.format("%swall.getLikes?owner_id=%d&post_id=%d&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userId, postId, JsonUtil.getString("token"), JsonUtil.getString("version"));
        JSONArray array = ApiUtil.get(url).getObject().getJSONObject("response").getJSONArray("users");
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            users.add(array.getJSONObject(i).getInt("uid"));
        }
        return users;
    }

    public static int sendComment(int postId, int userId, String message){
        String url = String.format("%swall.createComment?owner_id=%d&post_id=%d&message=%s&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userId, postId, message, JsonUtil.getString("token"), JsonUtil.getString("version"));
        return  ApiUtil.get(url).getObject().getJSONObject("response").getInt("comment_id");
    }

    public static void editPostAndAddPhotoOnWall(int userid, int postId, String message, String pathToImage){
        int photoId = getPhotoIdForWall(userid, uploadPhotoOnTheWall(getUploadUrl(userid), pathToImage));
        String url = String.format("%swall.edit?owner_id=%d&post_id=%d&message=%s&attachments=photo%d_%d&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userid, postId, message, userid, photoId, JsonUtil.getString("token"), JsonUtil.getString("version"));
        ApiUtil.get(url);
    }

    public static int getPhotoIdForWall(int userId, PhotoResponse response) {
        String photo = "";
        try {
            photo = URLEncoder.encode(response.getPhoto(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Logger.getInstance().error("Didn't encode photo\n" + e.getMessage());
        }
        String url = String.format("%sphotos.saveWallPhoto?user_id=%d&photo=%s&server=%d&hash=%s&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userId, photo, response.getServer(), response.getHash(), JsonUtil.getString("token"), JsonUtil.getString("version"));
        return ApiUtil.get(url).getObject().getJSONArray("response").getJSONObject(0).getInt("id");
    }

    public static PhotoResponse uploadPhotoOnTheWall(String url, String pathToImage) {
        JsonNode response = ApiUtil.post(url, pathToImage, "photo");
        PhotoResponse photoResponse = new PhotoResponse(response.getObject().getString("photo"),
                response.getObject().getInt("server"), response.getObject().getString("hash"));
        return photoResponse;
    }

    public static String getUploadUrl(int userId) {
        String url = String.format("%sphotos.getWallUploadServer?owner_id=%d&access_token=%s&v=%s", JsonUtil.getConfiguration("urlForApi"),
                userId, JsonUtil.getString("token"), JsonUtil.getString("version"));
        JsonNode jsonNode = ApiUtil.get(url);
        return jsonNode.getObject().getJSONObject("response").getString("upload_url");
    }
}
