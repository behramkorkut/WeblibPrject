package com.yenimobile.weblibprject.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yenimobile.weblibprject.BuildConfig;
import com.yenimobile.weblibprject.R;
import com.yenimobile.weblibprject.adapter.PostAdapter;
import com.yenimobile.weblibprject.model.TumPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImagesearchActivity extends AppCompatActivity {


    private JsonObjectRequest mObjectRequest;
    private RequestQueue mRequestQueue ;

    private RecyclerView mRV;
    private TextInputEditText mSearchInputET;
    private Button mSearchButton;

    private PostAdapter mAdapter;
    private ArrayList<TumPost> mPostsList;

    private static final String APIKEY = BuildConfig.TumblrApiKey;
    private static final String BASEURL = "https://api.tumblr.com/v2/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagesearch);

        mPostsList = new ArrayList<>();
        mAdapter = new PostAdapter(this, mPostsList);

        mSearchInputET = findViewById(R.id.inputTagsET);
        mSearchButton = findViewById(R.id.buutonImageSearch);

        mRV = findViewById(R.id.rvimagesearch);
        mRV.setHasFixedSize(true);
        mRV.setLayoutManager(new GridLayoutManager(this, 2));
        mRV.setAdapter(mAdapter);



        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSearchInputET.getText().toString().isEmpty()){
                    String input = mSearchInputET.getText().toString();
                    String request = buidRequestString(input);
                    jsoncall(request);
                    hideKeyboard(ImagesearchActivity.this);

                }else {
                    Toast.makeText(ImagesearchActivity.this, "please fill in text in the search bar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }// end of onCreate


    private String buidRequestString(String input){
        String[] words = input.split(" ");
        String tag = "";
        for (String s : words){
            tag = s+"+";
        }

        String requestString = BASEURL + "tagged?tag="+tag+"&api_key=" + APIKEY;
        return requestString;
    }


    public static void hideKeyboard( Activity activity ) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService( Context.INPUT_METHOD_SERVICE );
        View f = activity.getCurrentFocus();
        if( null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom( f.getClass() ) )
            imm.hideSoftInputFromWindow( f.getWindowToken(), 0 );
        else
            activity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
    }



    public void jsoncall(String requestString) {


        mObjectRequest = new JsonObjectRequest(requestString,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mPostsList.clear();
                JSONArray jsonArray = null;


                try {

                    jsonArray = response.getJSONArray("response");
                    Log.e("volley", "----------------------- JSONArray length is : "+ jsonArray.length());

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONObject jsonObject = null;

                for (int i = 0; i<jsonArray.length(); i++){
                    try {
                        jsonObject = jsonArray.getJSONObject(i);
                        TumPost post = new TumPost();

                        String postType = jsonObject.getString("type");

                        if (postType.equals("photo")){
                            post.setTumPostId( String.valueOf(jsonObject.getInt("id")) );
                            post.setTumPostType(jsonObject.getString("type"));
                            post.setTumPostUserName(jsonObject.getString("blog_name"));

                            JSONArray photoArray = jsonObject.getJSONArray("photos");
                            JSONObject zeroObject = (JSONObject) photoArray.get(0);
                            JSONArray imageSizeArray = zeroObject.getJSONArray("alt_sizes");
                            JSONObject fifthSize = imageSizeArray.getJSONObject(0);
                            post.setTumPostImageUrl(fifthSize.getString("url"));


                            mPostsList.add(post);
                        }

                    }catch (JSONException e){
                        Log.e("jsonExcept" , "---------------------- json exception "+ e.getMessage());
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "-------------------------- Error loading  "+ error.getMessage());
            }
        });

        mRequestQueue = Volley.newRequestQueue(ImagesearchActivity.this);
        mRequestQueue.add(mObjectRequest);
    }










}
