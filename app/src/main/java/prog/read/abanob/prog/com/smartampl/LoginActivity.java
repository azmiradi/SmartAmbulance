package prog.read.abanob.prog.com.smartampl;

import androidx.appcompat.app.AppCompatActivity;
import prog.read.abanob.prog.com.smartampl.model.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    ImageView imageView;
    private ChasingDots mChasingDotsDrawable;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
    }
    private void initialization() {

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        //ImageView
        imageView = (ImageView)  findViewById(R.id.image);
        imageView.setVisibility(View.GONE);
        mChasingDotsDrawable = new ChasingDots();
        mChasingDotsDrawable.setColor(getResources().getColor(R.color.colorPrimary));
        imageView.setImageDrawable(mChasingDotsDrawable);

        view=findViewById(R.id.reservation_second_screen);
        view.setVisibility(View.GONE);
        disLoading();
    }



    private void showLoading(){
        view.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        mChasingDotsDrawable.start();

    }
    private void disLoading(){
        view.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        mChasingDotsDrawable.stop();

    }
    private boolean checkEditText(){
        boolean result=false;


        if (TextUtils.isEmpty(getText(password)))
        {
            result=false;
            password.setError(getResources().getString(R.string.Required));
        }
        else
        {
            result=true;
        }

        if (TextUtils.isEmpty(getText(email)))
        {
            result=false;
            email.setError(getResources().getString(R.string.Required));
        }
        else
        {
            result=true;
        }
        return result;
    }
    private String getText(EditText editText)
    {
        return editText.getText().toString();
    }


    public void loginOn(View view) {
        if (checkEditText()) {
            sendData();
        }
    }

    public void registerOn(View view) {
        Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendData() {
        showLoading();
        final User user=new User();
        String  url = "http://cinematvone.com/smart_ambulance/api/paramedic/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String message=jsonObject.getString("message");
                            if (message.equals("Success"))
                            {
                                String accessToken=jsonObject.getString("accessToken");
                                user.setEmail(getText(email));
                                user.setAccessToken(accessToken);
                                 new PrefManager(getApplicationContext()).saveLoginDetails(user);
                                startActivity();
                            }
                            else {
                                Snackbar.make(view, message,Snackbar.LENGTH_LONG).show();
                            }
                            disLoading();
                            System.out.println("+++++++++++++++++"+message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e+"+++++++++++++++++");
                            disLoading();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (mChasingDotsDrawable.isRunning())
                        {
                            disLoading();
                            Snackbar snackbar= Snackbar.make(view, error.getMessage(),Snackbar.LENGTH_LONG);
                            snackbar.setAction(getString(R.string.tryAgian), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sendData();
                                }
                            });
                            snackbar.show();
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                 params.put("email", getText(email));
                params.put("password", getText(password));

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        queue.add(postRequest);

    }

    private void startActivity() {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }
}
