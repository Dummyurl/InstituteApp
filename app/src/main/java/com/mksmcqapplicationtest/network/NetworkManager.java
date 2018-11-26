package com.mksmcqapplicationtest.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mksmcqapplicationtest.disk.DatabaseHandler;
import com.mksmcqapplicationtest.util.AppUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static NetworkManager mInstance = null;
    DatabaseHandler databaseHandler;
    public RequestQueue requestQueue;

    private NetworkManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        databaseHandler = new DatabaseHandler(context);
    }

    private RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (null == mInstance)
            mInstance = new NetworkManager(context);
        return mInstance;
    }

    public void makeNetworkRequestForData(final int requestCode, int method, final String url, final Map<String, String> headers, final String GetListAdArrayString, final NetworkResponseListener listener) {
        try{
        StringRequest request = new StringRequest(
                method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                        listener.onDataReceived(requestCode, response, url);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse() called with: " + "error = [" + error + "]");
                        listener.onDataFailed(requestCode, error);
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String your_string_json = null;
                your_string_json = GetListAdArrayString;
                return your_string_json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        request.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);}
        catch (Exception ex){
            Log.d("NetworkManager ",""+ex);
        }
    }


    public void makeNetworkRequestForString(final int requestCode, int method, String url, final Map<String, String> headers, final NetworkResponseListener listener) {
        final String finalUrl = url;
        if (url.contains(",")) {
            String urlArray[] = url.split(",");
            url = urlArray[0];
        }
        StringRequest request = new StringRequest(
                method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                        listener.onDataReceived(requestCode, response, finalUrl);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse() called with: " + "error = [" + error + "]");
                        listener.onDataFailed(requestCode, error);
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                String your_string_json = null;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("ClassCode",AppUtility.KEY_CLASSCODE);
                    jsonObject.put("TestCode", AppUtility.KEY_TEST_ID);
                    jsonObject.put("Password", AppUtility.KEY_PASSWORD);
                    jsonObject.put("Username", AppUtility.KEY_USERNAME);
                    jsonArray.put(jsonObject);
                    your_string_json = jsonArray.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return your_string_json.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        request.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

    public void loginUser(final int requestcode, int method, final String url, final NetworkResponseListener listener, final String username, final String password, final String ActivityName) {


        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();

                        listener.onDataReceived(requestcode, response, url);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onDataFailed(requestcode, error);

                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                String your_string_json = null;

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Password", password);
                    jsonObject.put("Username", username);
                    jsonObject.put("ActivityName",ActivityName);
                    jsonArray.put(jsonObject);
                    your_string_json = jsonArray.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return your_string_json.getBytes();

            }

        };
        stringRequest.setTag(requestcode);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(stringRequest);
    }

    public void passwordChange(final int requestcode, int method, final String url, final NetworkResponseListener listener) {


        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onDataReceived(requestcode, response, url);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onDataFailed(requestcode, error);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONArray jsonArray = new JSONArray();
                String your_string_json = null;
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("NewPassword", AppUtility.KEY_NEW_PASSWORD);
                    jsonObject.put("StudentCode",AppUtility.KEY_STUDENTCODE);
                    //TODO CLassCode
                    jsonObject.put("ClassCode", AppUtility.KEY_CLASSCODE);
                    jsonObject.put("Username", AppUtility.KEY_USERNAME);

                    jsonArray.put(jsonObject);
                    your_string_json = jsonArray.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return your_string_json.getBytes();
            }

        };
        stringRequest.setTag(requestcode);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(stringRequest);
    }

    public void Synchronization(final int requestcode, int method, final String url, final NetworkResponseListener listener, final String Data) {


        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // String response=(String) data;
                        response = response.replaceAll("\"", "");
                        response = response.substring(1, response.length() - 1);
                        if (response.equals("Result:Test Result Save SuccessFully.")) {
                            databaseHandler.updatedataafterSynchronization(Data);
                            listener.onDataReceived(requestcode, response, url);
                        } else if (response.equals("Result:Test Result Not  Save SuccessFully.")) {
                            listener.onDataReceived(requestcode, response, url);
                        } else if (response.equals("")) {
                            listener.onDataReceived(requestcode, response, url);
                        }
                        //  Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onDataFailed(requestcode, error);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  JSONArray jsonArray = new JSONArray();
                String your_string_json = Data;
                return your_string_json.getBytes();
            }

        };
        stringRequest.setTag(requestcode);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(stringRequest);
    }

    public void AttendanceSynchronization(final int requestcode, int method, final String url, final NetworkResponseListener listener, final String Data) {


        StringRequest stringRequest = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // String response=(String) data;
                        response = response.replaceAll("\"", "");
                        response = response.substring(1, response.length() - 1);
                        if (response.equals("{Result:Attendance Save Sucessfully}")) {
                            databaseHandler.updateAttendanveAfterSynchronization(Data);
                            listener.onDataReceived(requestcode, response, url);
                        } else if (response.equals("{Result:Something Went Wrong}")) {
                            listener.onDataReceived(requestcode, response, url);
                        }
                        //  Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onDataFailed(requestcode, error);
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                //  JSONArray jsonArray = new JSONArray();
                String your_string_json = Data;
                return your_string_json.getBytes();
            }

        };
        stringRequest.setTag(requestcode);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(stringRequest);
    }
}