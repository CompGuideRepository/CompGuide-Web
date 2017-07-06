package WebService;

import com.compguide.web.Execution.Entities.ClinicalAction;
import com.compguide.web.Execution.Entities.ClinicalActionAdapter;
import com.compguide.web.Execution.Entities.ClinicalTaskAdapter;
import com.compguide.web.Execution.Entities.ClinicalTask;
import com.compguide.web.Execution.Entities.ProcessedTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import DataBase.Entities.Formula;
import DataBase.Entities.*;
import DataBase.Entities.Guideline;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.naming.Context;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpManager {

    final public static String INVALID_URL = "INVALID_URL";
    final public static String IO_EXCEPTION = "IO_EXCEPTION";
    final public static String CLI_EXCEPTION = "CLI_EXCEPTION";
    private String URL;
    private Integer HttpCode;
    private Header cookieLogin;
    private Header cookieValidade;
    private Header[] headers;
    private String status;
    private ArrayList<BasicNameValuePair> valuePairs;
    private Context context;

//	private static final String TAG = "HttpClient";
    public HttpManager(String url) {
        this.URL = url;
        cookieLogin = null;
        cookieValidade = null;
        HttpCode = -1;
        valuePairs = null;
        status = null;

    }

    public HttpManager() {
        this.URL = "http://localhost:8080/CompGuideCore/";
        //this.URL = "http://ec2-52-58-129-169.eu-central-1.compute.amazonaws.com/CompGuideCore/";
        cookieLogin = null;
        cookieValidade = null;
        HttpCode = -1;
        valuePairs = null;
        status = null;
    }

    public HttpManager(String url, ArrayList<BasicNameValuePair> valuePairs) {
        this.URL = url;
        cookieLogin = null;
        cookieValidade = null;
        HttpCode = -1;
        this.valuePairs = valuePairs;
        status = null;

    }

    public HttpManager(String url, ArrayList<BasicNameValuePair> valuePairs, Context c) {
        this.URL = url;
        cookieLogin = null;
        cookieValidade = null;
        HttpCode = -1;
        this.valuePairs = valuePairs;
        this.context = c;
        status = null;

    }

    public static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String sendUpdateUserDetails(Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);

        if (h == null) {
            return "Token: Sem permissao para efectuar o pedido";
        }
        httpPostRequest.addHeader("cookie", h.getValue());

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Erro a inserir parametros no POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPostRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "(Erro de IOException)";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String sendUpdateUser(Header h, ArrayList<BasicNameValuePair> params) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);

        if (h == null) {
            return "Token: Sem permissao para efectuar o pedido";
        }
        httpPostRequest.addHeader("cookie", h.getValue());

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(params);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Erro a inserir parametros no POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPostRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "(Erro de IOException)";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public Object getUserDetailsJson(Object obj, Header h, String username) {

        if (URL == null) {
            return "URL invalido";
        }
        this.URL = this.getURL() + "user/" + username;

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("User")) {
            obj = gson.fromJson(reader, User.class);
            return obj;
        }

        return null;
    }

    public Object sendGetFormula(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("Formula")) {
            obj = gson.fromJson(reader, Formula.class);
            return obj;
        }

        return null;
    }

    public Object sendGetLastTask(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        System.out.println(reader);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .setPrettyPrinting()
                .create();

        if (obj.getClass().getSimpleName().contains("ProcessedTask")) {
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }

        return null;
    }

    public Object sendGetLastTask2(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        System.out.println(reader);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .setPrettyPrinting()
                .create();

        if (obj.getClass().getSimpleName().contains("ProcessedTask")) {
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }

        return null;
    }

    public Object sendGetPatient(Object obj, Header h, String nutente) {

        if (URL == null) {
            return "URL invalido";
        }
        URL = this.getURL() + "patient/" + nutente;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("Patient")) {
            obj = gson.fromJson(reader, Patient.class);
            return obj;
        }

        return null;
    }

    public Object sendGetGuideline(Object obj, Header h, String idguideline) {

        if (URL == null) {
            return "URL invalido";
        }
        URL = this.getURL() + "guideline/" + idguideline;
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();
        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        this.setStatus(reader);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("Guideline") && HttpCode == 200) {
            obj = gson.fromJson(reader, Guideline.class);
            return obj;
        }

        return null;
    }

    public Object sendGetGuidelines(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }
        String reader = convertStreamToString(instream);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("GuidelineList")) {
            System.out.println(reader.toString());
            obj = gson.fromJson(reader, GuidelineList.class);
            return obj;
        }

        return null;
    }

    public Object sendGetGuideexecs(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {

            return null;
        } catch (Exception e) {
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }
        String reader = convertStreamToString(instream);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("GuideexecList")) {
            obj = gson.fromJson(reader, GuideexecList.class);
            return obj;
        }

        return null;
    }

    public Object getUser(Object obj, Header h, String username) {
        this.URL = this.getURL() + "user/" + username;

        System.out.println("OBJECT: " + obj);
        System.out.println("HEADER: " + h.toString());
        System.out.println("USERNAME: " + username);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        if (h == null) {
            return null;
        }
        httpgetRequest.addHeader("cookie", h.getValue());
        System.out.println(httpgetRequest.toString());
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();
            System.out.println(response.toString());

        } catch (ClientProtocolException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return null;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return null;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return null;//"Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return null;//"Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null; //"Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        String reader = convertStreamToString(instream);
        Gson gson = new Gson();

        if (obj.getClass().getSimpleName().contains("User") && HttpCode == 200) {
            obj = gson.fromJson(reader, User.class);
            return obj;
        }

        return null;
    }

    public String sendForgotGET() {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        HttpResponse response = null;

        try {
            response = (HttpResponse) httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String sendSearchGET() {

        if (URL == null) {
            return INVALID_URL;
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpgetRequest = new HttpGet(URL);

        HttpResponse response = null;

        try {
            response = (HttpResponse) httpclient.execute(httpgetRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return CLI_EXCEPTION;
        } catch (IOException e) {
            return IO_EXCEPTION;
        } catch (Exception e) {
            return CLI_EXCEPTION;
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return CLI_EXCEPTION;
        } catch (IOException e) {
            return IO_EXCEPTION;
        } catch (Exception e) {
            return CLI_EXCEPTION;
        }

        return convertStreamToString(instream);
    }

    public String sendAddUser(Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Erro a inserir parametros no POST";
        }
        if (h == null) {
            return null;
        }
        httpPutRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String sendAddPatient(Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Erro a inserir parametros no POST";
        }
        if (h == null) {
            return null;
        }
        httpPutRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String sendActionPut(Header h) {

        if (URL == null) {
            return "invalid url";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);
        httpPutRequest.addHeader("cookie", h.getValue());

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Error inserting POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public Object sendPostNext(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);

        if (h == null) {
            return "Token: Sem permissao para efectuar o pedido";
        }
        httpPostRequest.addHeader("cookie", h.getValue());

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: UnUnsupportedEncodingException! : " + e.toString());
            return "Erro a inserir parametros no POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPostRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : " + e.toString());
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : " + e.toString());
            return "(Erro de IOException)";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : " + e.toString());
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : " + e.toString());
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null;
        }
        String reader = convertStreamToString(instream);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .setPrettyPrinting()
                .create();
        System.out.println(reader);
        if (obj.getClass().getSimpleName().contains("ProcessedTask") && HttpCode == 200) {
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }
        return null;
    }

    public Object sendPostNext2(Object obj, Header h) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPostRequest = new HttpPost(URL);

        if (h == null) {
            return "Token: Sem permissao para efectuar o pedido";
        }
        httpPostRequest.addHeader("cookie", h.getValue());

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            System.out.println("ERRO: UnUnsupportedEncodingException! : " + e.toString());
            return "Erro a inserir parametros no POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPostRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            System.out.println("ERRO: ClientProtocolException : " + e.toString());
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : " + e.toString());
            return "(Erro de IOException)";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            System.out.println("ERRO: IllegalStateException : " + e.toString());
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            System.out.println("ERRO: IOException : " + e.toString());
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return null;
        }
        String reader = convertStreamToString(instream);
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .setPrettyPrinting()
                .create();
        System.out.println(reader);
        if (obj.getClass().getSimpleName().contains("ProcessedTask") && HttpCode == 200) {
            obj = gson.fromJson(reader, ProcessedTask.class);
            return obj;
        }
        return null;
    }

    public String sendDelete(Header h) {

        if (URL == null) {
            return "invalid url";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpDelete httpDeleteRequest = new HttpDelete(URL);
        httpDeleteRequest.addHeader("cookie", h.getValue());

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpDeleteRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String sendPut(Header h) {

        if (URL == null) {
            return "invalid url";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);
        httpPutRequest.addHeader("cookie", h.getValue());

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Error inserting POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String auth(String user, String password) {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        this.URL = this.getURL() + "auth";
        ArrayList<BasicNameValuePair> valuePair = new ArrayList<BasicNameValuePair>();
        valuePair.add(new BasicNameValuePair("username", user));
        valuePair.add(new BasicNameValuePair("password", password));
        this.setValuePairs(valuePair);
        System.out.println(URL);
        HttpPost httpPostRequest = new HttpPost(URL);
        System.out.println(httpPostRequest);
        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPostRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Erro a inserir parametros no POST";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPostRequest);

            HttpCode = response.getStatusLine().getStatusCode();
            cookieLogin = response.getFirstHeader("Set-Cookie");
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol\n\n" + e.toString();
        } catch (IOException e) {
            return "Erro de IOException" + e.toString();
        } catch (Exception e) {
            return "Erro (EXCEPTION) na resposta do POST" + e.toString();
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do POST";
        }

        return convertStreamToString(instream);

    }

    public String sendSupportPut() {

        if (URL == null) {
            return "URL invalido";
        }

        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpPutRequest = new HttpPut(URL);

        try {
            UrlEncodedFormEntity urlEncodedFormEntity;
            urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs);
            httpPutRequest.setEntity(urlEncodedFormEntity);

        } catch (UnsupportedEncodingException e) {
            return "Erro a inserir parametros do Support";
        }

        HttpResponse response = null;
        try {
            response = (HttpResponse) httpclient.execute(httpPutRequest);
            HttpCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            return "Erro de ClientProtocol";
        } catch (IOException e) {
            return "Erro de IOException";
        } catch (Exception e) {
            return "Erro (EXCEPTION) Sem resposta do PUT";
        }

        HttpEntity entity = response.getEntity();

        InputStream instream;
        try {
            instream = entity.getContent();
        } catch (IllegalStateException e) {
            return "Erro de IllegalStateException do entity.getContent()";
        } catch (IOException e) {
            return "Erro de IOException do entity.getContent()";
        } catch (Exception e) {
            return "Erro (EXCEPTION) na conteudo do Entity do PUT";
        }

        return convertStreamToString(instream);

    }

    public Integer getHttpCode() {
        return HttpCode;
    }

    public ArrayList<BasicNameValuePair> getValuePairs() {
        return new ArrayList<BasicNameValuePair>(valuePairs);
    }

    public void setValuePairs(ArrayList<BasicNameValuePair> valuePairs) {
        this.valuePairs = valuePairs;
    }

    public void setHttpCode(Integer httpCode) {
        HttpCode = httpCode;
    }

    public Header getCookieValidade() {
        return cookieValidade;
    }

    public String getCookieValidadeToString() {
        return cookieValidade.getValue();
    }

    public void setCookieValidade(Header cookieValidade) {
        this.cookieValidade = cookieValidade;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public Header getCookieLogin() {
        return cookieLogin;
    }

    public void setCookieLogin(Header cookieLog) {
        cookieLogin = cookieLog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
