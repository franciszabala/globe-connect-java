/* 
 * The MIT License
 *
 * Copyright 2016 charleszamora.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ph.com.globe.connect;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;

/**
 * Authentication Class.
 * 
 * @author Charles Zamora czamora@openovate.com
 */
public class Authentication extends Context {
    /* Default api host */
    private final String API_HOST   = "https://developer.globelabs.com.ph";
    
    /* Default dialog url */
    private final String DIALOG_URL = "/dialog/oauth";
    
    /* Default access url */
    private final String ACCESS_URL = "/oauth/access_token";
    
    /* API app id */
    protected String appId     = null;
    
    /* API app secret. */
    protected String appSecret = null;
    
    /**
     * Create Authentication class without paramters.
     */
    public Authentication() {
        super();
    }
    
    /**
     * Create Authentication class with appId and
     * appSecret parameters.
     * 
     * @param appId app id
     * @param appSecret app secret
     */
    public Authentication(String appId, String appSecret) {
        super();
        
        // set app id
        this.appId = appId;
        // set app secret
        this.appSecret = appSecret;
    }
    
    /**
     * Returns the current app id.
     * 
     * @return String 
     */
    public String getAppId() {
        return this.appId;
    }
    
    /**
     * Returns the current app secret.
     * 
     * @return String
     */
    public String getAppSecret() {
        return this.appSecret;
    }
    
    /**
     * Returns the Oauth dialog url.
     * 
     * @return String
     * @throws ApiException api exception
     */
    public String getDialogUrl() throws ApiException {
        // try parsing url
        try {
            // build url
            String url = this.API_HOST + this.DIALOG_URL;
            // initialize url builder
            URIBuilder builder = new URIBuilder(url);
            
            // set app_id parameter
            builder.setParameter("app_id", this.appId);
            
            // build the url
            url = builder.build().toString();
            
            return url;
        } catch(URISyntaxException e) {
            // throw exception
            throw new ApiException(e.getMessage());
        }
    }
    
    /**
     * Returns the access url.
     * 
     * @return String 
     */
    public String getAccessUrl() {
        return this.API_HOST + this.ACCESS_URL;
    }
    
    /**
     * Get access token request.
     * 
     * @param  code access code
     * @return HttpResponse
     * @throws HttpRequestException http request exception
     * @throws HttpResponseException http response excetion
     */
    public HttpResponse getAccessToken(String code) 
        throws HttpRequestException, HttpResponseException {
        // create data key value
        Map<String, Object> data = new HashMap<>();
        
        // set app id
        data.put("app_id", this.appId);
        // set app secret
        data.put("app_secret", this.appSecret);
        // set app code
        data.put("code", code);
        
        // send request
        CloseableHttpResponse results = this.request
        // set url
        .setUrl(this.getAccessUrl())
        // set data
        .setData(data)
        // send post request
        .sendPost();
        
        return new HttpResponse(results);
    }
    
    /**
     * Set API app id.
     * 
     * @param appId app id
     * @return this
     */
    public Authentication setAppId(String appId) {
        // set app id
        this.appId = appId;
        
        return this;
    }
    
    /**
     * Set API app secret.
     * 
     * @param appSecret app secret
     * @return this 
     */
    public Authentication setAppSecret(String appSecret) {
        // set app secret
        this.appSecret = appSecret;
        
        return this;
    }
}
