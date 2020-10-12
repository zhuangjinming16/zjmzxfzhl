package com.zjmzxfzhl.common.core.xss;

import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.reference.DefaultEncoder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final static DefaultEncoder defaultEncoder = new DefaultEncoder(Arrays.asList("HTMLEntityCodec",
            "PercentCodec"));
    //定义Pattern数组，用于正则匹配，可添加其他pattern规则至此
    private final static Pattern[] patterns = new Pattern[]{
            // 避免script 标签
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // src='...',避免src形式的表达式
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("src[\r\n]*=[\r\n" +
            "]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // lonely script tags,删除单个的<script ...> 标签
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // lonely script tags,删除单个的 </script> 标签
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE), Pattern.compile("<script(.*?)>",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // eval(...),// 避免 eval(...) 形式表达式
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // expression(...),避免 e­xpression(...) 表达式
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // javascript:...,避免 javascript: 表达式
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // vbscript:...,// 避免 vbscript: 表达式
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // 避免 οnlοad= 表达式
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
            // 避免 onXX= 表达式
            // Pattern.compile("on.*(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };
    /**
     * 没被包装过的HttpServletRequest（特殊场景，需要自己过滤）
     */
    HttpServletRequest orgRequest;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        String contentType = super.getHeader(HttpHeaders.CONTENT_TYPE);
        if (contentType != null && !contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }

        // 为空，直接返回
        String json = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StringUtils.isBlank(json)) {
            return super.getInputStream();
        }

        // xss过滤
        json = xssEncodeRequestBody(json);
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssEncode(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    private String xssEncode(String input) {
        if (CommonUtil.isEmptyStr(input)) {
            return input;
        }
        input = ESAPI.encoder().canonicalize(input, false, false);

        int length = input.length();
        input = patternReplace(input);
        if(length != input.length()){
            /// 暂时直接报错，不做清洗
            throw new SysException("包含XSS非法字符!");
        }
        return input;
    }

    /**
     * 请求体处理，多用于json数据，自定义encoder，排除掉javascriptcodec
     * @param input
     * @return
     */
    private String xssEncodeRequestBody(String input){
        if (CommonUtil.isEmptyStr(input)) {
            return input;
        }
        input = defaultEncoder.canonicalize(input, false, false);
        int length = input.length();
        input = patternReplace(input);
        if(length != input.length()){
            /// 暂时直接报错，不做清洗
            throw new SysException("包含XSS非法字符!");
        }
        return input;
    }

    /**
     * 根据 Pattern 替换字符
     */
    private String patternReplace(String input){
        if (StringUtils.isNotBlank(input)){
            // 避免null
            input = input.replaceAll("\0", "");
            // 根据Pattern匹配到的字符，做""替换
            for (Pattern scriptPattern : patterns){
                input = scriptPattern.matcher(input).replaceAll("");
            }
        }
        return input;
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }
        return request;
    }

}
