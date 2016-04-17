package com.d954mas.android.yandextest;

import com.d954mas.android.yandextest.models.ArtistModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ArtistModelTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private int id=1;
    private String name="name";
    private List<String> genres=new ArrayList();
    private int tracks=1;
    private int albums=2;
    private String link="www.site.com";
    private String description="Description";
    private String bigCover="bigCover.ru";
    private String smallCover="smallCover.ru";


    @Test
    public void addition_isCorrect() throws Exception {
        genres.add("pop");
        genres.add("rock");
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",id);
            jsonObject.put("name",name);
            jsonObject.put("genres", new JSONArray(genres));
            jsonObject.put("tracks", tracks);
            jsonObject.put("albums", albums);
            jsonObject.put("link", link);
            jsonObject.put("description", description);
            JSONObject covers=new JSONObject();
            covers.put("small",smallCover);
            covers.put("big", bigCover);
            jsonObject.put("cover", covers);
            ArtistModel artistModel=new ArtistModel(jsonObject);
            assert isArtistModelEquals(artistModel);
        } catch (JSONException e) {
            e.printStackTrace();
            assert false;
        }
    }
    protected boolean isArtistModelEquals(ArtistModel artistModel){
        return artistModel.id==id &&
                artistModel.name.equals(name) &&
                artistModel.genres.containsAll(genres) &&
                artistModel.genres.size()==genres.size()&&
                artistModel.tracks==tracks &&
                artistModel.albums==albums &&
                artistModel.link.equals(link) &&
                artistModel.description.equals(description) &&
                artistModel.smallImageUrl.equals(smallCover) &&
                artistModel.bigImageUrl.equals(bigCover);
    }


}