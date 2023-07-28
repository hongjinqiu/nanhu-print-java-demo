package com.hongjinqiu.nanhuprint.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.util.DateUtils;
import com.hongjinqiu.nanhuprint.exception.BusinessRuntimeException;
import com.hongjinqiu.pdfservice.NanhuprintInterpreterService;
import com.hongjinqiu.nanhuprint.eval.custom.NanhuprintFontCustomUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 南湖打印 Controller
 * @author hongjinqiu 2023.05.16
 */
@Controller
public class NanhuPrintController {
    private Logger logger = Logger.getLogger(getClass());
    private static final String CONTENT_DISPOSITION_HEADER_NAME = "Content-Disposition";

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "index";
    }

    @RequestMapping("/config/{page}.html")
    public String config(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable String page) {
        return "config/" + page;
    }

    /**
     * 取得 xml 配置
     */
    @RequestMapping("/config/getXml.json")
    @ResponseBody
    public Map<String, Object> getXml(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam String fileName) {
        Map<String, Object> result = new HashMap<>();
        InputStream in = null;
        try {
            // resources目录下的相对路径
            in = this.getClass().getClassLoader().getResourceAsStream("templates/printconfig/{fileName}.xml".replace("{fileName}", fileName));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copyLarge(in, out);
            String xmlText = out.toString("utf8");
            xmlText = xmlText.replaceAll("\r", "");
            xmlText = xmlText.replaceAll("\n", "");
            xmlText = xmlText.replaceAll("'", "&apos;");

            result.put("success", true);
            result.put("xmlContent", xmlText);
        } catch (IOException e) {
            throw new BusinessRuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return result;
    }

    @RequestMapping("/nanhuPrint.json")
    public void nanhuPrint(@RequestParam String fileName, @RequestParam String printConfigJSON,
                           HttpServletRequest request, HttpServletResponse response) {
        NanhuprintFontCustomUtil.setFontIfAbsent("arial", "/home/deployee/fonts/arial.ttf");
        String xmlText;
        InputStream in = null;
        try {
            // resources目录下的相对路径
            in = this.getClass().getClassLoader().getResourceAsStream("templates/printconfig/{fileName}.xml".replace("{fileName}", fileName));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copyLarge(in, out);
            xmlText = out.toString("utf8");
            xmlText = xmlText.replaceAll("\r", "");
            xmlText = xmlText.replaceAll("\n", "");
            xmlText = xmlText.replaceAll("'", "&apos;");
        } catch (IOException e) {
            throw new BusinessRuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }

        Map<String, Object> pdfMap = JSONObject.parseObject(printConfigJSON, Map.class);
        pdfMap.put("amountPrecision", "2");
        pdfMap.put("numberPrecision", "0");
        pdfMap.put("unitPricePrecision", "2");

//        pdfMap.put("printConfigTemplate", );

        // pdfMap 中的 docBillVo, 传到 pdf-service 中后，变成: "m_headVo": {}, 因此,重新用 json2Object 包装一遍,
        NanhuprintInterpreterService nanhuprintInterpreterService = new NanhuprintInterpreterService();
        byte[] pdfArray = nanhuprintInterpreterService.interpreterString(xmlText, pdfMap);
        try {
                // 默认在浏览器中打开,
            String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
            String download = request.getParameter("download");
            if ("true".equals(download)) {
                loadFile(pdfArray, fileName + "_" + date + ".pdf", response);
            } else {
                openPdfInBrowserByBytes(pdfArray, fileName, response);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e);
        }
    }

    /**
     * 字体自定义
     * @param fileName
     * @param printConfigJSON
     * @param request
     * @param response
     */
    @RequestMapping("/nanhuPrintFontCustom.json")
    public void nanhuPrintFontCustom(@RequestParam String fileName, @RequestParam String printConfigJSON,
                           HttpServletRequest request, HttpServletResponse response) {
        NanhuprintFontCustomUtil.setFontIfAbsent("arial", "/home/deployee/fonts/arial.ttf");
        NanhuprintFontCustomUtil.setFontIfAbsent("times", "/home/deployee/fonts/times.ttf");
        NanhuprintFontCustomUtil.setFontIfAbsent("calibri", "/home/deployee/fonts/calibri.ttf");
        NanhuprintFontCustomUtil.setFontIfAbsent("century", "/home/deployee/fonts/CENTURY.TTF");

        String xmlText;
        InputStream in = null;
        try {
            // resources目录下的相对路径
            in = this.getClass().getClassLoader().getResourceAsStream("templates/printconfig/{fileName}.xml".replace("{fileName}", fileName));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copyLarge(in, out);
            xmlText = out.toString("utf8");
            xmlText = xmlText.replaceAll("\r", "");
            xmlText = xmlText.replaceAll("\n", "");
            xmlText = xmlText.replaceAll("'", "&apos;");
        } catch (IOException e) {
            throw new BusinessRuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }

        Map<String, Object> pdfMap = JSONObject.parseObject(printConfigJSON, Map.class);
        pdfMap.put("amountPrecision", "2");
        pdfMap.put("numberPrecision", "0");
        pdfMap.put("unitPricePrecision", "2");

//        pdfMap.put("printConfigTemplate", );

        // pdfMap 中的 docBillVo, 传到 pdf-service 中后，变成: "m_headVo": {}, 因此,重新用 json2Object 包装一遍,
        NanhuprintInterpreterService nanhuprintInterpreterService = new NanhuprintInterpreterService();
        byte[] pdfArray = nanhuprintInterpreterService.interpreterString(xmlText, pdfMap);
        try {
            // 默认在浏览器中打开,
            String date = DateUtils.format(new Date(), "yyyyMMdd_HHmmss");
            String download = request.getParameter("download");
            if ("true".equals(download)) {
                loadFile(pdfArray, fileName + "_" + date + ".pdf", response);
            } else {
                openPdfInBrowserByBytes(pdfArray, fileName, response);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e);
        }
    }

    private void openPdfInBrowserByBytes(byte[] files, String showName, HttpServletResponse res) throws IOException {
        OutputStream os = res.getOutputStream();
        try {
            res.reset();
            res.setContentType("application/pdf; charset=utf-8");
            res.setHeader(CONTENT_DISPOSITION_HEADER_NAME, "inline; filename=" + showName +".pdf;charset=utf-8");
            os.write(files);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 文件下载
     */
    private void loadFile(byte[] files, String showName, HttpServletResponse res) throws IOException {
        OutputStream os = res.getOutputStream();
        try {
            res.reset();
            res.setHeader(CONTENT_DISPOSITION_HEADER_NAME, "attachment; filename=" + showName + ";charset=utf-8");
            res.setContentType("application/octet-stream; charset=utf-8");
            os.write(files);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
}
