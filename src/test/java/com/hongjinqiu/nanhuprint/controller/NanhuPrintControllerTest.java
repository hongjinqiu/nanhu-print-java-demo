package com.hongjinqiu.nanhuprint.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.util.DateUtils;
import com.hongjinqiu.nanhuprint.exception.BusinessRuntimeException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NanhuPrintControllerTest {

    /**
     * 普通模版打印
     * @throws Exception
     */
    @Test
    public void testRHeadBodyRTailMetaPrint() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "r_head_body_r_tail";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            {
                JSONObject order = printConfigJSON.getJSONObject("order");
                JSONArray itemLi = order.getJSONArray("itemLi");
                JSONObject item0 = itemLi.getJSONObject(0);
                for (int i = 0; i < 14; i++) {
                    itemLi.add(JSONObject.parseObject(JSONObject.toJSONString(item0)));
                }
            }
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testHeadRBodyRTailMetaPrint() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "head_r_body_r_tail";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            {
                JSONObject order = printConfigJSON.getJSONObject("order");
                JSONArray itemLi = order.getJSONArray("itemLi");
                JSONObject item0 = itemLi.getJSONObject(0);
                for (int i = 0; i < 14; i++) {
                    itemLi.add(JSONObject.parseObject(JSONObject.toJSONString(item0)));
                }
            }
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testNormalMetaPrint() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "normal";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 分页的打印
     * @throws Exception
     */
    @Test
    public void testPageNumMetaPrint() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "pageNum";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 套打测试
     * @throws Exception
     */
    @Test
    public void testGlovePrinting() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "glovePrinting";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试水印
     * @throws Exception
     */
    @Test
    public void testNormalWaterMarkPrint() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "normalWaterPrint";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自定义内容
     * @throws Exception
     */
    @Test
    public void testCustomContent() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "customContent";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 自定义字体的实现
     * @throws Exception
     */
    @Test
    public void testFontCustom() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrintFontCustom.json");
        String fileName = "fontCustom";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 交替显示内容
     * @throws Exception
     */
    @Test
    public void testTwoMode() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("http://localhost:8891/nanhuPrint.json");
        String fileName = "twoMode";
        String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
        {
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("fileName",fileName));

            String printConfigJSONContent = getNormalContent(fileName);
            JSONObject printConfigJSON = JSONObject.parseObject(printConfigJSONContent);
            params.add(new BasicNameValuePair("printConfigJSON",printConfigJSON.toJSONString()));

            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
            httpPost.setEntity(entity);
        }

        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());

                byte[] data = EntityUtils.toByteArray(responseEntity);

                String filePath = "e:\\tmp\\" + fileName + "_" + date + ".pdf";
                FileUtils.writeByteArrayToFile(new File(filePath), data);
                System.out.println("文件路径为:" + filePath);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getNormalContent(String fileName) {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName + ".json")) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copyLarge(in, out);
            return out.toString("utf8");
        } catch (IOException e) {
            throw new BusinessRuntimeException(e);
        }
    }
}
