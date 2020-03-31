import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Created by ydc on 2019/10/29.
 */
public class RestTest {

    public static void main(String[] args) throws Exception {
        String body = "{\"scurr\":\"CNY\",\"passenger\":{\"type\":\"ADT\"},\"sectors\":[{\"clazz\":\"Y\",\"begin\":\"2019-11-01 10:00\",\"end\":\"2019-11-01 12:00\",\"from\":\"PVG\",\"to\":\"FRA\",\"mcxr\":\"MU\"},{\"clazz\":\"Y\",\"begin\":\"2019-11-02 10:00\",\"end\":\"2019-11-02 12:00\",\"from\":\"FRA\",\"to\":\"PVG\",\"mcxr\":\"MU\"}]}";
        HttpGetWithEntity e = new HttpGetWithEntity();
        e.setURI(URI.create("http://172.28.98.28:9080/tax_engine/v3/api/taxyq"));
        e.addHeader("Content-Type", "application/json");
        e.addHeader("Accept", "application/json");
        ByteArrayEntity entity = new ByteArrayEntity(body.getBytes(StandardCharsets.UTF_8));
        entity.setContentType("application/json");
        e.setEntity(entity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse res = httpClient.execute(e)) {
            HttpEntity responseEntity = res.getEntity();
            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                System.out.println(result);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
        private final static String METHOD_NAME = "GET";

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }
}
