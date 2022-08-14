package com.wildcodeschool.cerebook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/*
 * Sample code to demonstrate the use of the v2 User Tweet timeline endpoint
 * */
public class tweeterApi {
    // To set your environment variables in your terminal run the following line:
    // export 'BEARER_TOKEN'='<your_bearer_token>'
    public static void main(String args[]) throws IOException, URISyntaxException {
        getPostFromTweet();
    }

    /*
     * This method calls the v2 User Tweet timeline endpoint by user ID
     * */
    private static String getTweets(String TweetUserId, String bearerToken) throws IOException, URISyntaxException {
        String tweetResponse = null;
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", TweetUserId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        queryParameters.add(new BasicNameValuePair("expansions", "attachments.media_keys"));
        queryParameters.add(new BasicNameValuePair("media.fields", "url"));
        uriBuilder.addParameters(queryParameters);
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

   public static ArrayList<JsonNode> getPostFromTweet() throws IOException, URISyntaxException {
       String[] wolverine = new String[]{"Wolverine","948687002668584960"};
       String[] Avengers = new String[]{"Avengers","393852070"};
       String[] Thor = new String[]{"Thor","701625379"};
       String[] IronMan = new String[]{"IronMan","570218986"};
       ArrayList<String[]> tweetUserIds = new ArrayList<String[]>();
       tweetUserIds.add(wolverine);
       tweetUserIds.add(Avengers);
       tweetUserIds.add(Thor);
       tweetUserIds.add(IronMan);
       ArrayList<JsonNode> tweetPosts = new ArrayList<>();
       for(String[] tweetUserId : tweetUserIds) {
           final String bearerToken = System.getenv("BEARER_TOKEN");
           String response = getTweets(tweetUserId[1], bearerToken);
           JsonNode jsonNode = new ObjectMapper().readValue(response, JsonNode.class);
           JsonNode data = jsonNode.get("data");
           Map<String, String> mediaKeyurlMap = new HashMap<String, String>();
           JsonNode media = null;
           if (jsonNode.get("includes") != null) {
               media = jsonNode.get("includes").get("media");
               for (JsonNode picture : media) {
                   String type = picture.get("type").asText();
                   if (type.equals("photo")) {
                       mediaKeyurlMap.put(picture.get("media_key").asText(), picture.get("url").asText());
                   }
               }
           }
           for (int i = 0; i < data.size(); i++) {
               JsonNode object = data.get(i);
               String username = tweetUserId[0];
               String createdAt = object.get("created_at").asText().substring(0, 10);
               String content = object.get("text").asText();
               String url = "";
               if (jsonNode.get("includes") != null) {
                   if (object.get("attachments") != null) {
                       for (Map.Entry<String, String> entry : mediaKeyurlMap.entrySet()) {
                           String dicoMediaKey = entry.getKey();
                           String mediaKey = object.get("attachments").get("media_keys").toString().substring(2,23);
                           if (mediaKey.equals(dicoMediaKey)) {
                               url = entry.getValue();
                           }
                       }
                   }
               }
               String stringTweetPost;
               String stringTweetPostContent = "{\"createdAt\":\"" + createdAt + "\",\"content\":\"" + content + "\",\"author\":{\"user\":{\"username\":\"" + username + "\"}}";
               String stringTweetPostPicture = ",\"picture\":\"" + url + "\"";
               if (url != "") {
                   stringTweetPost = stringTweetPostContent + stringTweetPostPicture + ",\"tweetos\":true}\";";
               } else {
                   stringTweetPost = stringTweetPostContent + ",\"tweetos\":true}\";";
               }
               JsonNode tweetPost = new ObjectMapper().readValue(stringTweetPost, JsonNode.class);
               tweetPosts.add(tweetPost);
           }
       }
       return tweetPosts;
   }
}
